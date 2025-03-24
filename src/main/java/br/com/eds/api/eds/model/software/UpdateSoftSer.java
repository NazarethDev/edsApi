package br.com.eds.api.eds.model.software;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UpdateSoftSer(
        String nomeCliente,
        String contatoCliente,
        String detalhesServico,
        TipoDispositivo dispositivo,
        List<TipoServicoSoftware> servicos
) {
}
