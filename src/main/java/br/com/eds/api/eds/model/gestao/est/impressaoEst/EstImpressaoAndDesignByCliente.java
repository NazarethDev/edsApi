package br.com.eds.api.eds.model.gestao.est.impressaoEst;

public record EstImpressaoAndDesignByCliente(
        Integer totalPedidos,
        Integer pedidosNesteMes,
        String produtoMaisPedido,
        String materialMaisPedido
) {}

