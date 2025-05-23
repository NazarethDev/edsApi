package br.com.eds.api.eds.service;

import br.com.eds.api.eds.model.conserto.*;
import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;
import br.com.eds.api.eds.repository.ConsertoRepository;
import br.com.eds.api.eds.service.arquivo.ArquivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ConsertoService {

    @Autowired
    ConsertoRepository consertoRepository;

    @Autowired
    ArquivoService arquivoService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    DomiciliarService domiciliarService;

    @Transactional
    public ResponseEntity createConserto(NovoConserto novo, MultipartFile file) throws IOException {
        var arquivo = arquivoService.salvarArquivo(file, null, false, true);
        var cliente = clienteService.obterOuCriarCliente(
                novo.nomeCliente(),
                novo.contatoCliente(),
                novo.contatoAlternativoCliente(),
                novo.emailCliente(),
                novo.cpf()
        );
        var domiciliar = domiciliarService.saveDomicilar(novo.domiciliar());
        var conserto = new Conserto(novo, arquivo, cliente, domiciliar);
        consertoRepository.save(conserto);
        return ResponseEntity.ok(new ConsertoResponse(conserto));
    }

    public ResponseEntity updateConserto(Long id, UpdateConserto dados, MultipartFile arquivo) throws IOException {
        var order = consertoRepository.findById(id);
        if (order.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var conserto = order.get();

        if (conserto.getStatus() == StatusServicos.CANCELADO){
            return ResponseEntity.badRequest().body("Não é possível atualizar serviço cancelado. Contate o administrador.");
        }

        String file = arquivoService.salvarArquivo(arquivo, conserto.getArquivo(), false, true);


        conserto.updateConserto(dados, file);
        consertoRepository.save(conserto);

        return ResponseEntity.ok(new ConsertoResponse(conserto));
    }

    @Transactional
    public ResponseEntity deleteOrder(Long id){
        var repair = consertoRepository.findById(id);
        if (repair.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        repair.get().setStatus(StatusServicos.CANCELADO);
        consertoRepository.save(repair.get());
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity showMyOrder(Long id, String contato, String email) {
        if (id == null && (contato == null || contato.isEmpty()) && (email == null || email.isEmpty())) {
            return ResponseEntity.badRequest().body("Por favor, informe o número do pedido ou contato do cliente.");
        }

        if (id != null) {
            var conserto = consertoRepository.findById(id);
            if (conserto.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(new ConsertoResponse(conserto.get()));
        }

        if (contato != null && !contato.isEmpty()) {
            var conserto = consertoRepository.findByCliente_ContatoCliente(contato);
            if (conserto.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            List<ConsertoResponse> consertos = conserto.stream().map(ConsertoResponse::new).toList();
            return ResponseEntity.ok(consertos);
        }

        if (email != null && !email.isEmpty()) {
            var request = consertoRepository.findByCliente_EmailCliente(email);
            if (request.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            List<ConsertoResponse> consertosSolicitados = request.stream().map(ConsertoResponse::new).toList();
            return ResponseEntity.ok(consertosSolicitados);
        }

        return ResponseEntity.badRequest().body("Por favor, forneça um identificador válido (ID, contato ou e-mail).");
    }


}
