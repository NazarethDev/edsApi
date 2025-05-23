package br.com.eds.api.eds.model.impressao;

import jakarta.validation.constraints.NotBlank;

public record NovaImpressao(
        @NotBlank
        String nomeCliente,
        @NotBlank
        String contatoCliente,
        String contatoAlternativoCliente,
        @NotBlank
        String emailCliente,
        String cpf,
        MaterialImpressao materialImpressao,
        @NotBlank Integer unidades,
        String ladosImpressao,
        String coresImpressao,
        @NotBlank Produto produto
) {
}