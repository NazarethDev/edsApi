package br.com.eds.api.eds.model.conserto;

import br.com.eds.api.eds.model.cliente.Cliente;
import jakarta.persistence.*;

import java.time.LocalDate;

public class Conserto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricaoProblema;
    private Cliente cliente;
    private String tempoDeUso;
    @Enumerated(EnumType.STRING)
    private FabricanteNotebook fabricanteNotebook;
    @Enumerated(EnumType.STRING)
    private FabricanteMicroondas fabricanteMicroondas;
    @Enumerated(EnumType.STRING)
    private FabricanteTelevisao fabricanteTelevisao;
    private LocalDate dataSolicitacao;


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

    public FabricanteTelevisao getFabricanteTelevisao() {
        return fabricanteTelevisao;
    }

    public void setFabricanteTelevisao(FabricanteTelevisao fabricanteTelevisao) {
        this.fabricanteTelevisao = fabricanteTelevisao;
    }

    public FabricanteMicroondas getFabricanteMicroondas() {
        return fabricanteMicroondas;
    }

    public void setFabricanteMicroondas(FabricanteMicroondas fabricanteMicroondas) {
        this.fabricanteMicroondas = fabricanteMicroondas;
    }

    public FabricanteNotebook getFabricanteNotebook() {
        return fabricanteNotebook;
    }

    public void setFabricanteNotebook(FabricanteNotebook fabricanteNotebook) {
        this.fabricanteNotebook = fabricanteNotebook;
    }

    public String getTempoDeUso() {
        return tempoDeUso;
    }

    public void setTempoDeUso(String tempoDeUso) {
        this.tempoDeUso = tempoDeUso;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }
}