package br.com.eds.api.eds.model.criacaoDesign;

import br.com.eds.api.eds.model.cliente.Cliente;
import br.com.eds.api.eds.model.impressao.Impressao;
import br.com.eds.api.eds.model.impressao.NovaImpressao;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@DiscriminatorValue("Criacao_design")
public class CriacaoDesign extends Impressao {
    private String ideiasDesign;
    private String arquivoReferencia;


    public CriacaoDesign(){}

    public CriacaoDesign(NovaCriacaoDesign novaCriacaoDesign, NovaImpressao dadosImpressao, String caminhoArquivo, Cliente cliente) {
        super(cliente,dadosImpressao,caminhoArquivo);
        this.ideiasDesign = novaCriacaoDesign.ideiasDesign();
        this.arquivoReferencia = caminhoArquivo;
    }

    public void updateDesign(UpdateDesign dadosAtualizados, String novaReferencia){
        if (dadosAtualizados.ideiasDesign() != null && !dadosAtualizados.ideiasDesign().isEmpty()){
            this.ideiasDesign = dadosAtualizados.ideiasDesign();
        }
        if (novaReferencia != null && !novaReferencia.isEmpty()) {
            this.arquivoReferencia = novaReferencia;
        }
        if (dadosAtualizados.novosDadosImpressao() != null){
            if (dadosAtualizados.novosDadosImpressao().materialImpressao() != null){
                setMaterialImpressao(dadosAtualizados.novosDadosImpressao().materialImpressao());
            }
            if (dadosAtualizados.novosDadosImpressao().produto() != null){
                setProduto(dadosAtualizados.novosDadosImpressao().produto());
            }
            if (dadosAtualizados.novosDadosImpressao().unidades() != null && dadosAtualizados.novosDadosImpressao().unidades() != 0){
                setUnidades(dadosAtualizados.novosDadosImpressao().unidades());
            }
        }
        this.setDataAtualizacao(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    public String getIdeiasDesign() {
        return ideiasDesign;
    }

    public void setIdeiasDesign(String ideiasDesign) {
        this.ideiasDesign = ideiasDesign;
    }

    public String getArquivoReferencia() {
        return arquivoReferencia;
    }

    public void setArquivoReferencia(String arquivoReferencia) {
        this.arquivoReferencia = arquivoReferencia;
    }

    @Override
    @Transient
    @JsonProperty("tipoEntidade")
    public String getTipoEntidade() {
        return "criacaodesign";
    }
}
