package br.com.eds.api.eds.service;

import br.com.eds.api.eds.model.impressao.*;
import br.com.eds.api.eds.repository.ImpressaoRepository;
import br.com.eds.api.eds.service.arquivo.ArquivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ImpressaoService {

    @Autowired
    ImpressaoRepository impressaoRepository;

    @Autowired
    ArquivoService arquivoService;

    @Autowired
    ClienteService clienteService;

    @Transactional
    public ResponseEntity createPrint(NovaImpressao impressao, MultipartFile file) throws IOException {
        String arquivo = arquivoService.salvarArquivo(file, null,true,false);
        var cliente = clienteService.obterOuCriarCliente(impressao.nomeCliente(),impressao.contatoCliente(), impressao.emailCliente());
        var novaImpressao = new Impressao(cliente, impressao, arquivo);

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

    @Transactional
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

    public ResponseEntity showMyOrder(Long id, String contato, String email) {
        if ((id == null && contato == null) || (contato != null && contato.isEmpty())) {
            return ResponseEntity.badRequest().body("Por favor, indique um número do pedido ou contato do informado no momento da compra!");
        } else if (id != null) {
            var pedido = impressaoRepository.findById(id);
            if (pedido.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(pedido.get());
        } else if (contato != null) {
            var pedido = impressaoRepository.findByCliente_ContatoCliente(contato);
            if (pedido.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            List<PedidosImpressao> pedidos = pedido.stream().map(PedidosImpressao::new).toList();
            return ResponseEntity.ok(pedidos);
        } else if (email != null && !email.isEmpty()) {
            var pedido = impressaoRepository.findByCliente_EmailCliente(email);
            if (pedido.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            List<PedidosImpressao> orders = pedido.stream().map(PedidosImpressao::new).toList();
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.badRequest().body("Por favor, forneça um identificador válido (ID, contato ou e-mail).");
        }
    }

}