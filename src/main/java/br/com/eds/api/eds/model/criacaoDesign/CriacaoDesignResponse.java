package br.com.eds.api.eds.model.criacaoDesign;

import br.com.eds.api.eds.model.cliente.ClienteResponse;
import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;
import br.com.eds.api.eds.model.impressao.MaterialImpressao;
import br.com.eds.api.eds.model.impressao.Produto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record CriacaoDesignResponse(
        Long id,
        MaterialImpressao materialImpressao,
        Produto produto,
        StatusServicos status,
        Integer unidades,
        String ladosImpressao,
        String coresImpressao,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataSolicitacao,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataAtualizacao,
        String ideiasDesign,
        String arquivoReferencia,
        ClienteResponse cliente
) {
    public CriacaoDesignResponse(CriacaoDesign criacaoDesign) {
        this(
                criacaoDesign.getId(),
                criacaoDesign.getMaterialImpressao(),
                criacaoDesign.getProduto(),
                criacaoDesign.getStatus(),
                criacaoDesign.getUnidades(),
                criacaoDesign.getLadosImpressao(),
                criacaoDesign.getCoresImpressao(),
                criacaoDesign.getDataSolicitacao(),
                criacaoDesign.getDataAtualizacao(),
                criacaoDesign.getIdeiasDesign(),
                criacaoDesign.getArquivoReferencia(),
                new ClienteResponse(criacaoDesign.getCliente())
        );
    }
}
