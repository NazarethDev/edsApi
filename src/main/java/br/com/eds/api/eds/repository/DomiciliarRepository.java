package br.com.eds.api.eds.repository;

import br.com.eds.api.eds.model.domiciliar.Domiciliar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomiciliarRepository extends JpaRepository <Domiciliar, Long> {
}
