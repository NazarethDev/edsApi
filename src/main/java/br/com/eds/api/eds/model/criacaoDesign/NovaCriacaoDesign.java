package br.com.eds.api.eds.model.criacaoDesign;


import br.com.eds.api.eds.model.impressao.NovaImpressao;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record NovaCriacaoDesign(
        @NotBlank
        String ideiasDesign,
        MultipartFile arquivoReferencia,
        NovaImpressao dadosImpressao
) {
}

