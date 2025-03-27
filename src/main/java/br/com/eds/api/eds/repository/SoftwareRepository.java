package br.com.eds.api.eds.repository;

import br.com.eds.api.eds.model.software.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoftwareRepository extends JpaRepository <Software, Long> {
    List<Software> findByContatoCliente(String contato);

    List<Software> findByEmailCliente(String email);

    @Query(value = """
            SELECT servico 
            FROM (
                SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(servicos, ',', n.digit+1), ',', -1) AS servico 
                FROM software 
                JOIN (SELECT 0 digit UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) n 
                ON CHAR_LENGTH(servicos) - CHAR_LENGTH(REPLACE(servicos, ',', '')) >= n.digit
                WHERE cliente_id = :clienteId
            ) AS servico_expandido 
            GROUP BY servico 
            ORDER BY COUNT(*) DESC 
            LIMIT 1;
            """, nativeQuery = true)
    String encontrarServicoMaisSolicitado(@Param("clienteId") Long clienteId);

    @Query(value = """
            SELECT AVG(DATEDIFF(LEAD(data_solicitacao) OVER (PARTITION BY cliente_id ORDER BY data_solicitacao), data_solicitacao))
            FROM software WHERE cliente_id = :clienteId;
            """, nativeQuery = true)
    Double calcularFrequenciaServicos(@Param("clienteId") Long clienteId);

    @Query(value = "SELECT dispositivo FROM software WHERE cliente_id = :clienteId " +
            "GROUP BY dispositivo ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    String encontrarTipoDispositivoMaisSolicitado(@Param("clienteId") Long clienteId);

    @Query(value = """
    SELECT servico, COUNT(*) as total 
    FROM (
        SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(servicos, ',', n.digit+1), ',', -1) AS servico 
        FROM software 
        JOIN (SELECT 0 digit UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) n 
        ON CHAR_LENGTH(servicos) - CHAR_LENGTH(REPLACE(servicos, ',', '')) >= n.digit
        WHERE MONTH(data_solicitacao) = :mes AND YEAR(data_solicitacao) = :ano
    ) AS servico_expandido 
    GROUP BY servico 
    ORDER BY total DESC;
    """, nativeQuery = true)
    List<Object[]> listarServicosMaisSolicitados(@Param("mes") int mes, @Param("ano") int ano);


    @Query(value = """
    SELECT dispositivo, COUNT(*) as total 
    FROM software 
    WHERE MONTH(data_solicitacao) = :mes AND YEAR(data_solicitacao) = :ano 
    GROUP BY dispositivo 
    ORDER BY total DESC;
    """, nativeQuery = true)
    List<Object[]> listarDispositivosMaisSolicitados(@Param("mes") int mes, @Param("ano") int ano);

}