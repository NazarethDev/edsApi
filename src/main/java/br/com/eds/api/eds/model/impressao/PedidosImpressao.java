package br.com.eds.api.eds.model.impressao;

import java.time.LocalDateTime;

public record PedidosImpressao(
        Long id,
        String nomeCliente,
        String contatoCliente,
        LocalDateTime dataSolicitacao,
        String arquivoImpressao,
        MaterialImpressao materialImpressao,
        Integer unidades
) {
    public PedidosImpressao(Impressao impressao){
        this(impressao.getId(), impressao.getNomeCliente(),
                impressao.getContatoCliente(),impressao.getDataSolicitacao(),
                impressao.getArquivoImpressao(), impressao.getMaterialImpressao(),
                impressao.getUnidades());
    }
}
