package br.com.eds.api.eds.model.impressao;

import br.com.eds.api.eds.model.cliente.ClienteResponse;
import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record PrintResponse(
        Long id,
        String arquivoImpressao,
        MaterialImpressao materialImpressao,
        Produto produto,
        StatusServicos status,
        Integer unidades,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataSolicitacao,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataAtualizacao,
        String ladosImpressao,
        String coresImpressao,
        ClienteResponse cliente
) {
    public PrintResponse(Impressao impressao) {
        this(
                impressao.getId(),
                impressao.getArquivoImpressao(),
                impressao.getMaterialImpressao(),
                impressao.getProduto(),
                impressao.getStatus(),
                impressao.getUnidades(),
                impressao.getDataSolicitacao(),
                impressao.getDataAtualizacao(),
                impressao.getLadosImpressao(),
                impressao.getCoresImpressao(),
                new ClienteResponse(impressao.getCliente())
        );
    }
}