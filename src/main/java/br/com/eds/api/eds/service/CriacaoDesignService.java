package br.com.eds.api.eds.service;

import br.com.eds.api.eds.model.criacaoDesign.*;
import br.com.eds.api.eds.repository.CriacaoDesignRepository;
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
public class CriacaoDesignService {

    @Autowired
    ArquivoService arquivoService;

    @Autowired
    CriacaoDesignRepository criacaoDesignRepository;

    @Autowired
    ClienteService clienteService;

    @Transactional
    public ResponseEntity novoDesign(NovaCriacaoDesign novoDesign) throws IOException {
        String caminhoArquivo = arquivoService.salvarArquivo(novoDesign.arquivo(), null,false,false);
        var cliente = clienteService.obterOuCriarCliente(
                novoDesign.dadosImpressao().nomeCliente(),
                novoDesign.dadosImpressao().contatoCliente(),
                novoDesign.dadosImpressao().contatoAlternativoCliente(),
                novoDesign.dadosImpressao().emailCliente(),
                novoDesign.dadosImpressao().cpf());
        var novaCriacao = new CriacaoDesign(novoDesign, novoDesign.dadosImpressao(),caminhoArquivo, cliente);
        criacaoDesignRepository.save(novaCriacao);
        return ResponseEntity.ok(new CriacaoDesignResponse(novaCriacao));
    }

    @Transactional
    public ResponseEntity<?> updateDesign(Long id,UpdateDesign dadosAtualizados, MultipartFile file) throws IOException {
        var design = criacaoDesignRepository.findById(id);
        if (design.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var criacaoDesign = design.get();

        if (LocalDateTime.now().isAfter(criacaoDesign.getDataSolicitacao().plusHours(2))){
            return ResponseEntity.badRequest().body("Não é possível alterar o pedido depois de duas horas da solicitação do serviço");
        }

        String novoArquivoReferencia = arquivoService.salvarArquivo(file, criacaoDesign.getArquivoReferencia(),false, false);

        criacaoDesign.updateDesign(dadosAtualizados, novoArquivoReferencia);
        criacaoDesignRepository.save(criacaoDesign);

        return ResponseEntity.ok(new CriacaoDesignResponse(criacaoDesign));
    }

    @Transactional
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
        if (id == null && (contato == null || contato.isEmpty()) && (email == null || email.isEmpty())) {
            return ResponseEntity.badRequest().body("Por favor, informe o número do pedido ou contato do cliente.");
        }

        if (id != null) {
            var design = criacaoDesignRepository.findById(id);
            if (design.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(new CriacaoDesignResponse(design.get()));
        }

        if (contato != null && !contato.isEmpty()) {
            var orders = criacaoDesignRepository.findByCliente_ContatoCliente(contato);
            if (orders.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            List<CriacaoDesignResponse> designs = orders.stream().map(CriacaoDesignResponse::new).toList();
            return ResponseEntity.ok(designs);
        }

        if (email != null && !email.isEmpty()) {
            var pedido = criacaoDesignRepository.findByCliente_EmailCliente(email);
            if (pedido.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            List<CriacaoDesignResponse> designs = pedido.stream().map(CriacaoDesignResponse::new).toList();
            return ResponseEntity.ok(designs);
        }

        return ResponseEntity.badRequest().body("Por favor, forneça um identificador válido (ID, contato ou e-mail).");
    }



}
