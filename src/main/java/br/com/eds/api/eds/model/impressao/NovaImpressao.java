package br.com.eds.api.eds.model.impressao;


import br.com.eds.api.eds.service.ArquivoService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public record NovaImpressao(
        @NotBlank
        String nomeCliente,
        @NotBlank
        String contatoCliente,
        @NotBlank
        MaterialImpressao materialImpressao,
        @NotBlank
        Dimensao dimensao,
        @NotBlank
        Integer unidades,
        @NotBlank
        MultipartFile arquivoImpressao
) {
}
