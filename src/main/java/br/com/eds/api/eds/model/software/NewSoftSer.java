package br.com.eds.api.eds.model.software;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record NewSoftSer(
        @NotBlank
        String nomeCliente,
        @NotBlank
        String contatoCliente,
        @NotBlank
        String emailCliente,
        String detalhesServico,
        @NotBlank
        TipoDispositivo dispositivo,
        @NotBlank
        List<TipoServicoSoftware> servicos
) {
}
