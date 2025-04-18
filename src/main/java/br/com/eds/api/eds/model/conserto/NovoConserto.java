package br.com.eds.api.eds.model.conserto;


import br.com.eds.api.eds.model.domiciliar.NovoEmDomicilio;
import jakarta.validation.constraints.NotBlank;

public record NovoConserto(
        @NotBlank
        String nomeCliente,
        @NotBlank
        String contatoCliente,
        String contatoAlternativoCliente,
        @NotBlank
        String emailCliente,
        String cpf,
        @NotBlank
        String descricaoProblema,
        @NotBlank
        String tempoDeUso,
        @NotBlank
        TipoProduto tipoAparelho,
        @NotBlank
        Fabricante fabricante,
        NovoEmDomicilio domiciliar
)
{
}