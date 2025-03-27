package br.com.eds.api.eds.model.criacaoDesign;

import br.com.eds.api.eds.model.cliente.Cliente;
import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;
import br.com.eds.api.eds.model.impressao.Dimensao;
import br.com.eds.api.eds.model.impressao.MaterialImpressao;

import java.time.LocalDateTime;

public record PedidosDesign(
        Long id,
        Cliente cliente,
        String arquivoReferencia,
        String ideiasDesign,
        MaterialImpressao materialImpressao,
        Dimensao dimensao,
        Integer unidades,
        LocalDateTime dataSolicitacao,
        StatusServicos status
) {
    public PedidosDesign (CriacaoDesign design){
        this(design.getId(), design.getCliente(),
                design.getArquivoReferencia(), design.getIdeiasDesign(),
                design.getMaterialImpressao(),design.getDimensao(),
                design.getUnidades(), design.getDataSolicitacao(), design.getStatus());
    }
}
