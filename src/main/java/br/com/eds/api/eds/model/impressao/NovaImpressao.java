package br.com.eds.api.eds.model.impressao;

import jakarta.validation.constraints.NotBlank;

public record NovaImpressao(
        @NotBlank String nomeCliente,
        @NotBlank String contatoCliente,
        @NotBlank String emailCliente,
        String contatoAlternativoCliente,
        String cpf,
        @NotBlank MaterialImpressao materialImpressao,
        @NotBlank Integer unidades,
        @NotBlank String ladosImpressao,
        String coresImpressao,
        @NotBlank Produto produto
) {
}