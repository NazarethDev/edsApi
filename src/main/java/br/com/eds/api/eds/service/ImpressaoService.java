package br.com.eds.api.eds.service;

import br.com.eds.api.eds.model.impressao.Impressao;
import br.com.eds.api.eds.model.impressao.NovaImpressao;
import br.com.eds.api.eds.model.impressao.UpdatePrint;
import br.com.eds.api.eds.repository.ImpressaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class ImpressaoService {

    @Autowired
    ImpressaoRepository impressaoRepository;

    @Autowired
    ArquivoService arquivoService;

    @Transactional
    public ResponseEntity createPrint(NovaImpressao impressao) throws IOException {
        String arquivo = arquivoService.salvarArquivo(impressao.arquivoImpressao(), true);
        var novaImpressao = new Impressao(impressao);

        impressaoRepository.save(novaImpressao);

        return ResponseEntity.ok(novaImpressao);
    }

    @Transactional
    public ResponseEntity updatePrint(UpdatePrint dadosAtualizados) throws IOException {
        String novoArquivo = "";
        if (dadosAtualizados.arquivoImpressao() != null) {
            novoArquivo = arquivoService.salvarArquivo(dadosAtualizados.arquivoImpressao(), true);
        }

        var impressao = impressaoRepository.findById(dadosAtualizados.id())
                .orElseThrow(() -> new RuntimeException("Impressão não encontrada"));
        impressao.updatePrint(dadosAtualizados,novoArquivo);
        impressaoRepository.save(impressao);

        return ResponseEntity.ok(impressao);
    }
}
