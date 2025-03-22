package br.com.eds.api.eds.controller;

import br.com.eds.api.eds.model.conserto.NovoConserto;
import br.com.eds.api.eds.model.conserto.UpdateConserto;
import br.com.eds.api.eds.service.ConsertoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/conserto")
public class ConsertoController {

    @Autowired
    ConsertoService consertoService;

    @PostMapping
    public ResponseEntity novoPedido(@RequestBody NovoConserto novoConserto) throws IOException {
        return consertoService.createConserto(novoConserto);
    }

    @PutMapping
    public ResponseEntity updateOrder(@PathVariable Long id, @RequestBody UpdateConserto dados) throws IOException{
        return consertoService.updateConserto(id, dados);
    }
    @GetMapping
    public ResponseEntity showOrder(@RequestParam(required = false)Long id,
                                    @RequestParam(required = false) String contato){
        return consertoService.showMyOrder(id, contato);
    }

    @DeleteMapping
    public ResponseEntity deleteOrder(@PathVariable Long id){
        return consertoService.deleteOrder(id);
    }

}
