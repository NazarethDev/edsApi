package br.com.eds.api.eds.model.impressao;

import br.com.eds.api.eds.model.cliente.Cliente;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public class Impressao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    private String arquivoImpressao;

    @Enumerated(EnumType.STRING)
    private MaterialImpressao materialImpressao;

    @Enumerated(EnumType.STRING)
    private Dimensao dimensao;
    private Integer unidades;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataSolicitacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataAtualizacao;

    public Impressao(){}

    public Impressao (Cliente cliente, NovaImpressao novaImpressao){
        this.cliente = cliente;
        this.materialImpressao = novaImpressao.materialImpressao();
        this.dimensao = novaImpressao.dimensao();
        this.unidades = novaImpressao.unidades();
        this.dataSolicitacao = LocalDateTime.now();
    }

    public void updatePrint(UpdatePrint dadosAtualizados, String arquivoImpressao){
        if (dadosAtualizados.materialImpressao() != null){
            this.materialImpressao = dadosAtualizados.materialImpressao();
        }
        if (dadosAtualizados.dimensao() != null){
            this.dimensao = dadosAtualizados.dimensao();
        }
        if (dadosAtualizados.unidades() != null && dadosAtualizados.unidades() != 0){
            this.unidades = dadosAtualizados.unidades();
        }
        if (arquivoImpressao != null && !arquivoImpressao.isEmpty()){
            this.arquivoImpressao = arquivoImpressao;
        }
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
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

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
