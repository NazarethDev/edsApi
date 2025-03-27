package br.com.eds.api.eds.model.conserto;

import br.com.eds.api.eds.model.cliente.Cliente;
import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Conserto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private String descricaoProblema;

    private String arquivo;

    private String tempoDeUso;

    @Enumerated(EnumType.STRING)
    private TipoProduto tipoAparelho;

    @Enumerated(EnumType.STRING)
    private Fabricante fabricante;

    @Enumerated(EnumType.STRING)
    private StatusServicos status;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataSolicitacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataAtualizacao;

    public Conserto(NovoConserto novoConserto, String arquivo, Cliente cliente) {
        this.cliente = cliente;
        this.descricaoProblema = novoConserto.descricaoProblema();
        this.arquivo = arquivo;
        this.tempoDeUso = novoConserto.tempoDeUso();
        this.tipoAparelho = novoConserto.tipoAparelho();
        this.fabricante = novoConserto.fabricante();
        this.dataSolicitacao = LocalDateTime.now();
    }

    public void updateConserto (UpdateConserto dados, String arquivo){
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
        this.dataAtualizacao = LocalDateTime.now();
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

    public TipoProduto getTipoAparelho() {
        return tipoAparelho;
    }

    public void setTipoAparelho(TipoProduto tipoAparelho) {
        this.tipoAparelho = tipoAparelho;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public StatusServicos getStatus() {
        return status;
    }

    public void setStatus(StatusServicos status) {
        this.status = status;
    }
}