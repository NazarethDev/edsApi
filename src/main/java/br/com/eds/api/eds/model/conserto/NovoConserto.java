package br.com.eds.api.eds.model.conserto;


import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record NovoConserto(
        @NotBlank
        String nomeCliente,
        @NotBlank
        String contatoCliente,
        @NotBlank
        String emailCliente,
        @NotBlank
        String descricaoProblema,
        MultipartFile arquivo,
        @NotBlank
        String tempoDeUso,
        @NotBlank
        TipoProduto tipoAparelho,
        @NotBlank
        Fabricante fabricante
        )
{
}