package br.com.eds.api.eds.service;

import br.com.eds.api.eds.model.criacaoDesign.CriacaoDesign;
import br.com.eds.api.eds.model.criacaoDesign.NovaCriacaoDesign;
import br.com.eds.api.eds.model.criacaoDesign.UpdateDesign;
import br.com.eds.api.eds.repository.CriacaoDesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class CriacaoDesignService {

    @Autowired
    ArquivoService arquivoService;

    @Autowired
    CriacaoDesignRepository criacaoDesignRepository;

    @Transactional
    public ResponseEntity novoDesign(NovaCriacaoDesign novoDesign) throws IOException {
        String caminhoArquivo = arquivoService.salvarArquivo(novoDesign.arquivoReferencia(), false);
        var novaCriacao = new CriacaoDesign(novoDesign, novoDesign.dadosImpressao(),caminhoArquivo);
        criacaoDesignRepository.save(novaCriacao);
        return ResponseEntity.ok(novaCriacao);
    }

    @Transactional
    public ResponseEntity<?> updateDesign(UpdateDesign dadosAtualizados) throws IOException {
        String novoArquivoReferencia = "";
        if (dadosAtualizados.arquivoreferencia() != null && !dadosAtualizados.arquivoreferencia().isEmpty()) {
            novoArquivoReferencia = arquivoService.salvarArquivo(dadosAtualizados.arquivoreferencia(), false);
        }

        var criacaoDesign = criacaoDesignRepository.findById(dadosAtualizados.novosDadosImpressao().id())
                .orElseThrow(() -> new RuntimeException("Criação de Design não encontrada"));

        criacaoDesign.updatePrint(dadosAtualizados, novoArquivoReferencia);
        criacaoDesignRepository.save(criacaoDesign);

        return ResponseEntity.ok(criacaoDesign);
    }
}
