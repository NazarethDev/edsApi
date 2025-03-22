package br.com.eds.api.eds.repository;

import br.com.eds.api.eds.model.impressao.Impressao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImpressaoRepository extends JpaRepository <Impressao, Long> {
    List <Impressao> findByContatoCliente(String contato);
}
