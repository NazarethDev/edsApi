package br.com.eds.api.eds.controller;

import br.com.eds.api.eds.model.conserto.NovoConserto;
import br.com.eds.api.eds.model.conserto.UpdateConserto;
import br.com.eds.api.eds.service.ConsertoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/conserto")
public class ConsertoController {

    @Autowired
    ConsertoService consertoService;

    @PostMapping
    public ResponseEntity novoPedido(@RequestPart("data")NovoConserto novoConserto,
                                     @RequestPart(value = "arquivo", required = false) MultipartFile arquivo) throws IOException {
        return consertoService.createConserto(novoConserto,arquivo);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateOrder(@PathVariable Long id,
                                      @RequestBody UpdateConserto dados,
                                      @RequestPart(value = "arquivo", required = false) MultipartFile arquivo) throws IOException{
        return consertoService.updateConserto(id, dados, arquivo);
    }

    @GetMapping
    public ResponseEntity showOrder(@RequestParam(required = false)Long id,
                                    @RequestParam(required = false) String contato,
                                    @RequestParam (required = false)String email){
        return consertoService.showMyOrder(id, contato, email);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable Long id){
        return consertoService.deleteOrder(id);
    }

}
