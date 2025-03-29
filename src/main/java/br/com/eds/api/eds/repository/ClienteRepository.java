package br.com.eds.api.eds.repository;

import br.com.eds.api.eds.model.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository <Cliente, Long> {
    Optional<Cliente> findByContatoClienteAndEmailCliente(String contatoCliente, String emailCliente);
}
