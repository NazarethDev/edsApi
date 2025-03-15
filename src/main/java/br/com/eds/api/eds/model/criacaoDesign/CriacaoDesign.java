package br.com.eds.api.eds.model.criacaoDesign;

import br.com.eds.api.eds.model.cliente.Cliente;
import br.com.eds.api.eds.model.impressao.Impressao;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class CriacaoDesign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ideiasDesign;
    private String arquivoReferencia;
    private Impressao impressao;
    private Cliente cliente;
    private LocalDate dataSolicitacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdeiasDesign() {
        return ideiasDesign;
    }

    public void setIdeiasDesign(String ideiasDesign) {
        this.ideiasDesign = ideiasDesign;
    }

    public String getArquivoReferencia() {
        return arquivoReferencia;
    }

    public void setArquivoReferencia(String arquivoReferencia) {
        this.arquivoReferencia = arquivoReferencia;
    }

    public Impressao getImpressao() {
        return impressao;
    }

    public void setImpressao(Impressao impressao) {
        this.impressao = impressao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }
}
