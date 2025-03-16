package br.com.eds.api.eds.repository;

import br.com.eds.api.eds.model.conserto.Conserto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsertoRepository extends JpaRepository <Long, Conserto> {
}
