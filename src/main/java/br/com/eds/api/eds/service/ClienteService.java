package br.com.eds.api.eds.service;

import br.com.eds.api.eds.model.cliente.Cliente;
import br.com.eds.api.eds.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public Cliente obterOuCriarCliente(String nomeCliente, String contatoCliente, String emailCliente) {
        return clienteRepository.findByContatoClienteAndNomeCliente(contatoCliente, nomeCliente)
                .orElseGet(() -> {
                    Cliente novoCliente = new Cliente();
                    novoCliente.setNomeCliente(nomeCliente);
                    novoCliente.setContatoCliente(contatoCliente);
                    novoCliente.setEmailCliente(emailCliente);
                    return clienteRepository.save(novoCliente);
                });
    }
}