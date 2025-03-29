package br.com.eds.api.eds.model.conserto;

import br.com.eds.api.eds.model.cliente.ClienteResponse;
import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ConsertoResponse(
        Long id,
        String descricaoProblema,
        String arquivo,
        String tempoDeUso,
        TipoProduto tipoAparelho,
        Fabricante fabricante,
        StatusServicos status,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataSolicitacao,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataAtualizacao,
        ClienteResponse cliente
) {
    public ConsertoResponse(Conserto conserto) {
        this(
                conserto.getId(),
                conserto.getDescricaoProblema(),
                conserto.getArquivo(),
                conserto.getTempoDeUso(),
                conserto.getTipoAparelho(),
                conserto.getFabricante(),
                conserto.getStatus(),
                conserto.getDataSolicitacao(),
                conserto.getDataAtualizacao(),
                new ClienteResponse(conserto.getCliente())
        );
    }
}
