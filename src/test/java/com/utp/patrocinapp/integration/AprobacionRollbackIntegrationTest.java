package com.utp.patrocinapp.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utp.patrocinapp.domain.enums.EstadoEvidencia;
import com.utp.patrocinapp.domain.enums.EstadoMeta;
import com.utp.patrocinapp.domain.ports.output.AuditoriaRepositoryPort;
import com.utp.patrocinapp.infrastructure.persistence.repository.ContratoJpaRepository;
import com.utp.patrocinapp.infrastructure.persistence.repository.EvidenciaJpaRepository;
import com.utp.patrocinapp.infrastructure.persistence.repository.FondoGarantiaJpaRepository;
import com.utp.patrocinapp.infrastructure.persistence.repository.MetaContratoJpaRepository;
import com.utp.patrocinapp.infrastructure.persistence.repository.TransaccionJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AprobacionRollbackIntegrationTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired EvidenciaJpaRepository evidenciaRepository;
    @Autowired MetaContratoJpaRepository metaRepository;
    @Autowired FondoGarantiaJpaRepository fondoRepository;
    @Autowired TransaccionJpaRepository transaccionRepository;
    @Autowired ContratoJpaRepository contratoRepository;
    @MockitoSpyBean AuditoriaRepositoryPort auditoriaRepository;

    @Test
    void unaExcepcionFinalRevierteEvidenciaMetaFondoPagoYContrato() throws Exception {
        JsonNode negocio = json(mockMvc.perform(post("/api/usuarios/negocios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"correo":"rollback.negocio@patrocinapp.test","password":"PruebaSegura2026!",
                                 "ruc":"20444444444","razonSocial":"Marca Rollback"}
                                """))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString());
        JsonNode deportista = json(mockMvc.perform(post("/api/usuarios/deportistas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"correo":"rollback.deportista@patrocinapp.test","password":"PruebaSegura2026!",
                                 "nombreCompleto":"Atleta Rollback","dni":"44444444","disciplina":"Natación",
                                 "biografia":"Deportista de prueba para validar atomicidad"}
                                """))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString());
        String tokenNegocio = login("rollback.negocio@patrocinapp.test");
        String tokenDeportista = login("rollback.deportista@patrocinapp.test");

        JsonNode contrato = json(mockMvc.perform(post("/api/contratos")
                        .header("Authorization", bearer(tokenNegocio))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"idNegocio":%d,"idDeportista":%d,"metas":[{
                                  "idPlantilla":1,"descripcionAcordada":"Validar rollback",
                                  "montoDeportista":100.00
                                }]}
                                """.formatted(negocio.path("data").path("id").asInt(),
                                deportista.path("data").path("id").asInt())))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString());
        int idContrato = contrato.path("data").path("idContrato").asInt();
        int idMeta = contrato.path("data").path("metas").get(0).path("idMeta").asInt();
        MockMultipartFile archivo = new MockMultipartFile("archivo", "rollback.png", "image/png",
                new byte[]{(byte) 0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a, 4});
        JsonNode evidencia = json(mockMvc.perform(multipart("/api/metas/{idMeta}/evidencias", idMeta)
                        .file(archivo).header("Authorization", bearer(tokenDeportista)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString());
        long idEvidencia = evidencia.path("data").path("idEvidencia").asLong();

        doThrow(new IllegalStateException("Fallo deliberado para comprobar atomicidad"))
                .when(auditoriaRepository).guardar(argThat(auditoria ->
                        "EVIDENCIA_APROBADA_Y_PAGADA".equals(auditoria.getAccion())));

        mockMvc.perform(post("/api/evidencias/{id}/aprobar", idEvidencia)
                        .header("Authorization", bearer(tokenNegocio)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("ERROR_INTERNO"));

        assertThat(evidenciaRepository.findById(idEvidencia).orElseThrow().getEstado())
                .isEqualTo(EstadoEvidencia.EN_REVISION);
        assertThat(metaRepository.findById(idMeta).orElseThrow().getEstado()).isEqualTo(EstadoMeta.EN_REVISION);
        assertThat(fondoRepository.findById(idContrato).orElseThrow().getMontoCongelado())
                .isEqualByComparingTo(new BigDecimal("110.00"));
        assertThat(transaccionRepository.existsByIdMetaContrato(idMeta)).isFalse();
        assertThat(contratoRepository.findById(idContrato).orElseThrow().getEstado().name()).isEqualTo("ACTIVO");
    }

    private String login(String correo) throws Exception {
        JsonNode response = json(mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"correo\":\"%s\",\"password\":\"PruebaSegura2026!\"}".formatted(correo)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString());
        return response.path("data").path("token").asText();
    }

    private String bearer(String token) { return "Bearer " + token; }
    private JsonNode json(String value) throws Exception { return objectMapper.readTree(value); }
}
