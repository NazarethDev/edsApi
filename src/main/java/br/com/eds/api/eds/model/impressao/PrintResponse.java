package br.com.eds.api.eds.model.impressao;

import br.com.eds.api.eds.model.cliente.ClienteResponse;
import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record PrintResponse(
        Long id,
        String arquivoImpressao,
        MaterialImpressao materialImpressao,
        Dimensao dimensao,
        StatusServicos status,
        Integer unidades,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataSolicitacao,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataAtualizacao,
        ClienteResponse cliente
) {
    public PrintResponse(Impressao impressao) {
        this(
                impressao.getId(),
                impressao.getArquivoImpressao(),
                impressao.getMaterialImpressao(),
                impressao.getDimensao(),
                impressao.getStatus(),
                impressao.getUnidades(),
                impressao.getDataSolicitacao(),
                impressao.getDataAtualizacao(),
                new ClienteResponse(impressao.getCliente())
        );
    }
}