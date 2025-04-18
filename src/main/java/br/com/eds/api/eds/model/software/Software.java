package br.com.eds.api.eds.model.software;
import br.com.eds.api.eds.model.cliente.Cliente;

import br.com.eds.api.eds.model.conserto.Fabricante;
import br.com.eds.api.eds.model.gestao.managementUpdates.StatusServicos;
import br.com.eds.api.eds.model.domiciliar.Domiciliar;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Software {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private String detalhesServico;

    @Enumerated(EnumType.STRING)
    private TipoDispositivo dispositivo;

    @ElementCollection(targetClass = TipoServicoSoftware.class)
    @CollectionTable(name = "software_servicos", joinColumns = @JoinColumn(name = "software_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "servico")
    private List<TipoServicoSoftware> servicos;

    @Enumerated(EnumType.STRING)
    private StatusServicos status;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataSolicitacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    private final String tipoEntidade = "software";

    private String tempoUso;

    @Enumerated(EnumType.STRING)
    private Fabricante fabricante;

    private String detalhesDoServico;


    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "domicilio_id")
    @JsonManagedReference
    private Domiciliar domicilio;

    public Software() {
    }

    public Software (Cliente cliente, NewSoftSer dados, Domiciliar domicilio){
        this.cliente = cliente;
        this.detalhesServico = dados.detalhesServico();
        this.dispositivo = dados.dispositivo();
        this.domicilio = domicilio;
        this.servicos = new ArrayList<>(dados.servicos());
        this.tempoUso = dados.tempoUso();
        this.fabricante = dados.fabricante();
        this.domicilio = new Domiciliar(dados.domicilio());
        this.dataSolicitacao = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        this.status = StatusServicos.NOVO;
    }

    public void updateService(UpdateSoftSer dados){
        this.dataAtualizacao = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        System.out.println("Data de atualização: " + this.dataAtualizacao);
        if (dados.detalhesServico() != null && !dados.detalhesServico().isEmpty()){
            this.detalhesServico = dados.detalhesServico();
        }
        if (dados.dispositivo() != null){
            this.dispositivo = dados.dispositivo();
        }
        if (dados.tempoUso() != null){
            this.tempoUso = dados.tempoUso();
        }
        if (dados.servicos() != null && !dados.servicos().isEmpty()){
            this.servicos = dados.servicos();
        }
        if (dados.fabricante() != null){
            this.fabricante = dados.fabricante();
        }
        if (dados.detalhesServico() != null && !detalhesDoServico.equals(this.detalhesDoServico)){
            this.detalhesDoServico = dados.detalhesServico();
        }
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

    public List<TipoServicoSoftware> getServicos() {
        return servicos;
    }

    public void setServicos(List<TipoServicoSoftware> servicos) {
        this.servicos = servicos;
    }

    public TipoDispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(TipoDispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public String getDetalhesServico() {
        return detalhesServico;
    }

    public void setDetalhesServico(String detalhesServico) {
        this.detalhesServico = detalhesServico;
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

    public String getTempoUso() {
        return tempoUso;
    }

    public void setTempoUso(String tempoUso) {
        this.tempoUso = tempoUso;
    }

    public Domiciliar getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domiciliar domicilio) {
        this.domicilio = domicilio;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }
}
