package br.com.eds.api.eds.model.gestao.user;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(
        @NotBlank
        String email,
        @NotBlank
        String senha
) {
}
