package br.com.eds.api.eds.service;

import br.com.eds.api.eds.model.impressao.*;
import br.com.eds.api.eds.repository.ImpressaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ImpressaoService {

    @Autowired
    ImpressaoRepository impressaoRepository;

    @Autowired
    ArquivoService arquivoService;

    @Transactional
    public ResponseEntity createPrint(NovaImpressao impressao) throws IOException {
        String arquivo = arquivoService.salvarArquivo(impressao.arquivoImpressao(), null,true,false);
        var novaImpressao = new Impressao(impressao);

        impressaoRepository.save(novaImpressao);

        return ResponseEntity.ok(new ResultadoNovaImpressao("Impressão criada com sucesso. Lembre-se o número de identificação!", novaImpressao));
    }

    @Transactional
    public ResponseEntity updatePrint(Long id, UpdatePrint dadosAtualizados) throws IOException {
        var impressao = impressaoRepository.findById(id);
        if (impressao.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var order = impressao.get();

        String novoArquivo = "";
        if (dadosAtualizados.arquivoImpressao() != null) {
            novoArquivo = arquivoService.salvarArquivo(dadosAtualizados.arquivoImpressao(), order.getArquivoImpressao(), true,false);
        }
        if (LocalDateTime.now().isAfter(order.getDataSolicitacao().plusHours(2))){
            return ResponseEntity.badRequest().body("Não é possível alterar características do pedido após duas horas da solicitação :(");
        }

        order.updatePrint(dadosAtualizados,novoArquivo);
        impressaoRepository.save(order);

        return ResponseEntity.ok(impressao);
    }

    public ResponseEntity deleteOrder(Long impressaoId){
        var impressao = impressaoRepository.findById(impressaoId);
        if (impressao.isEmpty()){
            return ResponseEntity.badRequest().body("Não encontramos o número de pedido indicado :(");
        }
        if (LocalDateTime.now().isAfter(impressao.get().getDataSolicitacao().plusHours(2))){
        }
        impressaoRepository.deleteById(impressaoId);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity showMyOrder(Long id, String contato) {
        if (id == null && contato == null || contato.isEmpty()){
            return ResponseEntity.badRequest().body("Por favor, indique um número do pedido ou contato do informado no momento da compra!");
        } else if (id != null){
            var pedido = impressaoRepository.findById(id);
            if (pedido.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(pedido.get());
        } else {
            var pedido = impressaoRepository.findByContatoCliente(contato);
            if (pedido.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            List<PedidosImpressao> pedidos = pedido.stream().map(PedidosImpressao::new).toList();
            return ResponseEntity.ok(pedidos);
         }
    }
}