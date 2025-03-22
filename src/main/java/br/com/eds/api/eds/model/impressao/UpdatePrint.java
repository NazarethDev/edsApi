package br.com.eds.api.eds.model.impressao;

import org.springframework.web.multipart.MultipartFile;

public record UpdatePrint(
        String nomeCliente,
        String contatoCliente,
        MaterialImpressao materialImpressao,
        Dimensao dimensao,
        Integer unidades,
        MultipartFile arquivoImpressao
) {
}
