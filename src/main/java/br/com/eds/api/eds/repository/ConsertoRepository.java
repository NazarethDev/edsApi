package br.com.eds.api.eds.repository;

import br.com.eds.api.eds.model.conserto.Conserto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsertoRepository extends JpaRepository <Conserto, Long> {
    List<Conserto> findByCliente_ContatoCliente(String contatoCliente);
    List<Conserto> findByCliente_EmailCliente(String emailCliente);

    @Query(value = "SELECT COUNT(*) FROM conserto WHERE cliente_id = :clienteId", nativeQuery = true)
    Integer contarSolicitacoesConserto(@Param("clienteId") Long clienteId);

    @Query(value = "SELECT tipo_aparelho FROM conserto WHERE cliente_id = :clienteId " +
            "GROUP BY tipo_aparelho ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    String encontrarTipoProdutoMaisConsertado(@Param("clienteId") Long clienteId);

    @Query(value = "SELECT fabricante FROM conserto WHERE cliente_id = :clienteId " +
            "GROUP BY fabricante ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    String encontrarFabricanteMaisRecorrente(@Param("clienteId") Long clienteId);

    @Query(value = "SELECT AVG(DATEDIFF(LEAD(data_solicitacao) OVER (PARTITION BY cliente_id ORDER BY data_solicitacao), data_solicitacao)) " +
            "FROM conserto WHERE cliente_id = :clienteId", nativeQuery = true)
    Double calcularFrequenciaConsertos(@Param("clienteId") Long clienteId);

    @Query(value = """
    SELECT tipo_aparelho, COUNT(*) as total
    FROM conserto
    WHERE MONTH(data_solicitacao) = :mes AND YEAR(data_solicitacao) = :ano
    GROUP BY tipo_aparelho
    ORDER BY total DESC;
    """, nativeQuery = true)
    List<Object[]> contarDispositivosPorMes(@Param("mes") int mes, @Param("ano") int ano);


}
