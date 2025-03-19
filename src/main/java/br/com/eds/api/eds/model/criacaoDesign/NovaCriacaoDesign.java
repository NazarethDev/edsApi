package br.com.eds.api.eds.model.criacaoDesign;


import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record NovaCriacaoDesign(
        @NotBlank
        String ideiasDesign,
        @NotBlank
        String nomeCliente,
        @NotBlank
        String contatoCliente,
        MultipartFile arquivoReferencia
) {
}
