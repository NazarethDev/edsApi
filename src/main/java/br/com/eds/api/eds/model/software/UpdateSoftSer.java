package br.com.eds.api.eds.model.software;


import br.com.eds.api.eds.model.conserto.Fabricante;

import java.util.List;

public record UpdateSoftSer(
        String nomeCliente,
        String contatoCliente,
        String detalhesServico,
        TipoDispositivo dispositivo,
        String tempoUso,
        Fabricante fabricante,
        List<TipoServicoSoftware> servicos
) {
}
