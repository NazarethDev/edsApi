package br.com.eds.api.eds.service.gerenciamento;

import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;
import br.com.eds.api.eds.repository.ConsertoRepository;
import br.com.eds.api.eds.repository.CriacaoDesignRepository;
import br.com.eds.api.eds.repository.ImpressaoRepository;
import br.com.eds.api.eds.repository.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GestaoServiceUpdates {

    @Autowired
    private ConsertoRepository consertoRepository;

    @Autowired
    private CriacaoDesignRepository criacaoDesignRepository;

    @Autowired
    private ImpressaoRepository impressaoRepository;

    @Autowired
    private SoftwareRepository softwareRepository;

    public void atualizarStatusServico(String tipoServico, Long id, StatusServicos novoStatus) {
        switch (tipoServico.toLowerCase()) {
            case "impressao":
                impressaoRepository.findById(id).ifPresent(servico -> {
                    servico.setStatus(novoStatus);
                    servico.setDataAtualizacao(LocalDateTime.now());
                    impressaoRepository.save(servico);
                });
                break;
            case "criacaodesign":
                criacaoDesignRepository.findById(id).ifPresent(servico -> {
                    servico.setStatus(novoStatus);
                    servico.setDataAtualizacao(LocalDateTime.now());
                    criacaoDesignRepository.save(servico);
                });
                break;
            case "conserto":
                consertoRepository.findById(id).ifPresent(servico -> {
                    servico.setStatus(novoStatus);
                    servico.setDataAtualizacao(LocalDateTime.now());
                    consertoRepository.save(servico);
                });
                break;
            case "software":
                softwareRepository.findById(id).ifPresent(servico -> {
                    servico.setStatus(novoStatus);
                    servico.setDataAtualizacao(LocalDateTime.now());
                    softwareRepository.save(servico);
                });
                break;
            default:
                throw new IllegalArgumentException("Tipo de serviço inválido: " + tipoServico);
        }
    }
}
