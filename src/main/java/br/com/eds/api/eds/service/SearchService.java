package br.com.eds.api.eds.service;

import br.com.eds.api.eds.repository.ConsertoRepository;
import br.com.eds.api.eds.repository.CriacaoDesignRepository;
import br.com.eds.api.eds.repository.ImpressaoRepository;
import br.com.eds.api.eds.repository.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchService {

    @Autowired
    ConsertoRepository consertoRepository;

    @Autowired
    CriacaoDesignRepository criacaoDesignRepository;

    @Autowired
    ImpressaoRepository impressaoRepository;

    @Autowired
    SoftwareRepository softwareRepository;

    public ResponseEntity search(String emailCliente, String contatoCliente){
        Map<String, Object> resultados = new HashMap<>();

        var consertos = consertoRepository.findByCliente_ContatoClienteOrCliente_EmailCliente(contatoCliente, emailCliente);
        var softwares = softwareRepository.findByCliente_ContatoClienteOrCliente_EmailCliente(contatoCliente,emailCliente);
        var impressoes = impressaoRepository.findByCliente_ContatoClienteOrCliente_EmailCliente(contatoCliente, emailCliente);
        var criacaoDesign = criacaoDesignRepository.findByCliente_ContatoClienteOrCliente_EmailCliente(contatoCliente, emailCliente);

        if (!consertos.isEmpty()){
            resultados.put("consertos", consertos);
        }
        if (!softwares.isEmpty()){
            resultados.put("software", softwares);
        }

        if (!impressoes.isEmpty()){
            resultados.put("impressoes", impressoes);
        }

        if (!criacaoDesign.isEmpty()){
            resultados.put("criacoesDesign", criacaoDesign);
        }

        return ResponseEntity.ok(resultados);
    }

}
