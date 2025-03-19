package br.com.eds.api.eds.model.conserto;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Conserto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricaoProblema;
    private String nomeCliente;
    private String contatoCliente;
    private String tempoDeUso;
    @Enumerated(EnumType.STRING)
    private TipoProduto tipoAparelho;
    @Enumerated(EnumType.STRING)
    private Fabricante fabricante;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataSolicitacao;
//    @Enumerated(EnumType.STRING)
//    private FabricanteNotebook fabricanteNotebook;
//    @Enumerated(EnumType.STRING)
//    private FabricanteMicroondas fabricanteMicroondas;
//    @Enumerated(EnumType.STRING)
//    private FabricanteTelevisao fabricanteTelevisao;


    public Conserto(NovoConserto novoConserto){
        this.dataSolicitacao = LocalDate.now();
        this.nomeCliente = novoConserto.nomeCliente();
        this.contatoCliente = novoConserto.contatoCliente();
        this.descricaoProblema = novoConserto.descricaoProblema();
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

//    public FabricanteTelevisao getFabricanteTelevisao() {
//        return fabricanteTelevisao;
//    }
//
//    public void setFabricanteTelevisao(FabricanteTelevisao fabricanteTelevisao) {
//        this.fabricanteTelevisao = fabricanteTelevisao;
//    }

//    public FabricanteMicroondas getFabricanteMicroondas() {
//        return fabricanteMicroondas;
//    }
//
//    public void setFabricanteMicroondas(FabricanteMicroondas fabricanteMicroondas) {
//        this.fabricanteMicroondas = fabricanteMicroondas;
//    }

//    public FabricanteNotebook getFabricanteNotebook() {
//        return fabricanteNotebook;
//    }
//
//    public void setFabricanteNotebook(FabricanteNotebook fabricanteNotebook) {
//        this.fabricanteNotebook = fabricanteNotebook;
//    }

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