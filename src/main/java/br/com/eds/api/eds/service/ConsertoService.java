package br.com.eds.api.eds.service;

import br.com.eds.api.eds.model.conserto.ConsertosSolicitados;
import br.com.eds.api.eds.model.conserto.UpdateConserto;
import br.com.eds.api.eds.model.conserto.Conserto;
import br.com.eds.api.eds.model.conserto.NovoConserto;
import br.com.eds.api.eds.repository.ConsertoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsertoService {

    @Autowired
    ConsertoRepository consertoRepository;

    @Autowired
    ArquivoService arquivoService;

    @Transactional
    public ResponseEntity createConserto(NovoConserto novo) throws IOException {
        var arquivo = arquivoService.salvarArquivo(novo.arquivo(),false, true);
        var conserto = new Conserto(novo, arquivo);
        return ResponseEntity.ok(conserto);
    }

    @Transactional
    public ResponseEntity updateConserto(Long id, UpdateConserto dados) throws IOException {
        var order = consertoRepository.findById(id);
        if (order.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var conserto = order.get();

        String arquivo = "";
        if (dados.arquivo() != null && !dados.arquivo().isEmpty()){
            arquivo = arquivoService.salvarArquivo(dados.arquivo(), false, true);
        }

        conserto.updateConserto(dados, arquivo);
        consertoRepository.save(conserto);

        return ResponseEntity.ok(conserto);
    }

    public ResponseEntity deleteOrder(Long id){
        var conserto = consertoRepository.findById(id);
        if (conserto.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        if (LocalDateTime.now().isAfter(conserto.get().getDataSolicitacao().plusHours(3))){
            return ResponseEntity.badRequest().body("Não é possível cancelar um pedido de orçamento depois de duas horas da solicitação.");
        }

        consertoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity showMyOrder(Long id, String contato){
        if (id == null && contato == null){
            return ResponseEntity.badRequest().body("Por favor, informe o número do pedido ou contato do cliente.");
        } else if (id != null) {
            var conserto = consertoRepository.findById(id);
            if (conserto.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(conserto.get());
        } else {
            var conserto = consertoRepository.findByContatoCliente(contato);
            if (conserto.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            List<ConsertosSolicitados> consertos = conserto.stream().map(ConsertosSolicitados::new).toList();
            return ResponseEntity.ok(consertos);
        }
    }

}
