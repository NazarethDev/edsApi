package br.com.eds.api.eds.model.conserto;

public record UpdateConserto(
        String nomeCliente,
        String contatoCliente,
        String descricaoProblema,
        String tempoDeUso,
        TipoProduto tipoAparelho,
        Fabricante fabricante
) {
}
