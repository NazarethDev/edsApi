package br.com.eds.api.eds.repository.criacaoDesign;

import br.com.eds.api.eds.model.criacaoDesign.CriacaoDesign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArquivoCriacaoDesignRepository extends JpaRepository <Long, CriacaoDesign> {
}
