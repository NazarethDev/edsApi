package br.com.eds.api.eds.controller;

import br.com.eds.api.eds.model.gestao.est.DateRequest;
import br.com.eds.api.eds.model.gestao.est.softwareEst.FrequenciaServicoResponse;
import br.com.eds.api.eds.model.software.TipoServicoSoftware;
import br.com.eds.api.eds.service.gerenciamento.GestaoServiceEst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gestao")
public class GestaoController {

    @Autowired
    GestaoServiceEst gestaoService;

    @GetMapping("/printbyclient/{id}")
    public ResponseEntity estImpressaoByCliente(@PathVariable Long id){
        return gestaoService.impressaoPorCliente(id);
    }

    @GetMapping("/repairbyclient/{id}")
    public ResponseEntity estConsertoByClient(@PathVariable Long id){
        return gestaoService.consertoPorCliente(id);
    }

    @GetMapping("/designcreationbyclient/{id}")
    public ResponseEntity estCriacaoDesignByClient(@PathVariable Long id){
        return gestaoService.CriacaoDesignPorCliente(id);
    }

    @GetMapping("/printmaterialbymonth")
    public ResponseEntity printMaterialByMonth(@RequestBody DateRequest date){
        return gestaoService.estatisticasMateriaisPorMes(date.mes(), date.ano());
    }

    @GetMapping("/printdimensionbymonth")
    public ResponseEntity printDimensionByMonth(@RequestBody DateRequest date){
        return gestaoService.estatisticasDimensoesPorMes(date.mes(), date.ano());
    }

    @GetMapping("/mostrepaireddevicebymonth")
    public ResponseEntity mostRepairedDevicesByMonth(@RequestBody DateRequest date){
        return gestaoService.dispositivosMaisConsertadosPorMes(date.mes(), date.ano());
    }
    @GetMapping("/softwarerepairbymonth")
    public ResponseEntity<List<Map.Entry<TipoServicoSoftware, Long>>> softwarerepairbymonth(@RequestBody DateRequest date) {
        return gestaoService.contarServicosNoMes(date.ano(), date.mes());
    }
}
