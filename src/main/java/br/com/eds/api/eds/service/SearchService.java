package br.com.eds.api.eds.service;

import br.com.eds.api.eds.repository.ConsertoRepository;
import br.com.eds.api.eds.repository.CriacaoDesignRepository;
import br.com.eds.api.eds.repository.ImpressaoRepository;
import br.com.eds.api.eds.repository.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

    public ResponseEntity<?> search(String emailCliente, String contatoCliente) {
        if ((emailCliente == null || emailCliente.isBlank()) &&
                (contatoCliente == null || contatoCliente.isBlank())) {
            return ResponseEntity.badRequest().body("Informe pelo menos o email ou o contato do cliente.");
        }

        Map<String, Object> resultados = new HashMap<>();

        List<?> consertos = Collections.emptyList();
        List<?> softwares = Collections.emptyList();
        List<?> impressoes = Collections.emptyList();
        List<?> criacoes = Collections.emptyList();

        if (emailCliente != null || contatoCliente != null) {
            consertos = consertoRepository.buscarPorContatoOuEmail(contatoCliente, emailCliente);
            softwares = softwareRepository.buscarPorContatoOuEmail(contatoCliente, emailCliente);
            impressoes = impressaoRepository.buscarPorContatoOuEmail(contatoCliente, emailCliente);
            criacoes = criacaoDesignRepository.buscarPorContatoOuEmail(contatoCliente, emailCliente);
        }

        if (!consertos.isEmpty()) resultados.put("consertos", consertos);
        if (!softwares.isEmpty()) resultados.put("softwares", softwares);
        if (!impressoes.isEmpty()) resultados.put("impressoes", impressoes);
        if (!criacoes.isEmpty()) resultados.put("criacoesDesign", criacoes);

        return ResponseEntity.ok(resultados);
    }
}
