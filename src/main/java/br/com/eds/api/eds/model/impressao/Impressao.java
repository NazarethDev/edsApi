package br.com.eds.api.eds.model.impressao;

import br.com.eds.api.eds.model.cliente.Cliente;
import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

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
    private StatusServicos status;

    private Integer unidades;

    private String ladosImpressao;

    private String coresImpressao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataSolicitacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataAtualizacao;

    @Enumerated(EnumType.STRING)
    private Produto produto;

    private final String tipoEntidade = "impressao";

    private static final Set<Produto> PRODUTOS_SEM_MATERIAL = Set.of(
            Produto.CALENDARIO_1,
            Produto.CALENDARIO_2,
            Produto.CALENDARIO_3,
            Produto.CALENDARIO_4,
            Produto.CALENDARIO_5,
            Produto.CALDENDARIO_6,
            Produto.CALENDÁRIO_7,
            Produto.CALENDÁRIO_8,
            Produto.CALENDÁRIO_9,
            Produto.CALENDARIO_10
    );


    public Impressao(){}

    public Impressao(Cliente cliente, NovaImpressao novaImpressao, String arquivo) {
        this.cliente = cliente;
        this.unidades = novaImpressao.unidades();
        this.ladosImpressao = novaImpressao.ladosImpressao();
        this.coresImpressao = novaImpressao.coresImpressao();
        this.produto = novaImpressao.produto();

        this.materialImpressao = produtoDispensaMaterial(this.produto) ? null : novaImpressao.materialImpressao();

        this.arquivoImpressao = arquivo;
        this.dataSolicitacao = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        this.status = StatusServicos.NOVO;
    }


    public void updatePrint(UpdatePrint dadosAtualizados, String arquivoImpressao){
        if (dadosAtualizados.materialImpressao() != null){
            this.materialImpressao = dadosAtualizados.materialImpressao();
        }
        if (dadosAtualizados.produto() != null){
            this.produto = dadosAtualizados.produto();
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

    private boolean produtoDispensaMaterial(Produto produto) {
        return PRODUTOS_SEM_MATERIAL.contains(produto);
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

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
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

}
