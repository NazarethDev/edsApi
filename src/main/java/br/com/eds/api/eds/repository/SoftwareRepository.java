package br.com.eds.api.eds.repository;

import br.com.eds.api.eds.model.software.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoftwareRepository extends JpaRepository <Software, Long> {
    List<Software> findByContatoCliente(String contato);

    List<Software> findByEmailCliente(String email);
}
