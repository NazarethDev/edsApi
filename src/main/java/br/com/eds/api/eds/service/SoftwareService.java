package br.com.eds.api.eds.service;

import br.com.eds.api.eds.model.software.*;
import br.com.eds.api.eds.repository.SoftwareRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SoftwareService {

    @Autowired
    SoftwareRepository softwareRepository;

    @Autowired
    ClienteService clienteService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public ResponseEntity createSoftServ(NewSoftSer dados){

        var cliente = clienteService.obterOuCriarCliente(dados.nomeCliente(), dados.contatoCliente(), dados.emailCliente(), dados.contatoAlternativoCliente(), dados.cpf());

        var softwareServ = new Software(cliente,dados);
        softwareRepository.save(softwareServ);
        return ResponseEntity.ok(softwareServ);
    }

    @Transactional
    public ResponseEntity updateSoftServ(Long id, UpdateSoftSer dados){
        var order = softwareRepository.findById(id);
        if (order.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var pedido = order.get();
        pedido.updateService(dados);

        softwareRepository.save(pedido);
        return ResponseEntity.ok(new SoftwareResponse(pedido));
    }

    public ResponseEntity showMyRequests(Long id, String contato, String email) {
        if (id == null && (contato == null || contato.isEmpty()) && (email == null || email.isEmpty())) {
            return ResponseEntity.badRequest().body("Por favor, indique um número do pedido ou contato do informado no momento da compra!");
        }

        if (id != null) {
            var request = softwareRepository.findById(id);
            if (request.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(new SoftwareResponse(request.get()));
        }

        if (contato != null && !contato.isEmpty()) {
            var request = softwareRepository.findByCliente_ContatoCliente(contato);
            if (request.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            List<SoftwareResponse> requests = request.stream().map(SoftwareResponse::new).toList();
            return ResponseEntity.ok(requests);
        }

        if (email != null && !email.isEmpty()) {
            var software = softwareRepository.findByCliente_EmailCliente(email);
            if (software.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            List<SoftwareResponse> pedidos = software.stream().map(SoftwareResponse::new).toList();
            return ResponseEntity.ok(pedidos);
        }

        return ResponseEntity.badRequest().body("Por favor, forneça um identificador válido (ID, contato ou e-mail).");
    }


    @Transactional
    public ResponseEntity deleteRequest(Long id){
            if (softwareRepository.existsById(id)){
                softwareRepository.deleteById(id);
                return ResponseEntity.noContent().build();
        } else return ResponseEntity.notFound().build();
    }

}
