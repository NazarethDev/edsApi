package br.com.eds.api.eds.model.impressao;


import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record NovaImpressao(
        @NotBlank String nomeCliente,
        @NotBlank String contatoCliente,
        @NotBlank String emailCliente,
        @NotBlank MaterialImpressao materialImpressao,
        @NotBlank Dimensao dimensao,
        @NotBlank Integer unidades,
        MultipartFile arquivoImpressao
) {
}
