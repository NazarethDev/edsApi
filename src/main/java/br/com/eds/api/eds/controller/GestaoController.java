package br.com.eds.api.eds.controller;

import br.com.eds.api.eds.model.gestao.est.DateRequest;
import br.com.eds.api.eds.model.software.TipoServicoSoftware;
import br.com.eds.api.eds.service.gerenciamento.GestaoServiceEst;
import br.com.eds.api.eds.service.gerenciamento.GestaoServiceOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gestao")
public class GestaoController {

    @Autowired
    GestaoServiceEst gestaoEstService;

    @Autowired
    GestaoServiceOrderStatus statusService;

    @GetMapping("/printbyclient/{id}")
    public ResponseEntity estImpressaoByCliente(@PathVariable Long id){
        return gestaoEstService.impressaoPorCliente(id);
    }

    @GetMapping("/repairbyclient/{id}")
    public ResponseEntity estConsertoByClient(@PathVariable Long id){
        return gestaoEstService.consertoPorCliente(id);
    }

    @GetMapping("/designcreationbyclient/{id}")
    public ResponseEntity estCriacaoDesignByClient(@PathVariable Long id){
        return gestaoEstService.CriacaoDesignPorCliente(id);
    }

    @GetMapping("/printmaterialbymonth")
    public ResponseEntity printMaterialByMonth(@RequestBody DateRequest date){
        return gestaoEstService.estatisticasMateriaisPorMes(date.mes(), date.ano());
    }

    @GetMapping("/printdimensionbymonth")
    public ResponseEntity printDimensionByMonth(@RequestBody DateRequest date){
        return gestaoEstService.estatisticasDimensoesPorMes(date.mes(), date.ano());
    }

    @GetMapping("/mostrepaireddevicebymonth")
    public ResponseEntity mostRepairedDevicesByMonth(@RequestBody DateRequest date){
        return gestaoEstService.dispositivosMaisConsertadosPorMes(date.mes(), date.ano());
    }
    @GetMapping("/softwarerepairbymonth")
    public ResponseEntity<List<Map.Entry<TipoServicoSoftware, Long>>> softwarerepairbymonth(@RequestBody DateRequest date) {
        return gestaoEstService.contarServicosNoMes(date.ano(), date.mes());
    }

    @GetMapping("/findallinatstatus")
    public ResponseEntity findInTheSameState(@RequestParam String status){
        return statusService.allInSameState(status);
    }

    @GetMapping("/findbyprintstatus")
    public ResponseEntity allPrintsInStatus(@RequestParam String status){
        return statusService.statusImpressao(status);
    }

    @GetMapping("/findbydesignstatus")
    public ResponseEntity allDesignsInAStatus(@RequestParam String status){
        return statusService.statusCriacaoDesign(status);
    }

    @GetMapping("/findbysoftwarestatus")
    public ResponseEntity allSoftwareServicesInAStatus(@RequestParam String status){
        return statusService.statusSoftware(status);
    }

    @GetMapping("/findbyrepairstatus")
    public ResponseEntity allRepairsInAStatus(@RequestParam String status){
        return statusService.statusConserto(status);
    }

    @PostMapping ("/changestatus")
    public ResponseEntity updateOrderStatus(@RequestParam Long id,
                                           @RequestParam String status){
        return statusService.updateStatus(id, status);
    }

}
