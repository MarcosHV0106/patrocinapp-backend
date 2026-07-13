package com.utp.patrocinapp.application.service;

import com.utp.patrocinapp.application.dto.evidencia.ArchivoEvidenciaCommand;
import com.utp.patrocinapp.shared.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.*;

class ValidadorArchivoEvidenciaTest {
    private final ValidadorArchivoEvidencia validador = new ValidadorArchivoEvidencia(
            1024, "image/jpeg,image/png,image/webp,application/pdf,video/mp4");

    @Test
    void aceptaPngConFirmaRealYGeneraSha256() {
        byte[] png = {(byte) 0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a, 1, 2, 3};
        ArchivoValidado result = validador.validar(new ArchivoEvidenciaCommand(
                "captura.png", "image/png", png.length, png, "Meta cumplida"));

        assertThat(result.tipoMime()).isEqualTo("image/png");
        assertThat(result.nombreOriginal()).isEqualTo("captura.png");
        assertThat(result.hashSha256()).hasSize(64);
    }

    @Test
    void aceptaPdfYNormalizaRutaDelNombre() {
        byte[] pdf = "%PDF-1.7 contenido".getBytes(StandardCharsets.US_ASCII);
        ArchivoValidado result = validador.validar(new ArchivoEvidenciaCommand(
                "C:\\fakepath\\comprobante.pdf", "application/pdf", pdf.length, pdf, null));
        assertThat(result.nombreOriginal()).isEqualTo("comprobante.pdf");
    }

    @Test
    void rechazaArchivoVacio() {
        assertCode(new ArchivoEvidenciaCommand("vacio.png", "image/png", 0, new byte[0], null),
                "ARCHIVO_VACIO");
    }

    @Test
    void rechazaArchivoQueSuperaLimite() {
        byte[] grande = new byte[1025];
        grande[0] = (byte) 0xff;
        assertCode(new ArchivoEvidenciaCommand("grande.jpg", "image/jpeg", grande.length, grande, null),
                "ARCHIVO_DEMASIADO_GRANDE");
    }

    @Test
    void rechazaMimeNoPermitido() {
        byte[] exe = {0x4d, 0x5a, 1};
        assertCode(new ArchivoEvidenciaCommand("archivo.exe", "application/x-msdownload", exe.length, exe, null),
                "ARCHIVO_NO_PERMITIDO");
    }

    @Test
    void rechazaFirmaQueNoCoincideConMime() {
        byte[] pdf = "%PDF-1.7".getBytes(StandardCharsets.US_ASCII);
        assertCode(new ArchivoEvidenciaCommand("falso.png", "image/png", pdf.length, pdf, null),
                "FIRMA_ARCHIVO_INVALIDA");
    }

    @Test
    void rechazaTamanioDeclaradoDiferente() {
        byte[] png = {(byte) 0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a};
        assertCode(new ArchivoEvidenciaCommand("captura.png", "image/png", png.length + 1, png, null),
                "TAMANIO_ARCHIVO_INVALIDO");
    }

    private void assertCode(ArchivoEvidenciaCommand command, String code) {
        assertThatThrownBy(() -> validador.validar(command))
                .isInstanceOfSatisfying(BusinessException.class,
                        ex -> assertThat(ex.getCode()).isEqualTo(code));
    }
}
