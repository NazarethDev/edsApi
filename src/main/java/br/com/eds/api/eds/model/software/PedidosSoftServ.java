package br.com.eds.api.eds.model.software;

import br.com.eds.api.eds.model.cliente.Cliente;

import java.time.LocalDateTime;
import java.util.List;

public record PedidosSoftServ(
        Long id,
        Cliente cliente,
        String detalhesServico,
        TipoDispositivo dispositivo,
        List<TipoServicoSoftware> servicos,
        LocalDateTime dataSolicitacao
) {
    public PedidosSoftServ(Software software){
        this(software.getId(), software.getCliente(),
                software.getDetalhesServico(), software.getDispositivo(),
                software.getServicos(),software.getDataSolicitacao());
    }
}
