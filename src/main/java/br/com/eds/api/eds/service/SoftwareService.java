package br.com.eds.api.eds.service;

import br.com.eds.api.eds.model.software.NewSoftSer;
import br.com.eds.api.eds.model.software.PedidosSoftServ;
import br.com.eds.api.eds.model.software.Software;
import br.com.eds.api.eds.model.software.UpdateSoftSer;
import br.com.eds.api.eds.repository.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SoftwareService {

    @Autowired
    SoftwareRepository softwareRepository;

    @Autowired
    ClienteService clienteService;

    @Transactional
    public ResponseEntity createSoftServ(NewSoftSer dados){

        var cliente = clienteService.obterOuCriarCliente(dados.nomeCliente(), dados.contatoCliente());

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
        return ResponseEntity.ok(pedido);
    }

    public ResponseEntity showMyRequests (Long id, String contato){
        if (id == null && contato == null || contato.isEmpty()){
            return ResponseEntity.badRequest().body("Por favor, indique um número do pedido ou contato do informado no momento da compra!");
        } else if (id != null){
            var request = softwareRepository.findById(id);
            if (request.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(request.get());
        } else {
            var request = softwareRepository.findByContatoCliente(contato);
            List<PedidosSoftServ> requests = request.stream().map(PedidosSoftServ::new).toList();
            return ResponseEntity.ok(requests);
        }
    }

    public ResponseEntity deleteRequest(Long id){
            if (softwareRepository.existsById(id)){
                softwareRepository.deleteById(id);
                return ResponseEntity.noContent().build();
        } else return ResponseEntity.notFound().build();
    }

}
