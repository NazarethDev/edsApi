package br.com.eds.api.eds.controller;

import br.com.eds.api.eds.model.criacaoDesign.NovaCriacaoDesign;
import br.com.eds.api.eds.model.criacaoDesign.UpdateDesign;
import br.com.eds.api.eds.service.CriacaoDesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/design")
public class CriacaoDesignController {

    @Autowired
    CriacaoDesignService designService;

    @PostMapping
    public ResponseEntity<?> createDesign(
            @RequestPart("data") NovaCriacaoDesign design,
            @RequestPart("arquivo") MultipartFile arquivo) throws IOException {
        NovaCriacaoDesign novaCriacao = new NovaCriacaoDesign(
                design.ideiasDesign(),
                design.dadosImpressao(),
                arquivo
        );

        return designService.novoDesign(novaCriacao);
    }

    @PutMapping
    public ResponseEntity updateDesign(@PathVariable Long id,@RequestBody UpdateDesign design) throws IOException {
        return designService.updateDesign(id, design);
    }

    @DeleteMapping
    public ResponseEntity deleteOrder(@PathVariable Long id){
       return designService.deleteOrder(id);
    }

    @GetMapping
    public ResponseEntity showOrder(@RequestParam(required = false)Long id,
                                     @RequestParam(required = false) String contato,
                                    @RequestParam(required = false) String email){
        return designService.showMyOrder(id,contato, email);
    }

}
