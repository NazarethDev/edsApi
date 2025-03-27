package br.com.eds.api.eds.model.gestao.est.softwareEst;

public record EstSoftwareByCliente(
        String servicoMaisSolicitado,
        Double frequenciaDias,
        String tipoDispositivoMaisSolicitado
) {
}
