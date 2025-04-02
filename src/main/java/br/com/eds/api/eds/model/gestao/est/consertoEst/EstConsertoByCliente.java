package br.com.eds.api.eds.model.gestao.est.consertoEst;

public record EstConsertoByCliente(
        Integer totalConsertos,
        String tipoProdutoMaisConsertado,
        String fabricanteMaisRecorrente,
        Integer pedidosNesteMes
) {
}
