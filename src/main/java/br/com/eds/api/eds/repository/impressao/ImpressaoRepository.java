package br.com.eds.api.eds.repository.impressao;

import br.com.eds.api.eds.model.impressao.Impressao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImpressaoRepository extends JpaRepository <Long, Impressao> {
}
