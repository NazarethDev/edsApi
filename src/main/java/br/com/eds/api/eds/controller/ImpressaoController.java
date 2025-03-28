package br.com.eds.api.eds.controller;

import br.com.eds.api.eds.model.impressao.NovaImpressao;
import br.com.eds.api.eds.model.impressao.UpdatePrint;
import br.com.eds.api.eds.service.ImpressaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/print")
public class ImpressaoController {

    @Autowired
    ImpressaoService impressaoService;

    @PostMapping
    public ResponseEntity novaImpressao(@RequestPart("data") NovaImpressao novaImpressao,
                                        @RequestPart("arquivo") MultipartFile arquivo) throws IOException {
        return impressaoService.createPrint(novaImpressao, arquivo);
    }

    @GetMapping
    public ResponseEntity showPrint(@RequestParam(required = false) Long id,
                                    @RequestParam(required = false) String contato,
                                    @RequestParam(required = false) String email){
        return impressaoService.showMyOrder(id,contato, email);
    }

    @PutMapping
    public ResponseEntity updatePrint(@PathVariable Long id, @RequestBody UpdatePrint dados) throws IOException {
        return impressaoService.updatePrint(id, dados);
    }

    @DeleteMapping
    public ResponseEntity deletePrint(Long id){
        return impressaoService.deleteOrder(id);
    }

}
