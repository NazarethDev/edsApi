package br.com.eds.api.eds.model.gestao.est.impressaoEst;

public record EstImpressaoAndDesignByCliente(
        Integer totalPedidos,
        Double frequenciaDias,
        String dimensaoMaisPedida,
        String materialMaisPedido
) {}

