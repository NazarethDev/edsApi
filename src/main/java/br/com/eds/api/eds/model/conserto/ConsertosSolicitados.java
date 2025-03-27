package br.com.eds.api.eds.model.conserto;

import br.com.eds.api.eds.model.cliente.Cliente;
import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;

import java.time.LocalDateTime;

public record ConsertosSolicitados(
        Long id,
        Cliente cliente,
        String descricaoProblema,
        String arquivo,
        String tempoDeUso,
        TipoProduto tipoAparelho,
        Fabricante fabricante,
        LocalDateTime dataSolicitacao,
        StatusServicos status
) {
    public ConsertosSolicitados (Conserto conserto){
        this(conserto.getId(), conserto.getCliente(),
                conserto.getDescricaoProblema(), conserto.getArquivo(),
                conserto.getTempoDeUso(), conserto.getTipoAparelho(),
                conserto.getFabricante(), conserto.getDataSolicitacao(), conserto.getStatus());
    }
}
