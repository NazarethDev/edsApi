package br.com.eds.api.eds.model.conserto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class Conserto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCliente;
    private String contatoCliente;
    private String descricaoProblema;
    private String arquivo;
    private String tempoDeUso;
    @Enumerated(EnumType.STRING)
    private TipoProduto tipoAparelho;
    @Enumerated(EnumType.STRING)
    private Fabricante fabricante;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataSolicitacao;

    public Conserto(NovoConserto novoConserto, String arquivo) {
        this.nomeCliente = novoConserto.nomeCliente();
        this.contatoCliente = novoConserto.contatoCliente();
        this.descricaoProblema = novoConserto.descricaoProblema();
        this.arquivo = arquivo;
        this.tempoDeUso = novoConserto.tempoDeUso();
        this.tipoAparelho = novoConserto.tipoAparelho();
        this.fabricante = novoConserto.fabricante();
        this.dataSolicitacao = LocalDateTime.now();
    }

    public void updateConserto (UpdateConserto dados, String arquivo){
        if (dados.nomeCliente() != null){
            this.nomeCliente = dados.nomeCliente();
        }
        if (dados.contatoCliente() != null){
            this.contatoCliente = dados.contatoCliente();
        }
        if (dados.descricaoProblema() != null){
            this.descricaoProblema = dados.descricaoProblema();
        }
        if (dados.tempoDeUso() != null){
            this.tempoDeUso = dados.tempoDeUso();
        }
        if (dados.tipoAparelho() != null){
            this.tipoAparelho = dados.tipoAparelho();
        }
        if (dados.fabricante() != null){
            this.fabricante = dados.fabricante();
        }
        if (arquivo != null && !arquivo.isEmpty()){
            this.arquivo = arquivo;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public String getTempoDeUso() {
        return tempoDeUso;
    }

    public void setTempoDeUso(String tempoDeUso) {
        this.tempoDeUso = tempoDeUso;
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

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public TipoProduto getTipoAparelho() {
        return tipoAparelho;
    }

    public void setTipoAparelho(TipoProduto tipoAparelho) {
        this.tipoAparelho = tipoAparelho;
    }
}