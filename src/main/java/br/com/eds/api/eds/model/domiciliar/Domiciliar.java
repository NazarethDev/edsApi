package br.com.eds.api.eds.model.domiciliar;

import br.com.eds.api.eds.model.conserto.Conserto;
import br.com.eds.api.eds.model.software.Software;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;


import java.time.LocalDate;

@Entity
public class Domiciliar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logradouro;

    private String numeroCasa;

    private String cep;

    private String complemento;

    @Enumerated(EnumType.STRING)
    private Periodo periodo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    private String tipoEntidade = "emDomicilio";

    @OneToOne(mappedBy = "domicilio")
    @JsonBackReference
    private Conserto conserto;

    @OneToOne(mappedBy = "domicilio")
    @JsonBackReference
    private Software software;

    public Domiciliar(NovoEmDomicilio visita){
        this.logradouro = visita.logradouro();
        this.numeroCasa = visita.numeroCasa();
        this.periodo = visita.periodo();
        this.cep = visita.cep();
        this.complemento = visita.complemento();
        this.data = visita.data();
    }

    public Domiciliar (){}

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTipoEntidade() {
        return tipoEntidade;
    }

    public Conserto getConserto() {
        return conserto;
    }

    public void setConserto(Conserto conserto) {
        this.conserto = conserto;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
