package br.com.eds.api.eds.controller;

import br.com.eds.api.eds.model.gestao.est.DateRequest;
import br.com.eds.api.eds.service.gerenciamento.GestaoServiceEst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gestao")
public class GestaoController {

    @Autowired
    GestaoServiceEst gestaoService;

    @GetMapping("/printbyclient")
    public ResponseEntity estImpressaoByCliente(Long clienteId){
        return gestaoService.impressaoPorCliente(clienteId);
    }

    @GetMapping("/repairbyclient")
    public ResponseEntity estConsertoByClient(Long id){
        return gestaoService.consertoPorCliente(id);
    }

    @GetMapping("/softwarerepairbyclient")
    public ResponseEntity estSoftwareByClient(Long id){
        return gestaoService.softwarePorCliente(id);
    }

    @GetMapping("/designcreationbyclient")
    public ResponseEntity estCriacaoDesignByClient(Long id){
        return gestaoService.CriacaoDesignPorCliente(id);
    }

    @GetMapping("/softwarerepairbymonth")
    public ResponseEntity softwareRepairByMonth(DateRequest date){
        return gestaoService.estatisticasGeraisPorMesSoftware(date.mes(), date.ano());
    }

    @GetMapping("/printmaterialbymonth")
    public ResponseEntity printMaterialByMonth(DateRequest date){
        return gestaoService.estatisticasMateriaisPorMes(date.mes(), date.ano());
    }

    @GetMapping("/printdimensionbymonth")
    public ResponseEntity printDimensionByMonth(DateRequest date){
        return gestaoService.estatisticasDimensoesPorMes(date.mes(), date.ano());
    }

    @GetMapping("/mostrepaireddevicebymonth")
    public ResponseEntity mostRepairedDevicesByMonth(DateRequest date){
        return gestaoService.dispositivosMaisConsertadosPorMes(date.mes(), date.ano());
    }

}
