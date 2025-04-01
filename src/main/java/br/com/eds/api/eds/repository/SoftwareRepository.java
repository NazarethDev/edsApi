package br.com.eds.api.eds.repository;

import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;
import br.com.eds.api.eds.model.software.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SoftwareRepository extends JpaRepository <Software, Long> {
    List<Software> findByCliente_ContatoCliente(String contatoCliente);

    List<Software> findByCliente_EmailCliente(String emailCliente);

    @Query("SELECT s FROM Software s WHERE s.dataSolicitacao BETWEEN :startDate AND :endDate")
    List<Software> findAllByDataSolicitacaoBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Software> findByStatus(StatusServicos status);

}