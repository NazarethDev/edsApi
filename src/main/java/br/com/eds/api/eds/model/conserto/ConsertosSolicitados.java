package br.com.eds.api.eds.model.conserto;

import java.time.LocalDateTime;

public record ConsertosSolicitados(
        Long id,
        String nomeCliente,
        String descricaoProblema,
        String arquivo,
        String tempoDeUso,
        TipoProduto tipoAparelho,
        Fabricante fabricante,
        LocalDateTime dataSolicitacao
) {
    public ConsertosSolicitados (Conserto conserto){
        this(conserto.getId(), conserto.getNomeCliente(),
                conserto.getDescricaoProblema(), conserto.getArquivo(),
                conserto.getTempoDeUso(), conserto.getTipoAparelho(),
                conserto.getFabricante(), conserto.getDataSolicitacao());
    }
}
