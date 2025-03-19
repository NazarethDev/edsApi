package br.com.eds.api.eds.model.conserto;



public record NovoConserto(
        String descricaoProblema,
        String tempoDeUso,
        TipoProduto tipoAparelho,
        Fabricante fabricante,
        String nomeCliente,
        String contatoCliente
        ) {
}
