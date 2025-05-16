package br.com.eds.api.eds.repository;

import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;
import br.com.eds.api.eds.model.impressao.Impressao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ImpressaoRepository extends JpaRepository <Impressao, Long> {
    List<Impressao> findByCliente_ContatoCliente(String contatoCliente);
    List<Impressao> findByCliente_EmailCliente(String emailCliente);

    @Query("SELECT i FROM Impressao i WHERE i.cliente.emailCliente = :emailCliente AND TYPE(i) = Impressao")
    List<Impressao> findByClienteEmailAndTipo(@Param("emailCliente") String emailCliente);

    @Query("SELECT i FROM Impressao i WHERE i.cliente.contatoCliente = :contatoCliente AND TYPE(i) = Impressao")
    List<Impressao> findByClienteContatoAndTipo(@Param("contatoCliente") String contatoCliente);

    @Query("SELECT i FROM Impressao i WHERE TYPE(i) = Impressao AND i.status = :status AND i.dataSolicitacao BETWEEN :inicio AND :fim")
    List<Impressao> findSomenteImpressoesPorStatusEPeriodo(
            @Param("status") StatusServicos status,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );


    List<Impressao> findByCliente_EmailClienteOrCliente_ContatoCliente(String email, String contato);



    @Query("SELECT c FROM Impressao c WHERE " +
            "(:contatoCliente IS NULL OR c.cliente.contatoCliente = :contatoCliente) AND " +
            "(:emailCliente IS NULL OR c.cliente.emailCliente = :emailCliente)")
    List<Impressao> buscarPorContatoOuEmail(@Param("contatoCliente") String contatoCliente,
                                           @Param("emailCliente") String emailCliente);


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

    @Query("""
    SELECT i 
    FROM Impressao i 
    WHERE TYPE(i) <> CriacaoDesign 
      AND i.status = :status 
      AND i.dataSolicitacao BETWEEN :startOfMonth AND :endOfMonth
    """)
    List<Impressao> findByStatusInCurrentMonth(@Param("status") StatusServicos status,
                                               @Param("startOfMonth") LocalDateTime startOfMonth,
                                               @Param("endOfMonth") LocalDateTime endOfMonth);

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
