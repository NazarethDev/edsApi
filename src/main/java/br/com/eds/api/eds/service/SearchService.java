package br.com.eds.api.eds.service;

import br.com.eds.api.eds.model.conserto.Conserto;
import br.com.eds.api.eds.model.criacaoDesign.CriacaoDesign;
import br.com.eds.api.eds.model.criacaoDesign.CriacaoDesignResponse;
import br.com.eds.api.eds.model.impressao.Impressao;
import br.com.eds.api.eds.model.impressao.PrintResponse;
import br.com.eds.api.eds.model.software.Software;
import br.com.eds.api.eds.repository.ConsertoRepository;
import br.com.eds.api.eds.repository.CriacaoDesignRepository;
import br.com.eds.api.eds.repository.ImpressaoRepository;
import br.com.eds.api.eds.repository.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public ResponseEntity<?> search(String emailCliente, String contatoCliente) {
        if ((emailCliente == null || emailCliente.isBlank()) &&
                (contatoCliente == null || contatoCliente.isBlank())) {
            return ResponseEntity.badRequest().body("Informe pelo menos o email ou o contato do cliente.");
        }

        Map<String, Object> resultados = new HashMap<>();

        Set<Conserto> consertos = new HashSet<>();
        Set<Software> softwares = new HashSet<>();
        Set<Impressao> impressoes = new HashSet<>();
        Set<CriacaoDesign> criacoes = new HashSet<>();

        // Buscar por emailCliente
        if (!isBlank(emailCliente)) {
            consertos.addAll(consertoRepository.findByCliente_EmailCliente(emailCliente));
            softwares.addAll(softwareRepository.findByCliente_EmailCliente(emailCliente));
            impressoes.addAll(impressaoRepository.findByClienteEmailAndTipo(emailCliente));
            criacoes.addAll(criacaoDesignRepository.findByClienteEmailAndTipo(emailCliente));

        }

        // Buscar por contatoCliente
        if (!isBlank(contatoCliente)) {
            consertos.addAll(consertoRepository.findByCliente_ContatoCliente(contatoCliente));
            softwares.addAll(softwareRepository.findByCliente_ContatoCliente(contatoCliente));
            impressoes.addAll(impressaoRepository.findByClienteContatoAndTipo(contatoCliente));
            criacoes.addAll(criacaoDesignRepository.findByClienteContatoAndTipo(contatoCliente));

        }

        if (!consertos.isEmpty()) resultados.put("consertos", consertos);
        if (!softwares.isEmpty()) resultados.put("softwares", softwares);
        if (!impressoes.isEmpty()) resultados.put("impressoes", impressoes);
        if (!criacoes.isEmpty()) resultados.put("criacoesDesign", criacoes);

        return ResponseEntity.ok(resultados);
    }

    private boolean isBlank(String str) {
        return str == null || str.isBlank();
    }

}
