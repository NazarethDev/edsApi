package br.com.eds.api.eds.model.conserto;


import org.springframework.web.multipart.MultipartFile;

public record NovoConserto(
        String nomeCliente,
        String contatoCliente,
        String descricaoProblema,
        MultipartFile arquivo,
        String tempoDeUso,
        TipoProduto tipoAparelho,
        Fabricante fabricante
        ) {
}
