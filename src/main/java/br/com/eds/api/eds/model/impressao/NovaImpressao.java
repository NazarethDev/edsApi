package br.com.eds.api.eds.model.impressao;


import br.com.eds.api.eds.model.domiciliar.NovoEmDomicilio;
import jakarta.validation.constraints.NotBlank;

public record NovaImpressao(
        @NotBlank String nomeCliente,
        @NotBlank String contatoCliente,
        @NotBlank String emailCliente,
        @NotBlank MaterialImpressao materialImpressao,
        @NotBlank Dimensao dimensao,
        @NotBlank Integer unidades,
        NovoEmDomicilio domiciliar
) {
}
