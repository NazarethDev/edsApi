package br.com.eds.api.eds.model.impressao;

import br.com.eds.api.eds.model.cliente.Cliente;
import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;

import java.time.LocalDateTime;

public record PedidosImpressao(
        Long id,
        Cliente cliente,
        LocalDateTime dataSolicitacao,
        String arquivoImpressao,
        MaterialImpressao materialImpressao,
        Integer unidades,
        StatusServicos status
) {
    public PedidosImpressao(Impressao impressao){
        this(impressao.getId(), impressao.getCliente(),
                impressao.getDataSolicitacao(),
                impressao.getArquivoImpressao(), impressao.getMaterialImpressao(),
                impressao.getUnidades(), impressao.getStatus());
    }
}
