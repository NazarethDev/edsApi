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
        var cliente = clienteService.obterOuCriarCliente(
                impressao.nomeCliente(),
                impressao.contatoCliente(),
                impressao.contatoAlternativoCliente(),
                impressao.emailCliente(),
                impressao.cpf()
        );        var novaImpressao = new Impressao(cliente, impressao, arquivo);

        impressaoRepository.save(novaImpressao);

        return ResponseEntity.ok(new PrintResponse(novaImpressao));
    }

    @Transactional
    public ResponseEntity updatePrint(Long id, UpdatePrint dadosAtualizados, MultipartFile file) throws IOException {
        var impressao = impressaoRepository.findById(id);
        if (impressao.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var order = impressao.get();

        String novoArquivo = arquivoService.salvarArquivo(file, order.getArquivoImpressao(), true,false);

        if (LocalDateTime.now().isAfter(order.getDataSolicitacao().plusHours(2))){
            return ResponseEntity.badRequest().body("Não é possível alterar características do pedido após duas horas da solicitação :(");
        }

        order.updatePrint(dadosAtualizados,novoArquivo);
        impressaoRepository.save(order);

        return ResponseEntity.ok(new PrintResponse(order));
    }

    @Transactional
    public ResponseEntity deleteOrder(Long impressaoId){
        var impressao = impressaoRepository.findById(impressaoId);
        if (impressao.isEmpty()){
            return ResponseEntity.badRequest().body("Não encontramos o número de pedido indicado :(");
        }
        if (LocalDateTime.now().isAfter(impressao.get().getDataSolicitacao().plusHours(2))){
            return ResponseEntity.badRequest().body("Não é possível cancelar o pedido depois de duas horas");
        }
        impressaoRepository.deleteById(impressaoId);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity showMyOrder(Long id, String contato, String email) {
        if (id == null && (contato == null || contato.isEmpty()) && (email == null || email.isEmpty())) {
            return ResponseEntity.badRequest().body("Por favor, indique um número do pedido, contato ou email utilizado na compra!");
        }

        if (id != null) {
            var pedido = impressaoRepository.findById(id);
            if (pedido.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(new PrintResponse(pedido.get()));
        }

        if (contato != null && !contato.isEmpty()) {
            var pedidos = impressaoRepository.findByCliente_ContatoCliente(contato);
            if (pedidos.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            List<PrintResponse> listaPedidos = pedidos.stream().map(PrintResponse::new).toList();
            return ResponseEntity.ok(listaPedidos);
        }

        if (email != null && !email.isEmpty()) {
            var pedidos = impressaoRepository.findByCliente_EmailCliente(email);
            if (pedidos.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            List<PrintResponse> listaPedidos = pedidos.stream().map(PrintResponse::new).toList();
            return ResponseEntity.ok(listaPedidos);
        }

        return ResponseEntity.badRequest().body("Por favor, forneça um identificador válido (ID, contato ou e-mail).");
    }


}