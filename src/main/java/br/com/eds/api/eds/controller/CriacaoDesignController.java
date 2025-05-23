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
    public ResponseEntity createDesign(
            @RequestPart("data") NovaCriacaoDesign design,
            @RequestPart(value = "arquivo", required = false) MultipartFile arquivo) throws IOException {
        NovaCriacaoDesign novaCriacao = new NovaCriacaoDesign(
                design.ideiasDesign(),
                design.dadosImpressao(),
                arquivo
        );

        return designService.novoDesign(novaCriacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateDesign(@PathVariable Long id,
                                       @RequestPart( value = "data", required = false) UpdateDesign design,
                                       @RequestPart(value = "file",required = false) MultipartFile file) throws IOException {
        return designService.updateDesign(id, design, file);
    }

    @DeleteMapping("/{id}")
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
