package br.com.eds.api.eds.service;

import br.com.eds.api.eds.model.cliente.Cliente;
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

    @Autowired
    ClienteService clienteService;

    @Transactional
    public ResponseEntity novoDesign(NovaCriacaoDesign novoDesign) throws IOException {
        String caminhoArquivo = arquivoService.salvarArquivo(novoDesign.arquivoReferencia(),null,false,false);
        Cliente cliente = clienteService.obterOuCriarCliente(novoDesign.dadosImpressao().nomeCliente(),novoDesign.dadosImpressao().contatoCliente(), novoDesign.dadosImpressao().emailCliente());
        var novaCriacao = new CriacaoDesign(novoDesign, novoDesign.dadosImpressao(),caminhoArquivo, cliente);
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
            novoArquivoReferencia = arquivoService.salvarArquivo(dadosAtualizados.arquivoreferencia(), criacaoDesign.getArquivoReferencia(),false, false);
        }

        criacaoDesign.updateDesign(dadosAtualizados, novoArquivoReferencia);
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
            return ResponseEntity.badRequest().body("Não é possível excluir o pedido depois de duas horas da solicitação do serviço");
        }
        criacaoDesignRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity showMyOrder(Long id, String contato, String email) {
        if ((id == null && contato == null) || (contato != null && contato.isEmpty())) {
            return ResponseEntity.badRequest().body("Por favor, informe o número do pedido ou contato do cliente.");
        } else if (id != null) {
            var design = criacaoDesignRepository.findById(id);
            if (design.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(design.get());
        } else if (contato != null) {
            var orders = criacaoDesignRepository.findByContatoCliente(contato);
            if (orders.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            List<PedidosDesign> designs = orders.stream().map(PedidosDesign::new).toList();
            return ResponseEntity.ok(designs);
        } else if (email != null && !email.isEmpty()) {
            var pedido = criacaoDesignRepository.findByEmailCliente(email);
            if (pedido.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            List<PedidosDesign> designs = pedido.stream().map(PedidosDesign::new).toList();
            return ResponseEntity.ok(designs);
        } else {
            return ResponseEntity.badRequest().body("Por favor, forneça um identificador válido (ID, contato ou e-mail).");
        }
    }


}
