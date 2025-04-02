package br.com.eds.api.eds.service.gerenciamento;

import br.com.eds.api.eds.model.conserto.Conserto;
import br.com.eds.api.eds.model.criacaoDesign.CriacaoDesign;
import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;
import br.com.eds.api.eds.model.impressao.Impressao;
import br.com.eds.api.eds.model.software.Software;
import br.com.eds.api.eds.repository.ConsertoRepository;
import br.com.eds.api.eds.repository.CriacaoDesignRepository;
import br.com.eds.api.eds.repository.ImpressaoRepository;
import br.com.eds.api.eds.repository.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GestaoServiceOrderStatus {

    @Autowired
    private ConsertoRepository consertoRepository;

    @Autowired
    private CriacaoDesignRepository criacaoDesignRepository;

    @Autowired
    private ImpressaoRepository impressaoRepository;

    @Autowired
    private SoftwareRepository softwareRepository;

    public StatusServicos convertToStatusPedido (String status){
        var valorConvertido = StatusServicos.valueOf(String.valueOf(StatusServicos.valueOf(status.trim().toUpperCase().replace(" ", "_"))));
        return valorConvertido;
    }

    public ResponseEntity statusImpressao(String status){
        List<Impressao> impressoes = impressaoRepository.findByStatusPrintStatus(convertToStatusPedido(status));
        return ResponseEntity.ok(impressoes);
    }

    public ResponseEntity statusCriacaoDesign(String status){
        List<CriacaoDesign> designs = criacaoDesignRepository.findByStatus(convertToStatusPedido(status));
        return ResponseEntity.ok(designs);
    }

    public ResponseEntity statusConserto(String status){
        List<Conserto> consertos = consertoRepository.findByStatus(convertToStatusPedido(status));
        return ResponseEntity.ok(consertos);
    }

    public ResponseEntity statusSoftware (String status){
        List<Software> software = softwareRepository.findByStatus(convertToStatusPedido(status));
        return ResponseEntity.ok(software);
    }

    public ResponseEntity allInSameState(String status) {
        var statusEnum = convertToStatusPedido(status);
        List<Object> todosPedidos = new ArrayList<>();

        todosPedidos.addAll(impressaoRepository.findByStatusPrintStatus(statusEnum));

        todosPedidos.addAll(criacaoDesignRepository.findByStatus(statusEnum));

        todosPedidos.addAll(consertoRepository.findByStatus(statusEnum));

        todosPedidos.addAll(softwareRepository.findByStatus(statusEnum));

        return ResponseEntity.ok(todosPedidos);
    }

    @Transactional
    public ResponseEntity updateStatus (Long id, String status){
        StatusServicos novoStatus = convertToStatusPedido(status);
        Optional<Impressao> impressao = impressaoRepository.findById(id);
        if (impressao.isPresent() && impressao.get().getTipoEntidade().equalsIgnoreCase("impressao")) {
            impressao.get().setStatus(novoStatus);
            impressaoRepository.save(impressao.get());
            return ResponseEntity.ok(impressao.get());
        }

        Optional<CriacaoDesign> design = criacaoDesignRepository.findById(id);
        if (design.isPresent() && design.get().getTipoEntidade().equalsIgnoreCase("criacaodesign")) {
            design.get().setStatus(novoStatus);
            criacaoDesignRepository.save(design.get());
            return ResponseEntity.ok(design.get());
        }

        Optional<Conserto> conserto = consertoRepository.findById(id);
        if (conserto.isPresent() && conserto.get().getTipoEntidade().equalsIgnoreCase("conserto")) {
            conserto.get().setStatus(novoStatus);
            consertoRepository.save(conserto.get());
            return ResponseEntity.ok(conserto.get());
        }

        Optional<Software> software = softwareRepository.findById(id);
        if (software.isPresent() && software.get().getTipoEntidade().equalsIgnoreCase("software")) {
            software.get().setStatus(novoStatus);
            softwareRepository.save(software.get());
            return ResponseEntity.ok(software.get());
        }

        return ResponseEntity.notFound().build();
    }
}
