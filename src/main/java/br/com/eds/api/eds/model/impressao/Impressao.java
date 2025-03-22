package br.com.eds.api.eds.model.impressao;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

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

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataSolicitacao;

    public Impressao(){}

    public Impressao (NovaImpressao novaImpressao){
        this.nomeCliente = novaImpressao.nomeCliente();
        this.contatoCliente = novaImpressao.contatoCliente();
        this.materialImpressao = novaImpressao.materialImpressao();
        this.dimensao = novaImpressao.dimensao();
        this.unidades = novaImpressao.unidades();
        this.dataSolicitacao = LocalDateTime.now();
    }

    public void updatePrint(UpdatePrint dadosAtualizados, String arquivoImpressao){
        if (LocalDateTime.now().isAfter(dataSolicitacao.plusHours(2))){
            throw new RuntimeException("Não é mais possível alterar o serviço de design após duas horas desde a solicitação");
        }
        if (dadosAtualizados.nomeCliente() != null){
            this.nomeCliente = dadosAtualizados.nomeCliente();
        }
        if (dadosAtualizados.contatoCliente() != null){
            this.contatoCliente = dadosAtualizados.contatoCliente();
        }
        if (dadosAtualizados.materialImpressao() != null){
            this.materialImpressao = dadosAtualizados.materialImpressao();
        }
        if (dadosAtualizados.dimensao() != null){
            this.dimensao = dadosAtualizados.dimensao();
        }
        if (dadosAtualizados.unidades() != null){
            this.unidades = dadosAtualizados.unidades();
        }
        if (arquivoImpressao != null && !arquivoImpressao.isEmpty()){
            this.arquivoImpressao = arquivoImpressao;
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
