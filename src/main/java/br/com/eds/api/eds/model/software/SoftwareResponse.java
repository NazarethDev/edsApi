package br.com.eds.api.eds.model.software;

import br.com.eds.api.eds.model.cliente.ClienteResponse;
import br.com.eds.api.eds.model.conserto.Fabricante;
import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record SoftwareResponse(
        Long id,
        String detalhesServico,
        TipoDispositivo dispositivo,
        List<TipoServicoSoftware> servicos,
        StatusServicos status,
        String tempoUso,
        Fabricante fabricante,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataSolicitacao,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataAtualizacao,
        ClienteResponse cliente
) {
    public SoftwareResponse(Software software) {
        this(
                software.getId(),
                software.getDetalhesServico(),
                software.getDispositivo(),
                software.getServicos(),
                software.getStatus(),
                software.getTempoUso(),
                software.getFabricante(),
                software.getDataSolicitacao(),
                software.getDataAtualizacao(),
                new ClienteResponse(software.getCliente())
        );
    }
}