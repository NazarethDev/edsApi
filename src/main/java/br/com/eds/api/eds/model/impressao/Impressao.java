package br.com.eds.api.eds.model.impressao;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public class Impressao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCliente;
    private String contatoCliente;
    private String arquivoImpressao;
    @Enumerated(EnumType.STRING)
    private MaterialImpressao materialImpressao;
    @Enumerated(EnumType.STRING)
    private Dimensao dimensao;
    private Integer unidades;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataSolicitacao;

    public Impressao(){}
    public Impressao(String nomeCliente, String contatoCliente, String arquivoImpressao,
                     MaterialImpressao materialImpressao, Dimensao dimensao, Integer unidades) {
        this.nomeCliente = nomeCliente;
        this.contatoCliente = contatoCliente;
        this.arquivoImpressao = arquivoImpressao;
        this.materialImpressao = materialImpressao;
        this.dimensao = dimensao;
        this.unidades = unidades;
    }

    public Impressao (NovaImpressao novaImpressao, String caminhoArquivo){
        this.nomeCliente = novaImpressao.nomeCliente();
        this.contatoCliente = novaImpressao.contatoCliente();
        this.materialImpressao = novaImpressao.materialImpressao();
        this.dimensao = novaImpressao.dimensao();
        this.unidades = novaImpressao.unidades();
        this.arquivoImpressao = caminhoArquivo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public Dimensao getDimensao() {
        return dimensao;
    }

    public void setDimensao(Dimensao dimensao) {
        this.dimensao = dimensao;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }

    public MaterialImpressao getMaterialImpressao() {
        return materialImpressao;
    }

    public void setMaterialImpressao(MaterialImpressao materialImpressao) {
        this.materialImpressao = materialImpressao;
    }

    public String getArquivoImpressao() {
        return arquivoImpressao;
    }

    public void setArquivoImpressao(String arquivoImpressao) {
        this.arquivoImpressao = arquivoImpressao;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getContatoCliente() {
        return contatoCliente;
    }

    public void setContatoCliente(String contatoCliente) {
        this.contatoCliente = contatoCliente;
    }
}
