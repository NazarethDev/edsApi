package br.com.eds.api.eds.model.criacaoDesign;

import br.com.eds.api.eds.model.impressao.Dimensao;
import br.com.eds.api.eds.model.impressao.MaterialImpressao;

import java.time.LocalDateTime;

public record PedidosDesign(
        Long id,
        String nomeCliente,
        String arquivoReferencia,
        String ideiasDesign,
        MaterialImpressao materialImpressao,
        Dimensao dimensao,
        Integer unidades,
        LocalDateTime dataSolicitacao
) {
    public PedidosDesign (CriacaoDesign design){
        this(design.getId(), design.getNomeCliente(),
                design.getArquivoReferencia(), design.getIdeiasDesign(),
                design.getMaterialImpressao(),design.getDimensao(),
                design.getUnidades(), design.getDataSolicitacao());
    }
}
