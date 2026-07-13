package com.utp.patrocinapp.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utp.patrocinapp.infrastructure.persistence.repository.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FlujoEvidenciasIntegrationTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired TransaccionJpaRepository transaccionRepository;
    @Autowired FondoGarantiaJpaRepository fondoRepository;
    @Autowired ContratoJpaRepository contratoRepository;

    @Test
    void ejecutaRechazoReenvioAprobacionPagoEIdempotencia() throws Exception {
        int idNegocio = registrarNegocio("negocio.flujo@patrocinapp.test", "20111111111", "Marca Flujo");
        int idOtroNegocio = registrarNegocio("otro.negocio@patrocinapp.test", "20222222222", "Marca Ajena");
        int idDeportista = registrarDeportista("deportista.flujo@patrocinapp.test", "11111111");
        registrarDeportista("otro.deportista@patrocinapp.test", "22222222");
        String tokenNegocio = login("negocio.flujo@patrocinapp.test");
        String tokenAjeno = login("otro.negocio@patrocinapp.test");
        String tokenDeportista = login("deportista.flujo@patrocinapp.test");
        String tokenDeportistaAjeno = login("otro.deportista@patrocinapp.test");

        JsonNode contratoCreado = json(mockMvc.perform(post("/api/contratos")
                        .header("Authorization", bearer(tokenNegocio))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"idNegocio":%d,"idDeportista":%d,"metas":[{
                                  "idPlantilla":1,
                                  "descripcionAcordada":"Publicar fotografía con el logotipo",
                                  "montoDeportista":100.00
                                }]}
                                """.formatted(idNegocio, idDeportista)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.estado").value("ACTIVO"))
                .andReturn().getResponse().getContentAsString());
        int idContrato = contratoCreado.path("data").path("idContrato").asInt();
        int idMeta = contratoCreado.path("data").path("metas").get(0).path("idMeta").asInt();

        MockMultipartFile archivoInvalido = new MockMultipartFile("archivo", "evidencia.png",
                "image/png", "contenido sin firma".getBytes(StandardCharsets.UTF_8));
        mockMvc.perform(multipart("/api/metas/{idMeta}/evidencias", idMeta).file(archivoInvalido)
                        .header("Authorization", bearer(tokenDeportista)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("FIRMA_ARCHIVO_INVALIDA"));

        mockMvc.perform(multipart("/api/metas/{idMeta}/evidencias", idMeta)
                        .file(png("evidencia-ajena.png", (byte) 7))
                        .header("Authorization", bearer(tokenDeportistaAjeno)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("RECURSO_AJENO"));

        MockMultipartFile primerArchivo = png("primer-intento.png", (byte) 1);
        JsonNode primerEnvio = json(mockMvc.perform(multipart("/api/metas/{idMeta}/evidencias", idMeta)
                        .file(primerArchivo).param("comentario", "Primera entrega")
                        .header("Authorization", bearer(tokenDeportista)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.numeroIntento").value(1))
                .andExpect(jsonPath("$.data.estado").value("EN_REVISION"))
                .andReturn().getResponse().getContentAsString());
        long idPrimeraEvidencia = primerEnvio.path("data").path("idEvidencia").asLong();

        mockMvc.perform(multipart("/api/metas/{idMeta}/evidencias", idMeta).file(png("otra.png", (byte) 2))
                        .header("Authorization", bearer(tokenNegocio)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("ROL_INCORRECTO"));

        mockMvc.perform(post("/api/evidencias/{id}/aprobar", idPrimeraEvidencia)
                        .header("Authorization", bearer(tokenAjeno)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("RECURSO_AJENO"));

        mockMvc.perform(post("/api/evidencias/{id}/rechazar", idPrimeraEvidencia)
                        .header("Authorization", bearer(tokenNegocio))
                        .contentType(MediaType.APPLICATION_JSON).content("{\"motivo\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("DATOS_INVALIDOS"));

        mockMvc.perform(post("/api/evidencias/{id}/rechazar", idPrimeraEvidencia)
                        .header("Authorization", bearer(tokenNegocio))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"motivo\":\"El logotipo no se distingue con claridad\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.estado").value("RECHAZADA"))
                .andExpect(jsonPath("$.data.motivoRechazo").isNotEmpty());

        mockMvc.perform(get("/api/contratos/deportista/{id}", idDeportista)
                        .header("Authorization", bearer(tokenDeportista)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].metas[0].estado").value("RECHAZADA"))
                .andExpect(jsonPath("$.data[0].metas[0].evidencias[0].motivoRechazo")
                        .value("El logotipo no se distingue con claridad"));

        JsonNode reenvio = json(mockMvc.perform(multipart("/api/metas/{idMeta}/evidencias", idMeta)
                        .file(png("segundo-intento.png", (byte) 9)).param("comentario", "Fotografía corregida")
                        .header("Authorization", bearer(tokenDeportista)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.numeroIntento").value(2))
                .andReturn().getResponse().getContentAsString());
        long idSegundaEvidencia = reenvio.path("data").path("idEvidencia").asLong();

        mockMvc.perform(get("/api/evidencias/{id}/archivo", idSegundaEvidencia)
                        .header("Authorization", bearer(tokenNegocio)))
                .andExpect(status().isOk())
                .andExpect(header().string("Cache-Control", "private, no-store"))
                .andExpect(content().contentType("image/png"));

        mockMvc.perform(post("/api/evidencias/{id}/aprobar", idSegundaEvidencia)
                        .header("Authorization", bearer(tokenNegocio)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.estadoMeta").value("PAGADA"))
                .andExpect(jsonPath("$.data.montoNeto").value(100.0))
                .andExpect(jsonPath("$.data.comisionPlataforma").value(10.0))
                .andExpect(jsonPath("$.data.saldoRetenido").value(0.0))
                .andExpect(jsonPath("$.data.estadoContrato").value("FINALIZADO"));

        mockMvc.perform(post("/api/evidencias/{id}/aprobar", idSegundaEvidencia)
                        .header("Authorization", bearer(tokenNegocio)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("EVIDENCIA_YA_REVISADA"));

        mockMvc.perform(get("/api/contratos/{id}/historial", idContrato)
                        .header("Authorization", bearer(tokenNegocio)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.evidencias.length()").value(2))
                .andExpect(jsonPath("$.data.transacciones.length()").value(1));

        mockMvc.perform(get("/api/dashboard/deportista/resumen")
                        .header("Authorization", bearer(tokenDeportista)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.metasPagadas").value(1))
                .andExpect(jsonPath("$.data.montoLiberado").value(100.0));

        mockMvc.perform(get("/api/notificaciones").header("Authorization", bearer(tokenDeportista)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(org.hamcrest.Matchers.greaterThanOrEqualTo(4)));

        assertThat(idOtroNegocio).isPositive();
        assertThat(transaccionRepository.existsByIdMetaContrato(idMeta)).isTrue();
        assertThat(fondoRepository.findById(idContrato).orElseThrow().getMontoCongelado()).isZero();
        assertThat(contratoRepository.findById(idContrato).orElseThrow().getEstado().name()).isEqualTo("FINALIZADO");
    }

    @Test
    void protegeEndpointsSinTokenYConTokenInvalido() throws Exception {
        mockMvc.perform(get("/api/notificaciones"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("TOKEN_INVALIDO"));
        mockMvc.perform(get("/api/notificaciones").header("Authorization", "Bearer token-invalido"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("TOKEN_INVALIDO"));

        byte[] secreto = "PatrocinAppTestSecretOnlyForAutomatedTests2026".getBytes(StandardCharsets.UTF_8);
        Date emitido = new Date(System.currentTimeMillis() - 120_000);
        String expirado = Jwts.builder().subject("nadie@patrocinapp.test").issuedAt(emitido)
                .expiration(new Date(System.currentTimeMillis() - 60_000))
                .signWith(Keys.hmacShaKeyFor(secreto)).compact();
        mockMvc.perform(get("/api/notificaciones").header("Authorization", bearer(expirado)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("TOKEN_INVALIDO"));
    }

    @Test
    void dosAprobacionesConcurrentesProducenUnSoloPago() throws Exception {
        int idNegocio = registrarNegocio("negocio.concurrente@patrocinapp.test", "20333333333", "Marca Concurrente");
        int idDeportista = registrarDeportista("deportista.concurrente@patrocinapp.test", "33333333");
        String tokenNegocio = login("negocio.concurrente@patrocinapp.test");
        String tokenDeportista = login("deportista.concurrente@patrocinapp.test");

        JsonNode contrato = json(mockMvc.perform(post("/api/contratos")
                        .header("Authorization", bearer(tokenNegocio))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"idNegocio":%d,"idDeportista":%d,"metas":[{
                                  "idPlantilla":1,"descripcionAcordada":"Prueba concurrente",
                                  "montoDeportista":50.00
                                }]}
                                """.formatted(idNegocio, idDeportista)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString());
        int idMeta = contrato.path("data").path("metas").get(0).path("idMeta").asInt();
        JsonNode evidencia = json(mockMvc.perform(multipart("/api/metas/{idMeta}/evidencias", idMeta)
                        .file(png("concurrente.png", (byte) 5))
                        .header("Authorization", bearer(tokenDeportista)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString());
        long idEvidencia = evidencia.path("data").path("idEvidencia").asLong();
        long transaccionesAntes = transaccionRepository.count();

        CountDownLatch salida = new CountDownLatch(1);
        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {
            Future<Integer> primera = executor.submit(() -> aprobarAlLiberarse(idEvidencia, tokenNegocio, salida));
            Future<Integer> segunda = executor.submit(() -> aprobarAlLiberarse(idEvidencia, tokenNegocio, salida));
            salida.countDown();
            assertThat(List.of(primera.get(), segunda.get())).containsExactlyInAnyOrder(200, 409);
        }
        assertThat(transaccionRepository.count()).isEqualTo(transaccionesAntes + 1);
        assertThat(transaccionRepository.existsByIdMetaContrato(idMeta)).isTrue();
    }

    private int registrarNegocio(String correo, String ruc, String razonSocial) throws Exception {
        JsonNode response = json(mockMvc.perform(post("/api/usuarios/negocios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"correo":"%s","password":"PruebaSegura2026!","ruc":"%s","razonSocial":"%s"}
                                """.formatted(correo, ruc, razonSocial)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString());
        return response.path("data").path("id").asInt();
    }

    private int registrarDeportista(String correo, String dni) throws Exception {
        JsonNode response = json(mockMvc.perform(post("/api/usuarios/deportistas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"correo":"%s","password":"PruebaSegura2026!","nombreCompleto":"Ana Atleta",
                                 "dni":"%s","disciplina":"Atletismo","biografia":"Deportista regional"}
                                """.formatted(correo, dni)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString());
        return response.path("data").path("id").asInt();
    }

    private String login(String correo) throws Exception {
        JsonNode response = json(mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"correo\":\"%s\",\"password\":\"PruebaSegura2026!\"}".formatted(correo)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString());
        return response.path("data").path("token").asText();
    }

    private MockMultipartFile png(String nombre, byte marker) {
        return new MockMultipartFile("archivo", nombre, "image/png",
                new byte[]{(byte) 0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a, marker});
    }

    private String bearer(String token) { return "Bearer " + token; }
    private JsonNode json(String value) throws Exception { return objectMapper.readTree(value); }

    private int aprobarAlLiberarse(long idEvidencia, String token, CountDownLatch salida) throws Exception {
        salida.await();
        return mockMvc.perform(post("/api/evidencias/{id}/aprobar", idEvidencia)
                        .header("Authorization", bearer(token)))
                .andReturn().getResponse().getStatus();
    }
}
