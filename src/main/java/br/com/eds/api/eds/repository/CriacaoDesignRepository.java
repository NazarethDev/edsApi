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

    List<CriacaoDesign> findByCliente_ContatoClienteOrCliente_EmailCliente(String contatoCliente, String emailCliente);


    @Query(value = "SELECT COUNT(*) FROM impressao WHERE cliente_id = :clienteId", nativeQuery = true)
    Integer contarPedidosPorCliente(@Param("clienteId") Long clienteId);

    @Query(value = """
    SELECT COUNT(*) 
    FROM criacao_design cd
    INNER JOIN impressao i ON cd.id = i.id
    WHERE i.cliente_id = :clienteId
    AND MONTH(i.data_solicitacao) = MONTH(CURRENT_DATE)
    AND YEAR(i.data_solicitacao) = YEAR(CURRENT_DATE)
    """, nativeQuery = true)
    Integer calcularFrequenciaCriacaoDesign(@Param("clienteId") Long clienteId);


    @Query(value = "SELECT produto FROM impressao WHERE cliente_id = :clienteId " +
            "GROUP BY produto ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    String encontrarProdutoMaisPedida(@Param("clienteId") Long clienteId);

    @Query(value = "SELECT material_impressao FROM impressao WHERE cliente_id = :clienteId " +
            "GROUP BY material_impressao ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    String encontrarMaterialMaisPedido(@Param("clienteId") Long clienteId);

    List<CriacaoDesign> findByStatus(StatusServicos status);
}
