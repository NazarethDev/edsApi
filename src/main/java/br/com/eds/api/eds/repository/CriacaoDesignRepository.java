package br.com.eds.api.eds.repository;

import br.com.eds.api.eds.model.criacaoDesign.CriacaoDesign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CriacaoDesignRepository extends JpaRepository <CriacaoDesign,Long> {
    List<CriacaoDesign> findByContatoCliente(String contato);
}
