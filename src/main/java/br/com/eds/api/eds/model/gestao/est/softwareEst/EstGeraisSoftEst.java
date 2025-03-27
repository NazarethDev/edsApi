package br.com.eds.api.eds.model.gestao.est.softwareEst;

import java.util.List;

public record EstGeraisSoftEst(
        List<ServicoSoftEstatisticaDTO> servicos,
        List<DispositivoSoftEstDTO> dispositivos
) {}