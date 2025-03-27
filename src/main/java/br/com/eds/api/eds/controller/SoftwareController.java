package br.com.eds.api.eds.controller;

import br.com.eds.api.eds.model.software.NewSoftSer;
import br.com.eds.api.eds.model.software.UpdateSoftSer;
import br.com.eds.api.eds.service.SoftwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/software")
public class SoftwareController {

    @Autowired
    SoftwareService softwareService;

    @PostMapping
    public ResponseEntity novoSoftServ(NewSoftSer novo){
        return softwareService.createSoftServ(novo);
    }

    @PutMapping
    public ResponseEntity updateSoftServ(Long id,UpdateSoftSer update){
        return softwareService.updateSoftServ(id, update);
    }

    @GetMapping
    public ResponseEntity showMyRequest(@RequestParam(required = false)Long id,
                                        @RequestParam(required = false)String contato,
                                        @RequestParam(required = false) String email){
        return softwareService.showMyRequests(id,contato,email);
    }

    @DeleteMapping
    public ResponseEntity deleteRequest(Long id){
        return softwareService.deleteRequest(id);
    }
}
