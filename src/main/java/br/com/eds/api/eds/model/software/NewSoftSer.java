package br.com.eds.api.eds.model.software;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record NewSoftSer(
        @NotBlank
        String nomeCliente,
        @NotBlank
        String contatoCliente,
        @NotNull
        String emailCliente,
        String detalhesServico,
        @NotNull
        TipoDispositivo dispositivo,
        @NotNull
        List<TipoServicoSoftware> servicos
) {
}
