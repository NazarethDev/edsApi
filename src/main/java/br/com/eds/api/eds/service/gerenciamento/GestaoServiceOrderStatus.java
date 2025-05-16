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
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

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

    public Pair<LocalDateTime, LocalDateTime> findStatusDate(Integer mes, Integer ano){
        LocalDate firstDay = LocalDate.of(ano, mes, 1);
        LocalDate lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());

        LocalDateTime dataInicio = firstDay.atStartOfDay();
        LocalDateTime dataFim = lastDay.atTime(23,59,59,999_999_999);

        return Pair.of(dataInicio, dataFim);
    }

    public StatusServicos convertToStatusPedido (String status){
        try {
            return StatusServicos.valueOf(status.trim().toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }
    }

    public ResponseEntity statusImpressao(String status, Integer mes, Integer ano){
        var searchDate = findStatusDate(mes, ano);
        List<Impressao> impressoes = impressaoRepository.findSomenteImpressoesPorStatusEPeriodo(convertToStatusPedido(status), searchDate.getFirst(), searchDate.getSecond());
        return ResponseEntity.ok(impressoes);
    }

    public ResponseEntity statusCriacaoDesign(String status, Integer mes, Integer ano){
        var searchDate = findStatusDate(mes, ano);
        List<CriacaoDesign> designs = criacaoDesignRepository.findByStatusAndDataSolicitacaoBetween(convertToStatusPedido(status), searchDate.getFirst(), searchDate.getSecond());
        return ResponseEntity.ok(designs);
    }

    public ResponseEntity statusConserto(String status, Integer mes, Integer ano){
        var searchDate = findStatusDate(mes, ano);
        List<Conserto> consertos = consertoRepository.findByStatusAndDataSolicitacaoBetween(convertToStatusPedido(status), searchDate.getFirst(), searchDate.getSecond());
        return ResponseEntity.ok(consertos);
    }

    public ResponseEntity statusSoftware(String status, Integer mes, Integer ano){
        var searchDate = findStatusDate(mes, ano);
        List<Software> software = softwareRepository.findByStatusAndDataSolicitacaoBetween(convertToStatusPedido(status), searchDate.getFirst(), searchDate.getSecond());
        return ResponseEntity.ok(software);
    }

    public ResponseEntity<Map<String, List<?>>> allInSameState(String status) {
        var statusEnum = convertToStatusPedido(status);

        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59);

        Map<String, List<?>> responseMap = new HashMap<>();
        responseMap.put("impressoes", impressaoRepository.findByStatusInCurrentMonth(statusEnum, startOfMonth, endOfMonth));
        responseMap.put("criacoesdesign", criacaoDesignRepository.findByStatusInCurrentMonth(statusEnum, startOfMonth, endOfMonth));
        responseMap.put("consertos", consertoRepository.findByStatusInCurrentMonth(statusEnum, startOfMonth, endOfMonth));
        responseMap.put("softwares", softwareRepository.findByStatusInCurrentMonth(statusEnum, startOfMonth, endOfMonth));

        return ResponseEntity.ok(responseMap);
    }


    @Transactional
    public ResponseEntity updateStatus(String entidade, Long id, String status){
        StatusServicos novoStatus = convertToStatusPedido(status);

        switch (entidade.toLowerCase()) {
            case "impressao":
                return impressaoRepository.findById(id)
                        .map(i -> {
                            i.setStatus(novoStatus);
                            impressaoRepository.save(i);
                            return ResponseEntity.ok().build();
                        }).orElse(ResponseEntity.notFound().build());

            case "criacaodesign":
                return criacaoDesignRepository.findById(id)
                        .map(d -> {
                            d.setStatus(novoStatus);
                            criacaoDesignRepository.save(d);
                            return ResponseEntity.ok().build();
                        }).orElse(ResponseEntity.notFound().build());

            case "conserto":
                return consertoRepository.findById(id)
                        .map(c -> {
                            c.setStatus(novoStatus);
                            consertoRepository.save(c);
                            return ResponseEntity.ok().build();
                        }).orElse(ResponseEntity.notFound().build());

            case "software":
                return softwareRepository.findById(id)
                        .map(s -> {
                            s.setStatus(novoStatus);
                            softwareRepository.save(s);
                            return ResponseEntity.ok().build();
                        }).orElse(ResponseEntity.notFound().build());

            default:
                return ResponseEntity.badRequest().body("Tipo de entidade inválido.");
        }
    }

}
