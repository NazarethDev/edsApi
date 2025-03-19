package br.com.eds.api.eds.model.software;

import br.com.eds.api.eds.model.cliente.Cliente;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class Software {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String detalhesServico;
    @Enumerated(EnumType.STRING)
    private TipoDispositivo dispositivo;
    @Enumerated(EnumType.STRING)
    private TipoServicoSoftware servico;
    private Cliente cliente;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoServicoSoftware getServico() {
        return servico;
    }

    public void setServico(TipoServicoSoftware servico) {
        this.servico = servico;
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
}
