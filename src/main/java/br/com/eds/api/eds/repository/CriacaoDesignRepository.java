package br.com.eds.api.eds.repository;

import br.com.eds.api.eds.model.criacaoDesign.CriacaoDesign;
import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CriacaoDesignRepository extends JpaRepository <CriacaoDesign,Long> {
    List<CriacaoDesign> findByCliente_ContatoCliente(String contatoCliente);
    List<CriacaoDesign> findByCliente_EmailCliente(String emailCliente);

    @Query(value = "SELECT COUNT(*) FROM impressao WHERE cliente_id = :clienteId", nativeQuery = true)
    Integer contarPedidosPorCliente(@Param("clienteId") Long clienteId);

    @Query(value = "SELECT AVG(diff) FROM ( " +
            "SELECT DATEDIFF(data_solicitacao, LAG(data_solicitacao) OVER (PARTITION BY cliente_id ORDER BY data_solicitacao)) AS diff " +
            "FROM impressao WHERE cliente_id = :clienteId ) AS subquery",
            nativeQuery = true)
    Double calcularFrequenciaPedidos(@Param("clienteId") Long clienteId);

    @Query(value = "SELECT dimensao FROM impressao WHERE cliente_id = :clienteId " +
            "GROUP BY dimensao ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    String encontrarDimensaoMaisPedida(@Param("clienteId") Long clienteId);

    @Query(value = "SELECT material_impressao FROM impressao WHERE cliente_id = :clienteId " +
            "GROUP BY material_impressao ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    String encontrarMaterialMaisPedido(@Param("clienteId") Long clienteId);

    List<CriacaoDesign> findByStatus(StatusServicos status);
}
