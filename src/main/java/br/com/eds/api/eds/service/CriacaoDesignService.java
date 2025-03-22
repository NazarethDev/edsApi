package br.com.eds.api.eds.service;

import br.com.eds.api.eds.model.criacaoDesign.CriacaoDesign;
import br.com.eds.api.eds.model.criacaoDesign.NovaCriacaoDesign;
import br.com.eds.api.eds.model.criacaoDesign.PedidosDesign;
import br.com.eds.api.eds.model.criacaoDesign.UpdateDesign;
import br.com.eds.api.eds.repository.CriacaoDesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CriacaoDesignService {

    @Autowired
    ArquivoService arquivoService;

    @Autowired
    CriacaoDesignRepository criacaoDesignRepository;

    @Transactional
    public ResponseEntity novoDesign(NovaCriacaoDesign novoDesign) throws IOException {
        String caminhoArquivo = arquivoService.salvarArquivo(novoDesign.arquivoReferencia(), false,false);
        var novaCriacao = new CriacaoDesign(novoDesign, novoDesign.dadosImpressao(),caminhoArquivo);
        criacaoDesignRepository.save(novaCriacao);
        return ResponseEntity.ok(novaCriacao);
    }

    @Transactional
    public ResponseEntity<?> updateDesign(Long id,UpdateDesign dadosAtualizados) throws IOException {
        var design = criacaoDesignRepository.findById(id);
        if (design.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var criacaoDesign = design.get();

        if (LocalDateTime.now().isAfter(criacaoDesign.getDataSolicitacao().plusHours(2))){
            return ResponseEntity.badRequest().body("Não é possível alterar o pedido depois de duas horas da solicitação do serviço");
        }

        String novoArquivoReferencia = "";

        if (dadosAtualizados.arquivoreferencia() != null && !dadosAtualizados.arquivoreferencia().isEmpty()) {
            novoArquivoReferencia = arquivoService.salvarArquivo(dadosAtualizados.arquivoreferencia(), false, false);
        }

        criacaoDesign.updateDesig(dadosAtualizados, novoArquivoReferencia);
        criacaoDesignRepository.save(criacaoDesign);

        return ResponseEntity.ok(criacaoDesign);
    }

    public ResponseEntity deleteOrder (Long id) {
        var criacaoDesign = criacaoDesignRepository.findById(id);
        if (criacaoDesign.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var design = criacaoDesign.get();
        if (LocalDateTime.now().isAfter(design.getDataSolicitacao().plusHours(2))) {
            return ResponseEntity.badRequest().body("Não é possível alterar o pedido depois de duas horas da solicitação do serviço");
        }
        criacaoDesignRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity showMyOrder(Long id, String contato){
        if (id == null && contato == null){
            return ResponseEntity.badRequest().body("Por favor, informe o número do pedido ou contato do cliente.");
        } else if (id != null) {
            var design = criacaoDesignRepository.findById(id);
            if (design.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(design.get());
        } else {
            var orders = criacaoDesignRepository.findByContatoCliente(contato);
            if (orders.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            List<PedidosDesign> designs = orders.stream().map(PedidosDesign::new).toList();
            return ResponseEntity.ok(designs);
        }
    }

}
