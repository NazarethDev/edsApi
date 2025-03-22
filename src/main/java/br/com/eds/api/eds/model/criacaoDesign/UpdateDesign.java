package br.com.eds.api.eds.model.criacaoDesign;

import br.com.eds.api.eds.model.impressao.UpdatePrint;
import org.springframework.web.multipart.MultipartFile;

public record UpdateDesign(
        Long id,
        String ideiasDesign,
        MultipartFile arquivoreferencia,
        UpdatePrint novosDadosImpressao
) {
}
