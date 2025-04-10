package br.com.eds.api.eds.model.impressao;

import br.com.eds.api.eds.model.cliente.Cliente;
import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;
import br.com.eds.api.eds.model.domiciliar.Domiciliar;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    @Enumerated(EnumType.STRING)
    private StatusServicos status;

    private Integer unidades;

    private String ladosImpressao;

    private String coresImpressao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataSolicitacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataAtualizacao;

    private final String tipoEntidade = "impressao";

    @OneToOne
    @JoinColumn(name = "domicilio_id")
    @JsonProperty("entregaCombinada")
    private Domiciliar domicilio;


    public Impressao(){}

    public Impressao (Cliente cliente, NovaImpressao novaImpressao, String arquivo){
        this.cliente = cliente;
        this.materialImpressao = novaImpressao.materialImpressao();
        this.dimensao = novaImpressao.dimensao();
        this.unidades = novaImpressao.unidades();
        this.ladosImpressao = novaImpressao.ladosImpressao();
        this.coresImpressao = novaImpressao.coresImpressao();
        this.domicilio = new Domiciliar(novaImpressao.domiciliar());
        this.arquivoImpressao = arquivo;
        this.dataSolicitacao = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        this.status = StatusServicos.NOVO;    }

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
        if (dadosAtualizados.coresImpressao() != null && !dadosAtualizados.coresImpressao().equals(coresImpressao)){
            this.coresImpressao = dadosAtualizados.coresImpressao();
        }
        if (dadosAtualizados.ladosImpressao() !=null && !dadosAtualizados.ladosImpressao().equals(ladosImpressao)){
            this.ladosImpressao = dadosAtualizados.ladosImpressao();
        }
        if (arquivoImpressao != null && !arquivoImpressao.isEmpty()){
            this.arquivoImpressao = arquivoImpressao;
        }
        this.dataAtualizacao = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
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

    public StatusServicos getStatus() {
        return status;
    }

    public void setStatus(StatusServicos status) {
        this.status = status;
    }

    public String getTipoEntidade() {
        return tipoEntidade;
    }

    public String getLadosImpressao() {
        return ladosImpressao;
    }

    public void setLadosImpressao(String ladosImpressao) {
        this.ladosImpressao = ladosImpressao;
    }

    public String getCoresImpressao() {
        return coresImpressao;
    }

    public void setCoresImpressao(String coresImpressao) {
        this.coresImpressao = coresImpressao;
    }

    public Domiciliar getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domiciliar domicilio) {
        this.domicilio = domicilio;
    }
}
