package br.com.eds.api.eds.repository;

import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;
import br.com.eds.api.eds.model.impressao.Impressao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImpressaoRepository extends JpaRepository <Impressao, Long> {
    List<Impressao> findByCliente_ContatoCliente(String contatoCliente);

    List<Impressao> findByCliente_EmailCliente(String emailCliente);

    List<Impressao> findByCliente_ContatoClienteOrCliente_EmailCliente(String contatoCliente, String emailCliente);


    @Query(value = "SELECT COUNT(*) FROM impressao WHERE cliente_id = :clienteId", nativeQuery = true)
    Integer contarPedidosPorCliente(@Param("clienteId") Long clienteId);

    @Query(value = """
    SELECT COUNT(*) 
    FROM impressao 
    WHERE cliente_id = :clienteId 
    AND MONTH(data_solicitacao) = MONTH(CURRENT_DATE) 
    AND YEAR(data_solicitacao) = YEAR(CURRENT_DATE)
    """, nativeQuery = true)
    Integer calcularFrequenciaPedidos(@Param("clienteId") Long clienteId);

    @Query("SELECT i FROM Impressao i WHERE TYPE(i) <> CriacaoDesign AND i.status = :status")
    List<Impressao> findByStatusPrintStatus(@Param("status") StatusServicos status);

    @Query(value = "SELECT dimensao FROM impressao WHERE cliente_id = :clienteId " +
            "GROUP BY dimensao ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    String encontrarDimensaoMaisPedida(@Param("clienteId") Long clienteId);

    @Query(value = "SELECT material_impressao FROM impressao WHERE cliente_id = :clienteId " +
            "GROUP BY material_impressao ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    String encontrarMaterialMaisPedido(@Param("clienteId") Long clienteId);

    @Query(value = """
    SELECT material_impressao, COUNT(*) as total
    FROM impressao
    WHERE MONTH(data_solicitacao) = :mes AND YEAR(data_solicitacao) = :ano
    GROUP BY material_impressao
    ORDER BY total DESC;
    """, nativeQuery = true)
    List<Object[]> contarMateriaisPorMes(@Param("mes") int mes, @Param("ano") int ano);

    @Query(value = """
    SELECT produtp, COUNT(*) as total
    FROM impressao
    WHERE MONTH(data_solicitacao) = :mes AND YEAR(data_solicitacao) = :ano
    GROUP BY produto
    ORDER BY total DESC;
    """, nativeQuery = true)
    List<Object[]> produtoPorMes(@Param("mes") int mes, @Param("ano") int ano);

}
