package br.com.eds.api.eds.repository;

import br.com.eds.api.eds.model.conserto.Conserto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsertoRepository extends JpaRepository <Conserto, Long> {
    List <Conserto> findByContatoCliente(String contato);

    List<Conserto> findByEmailCliente(String email);
}
