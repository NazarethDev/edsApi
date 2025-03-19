package br.com.eds.api.eds.model.criacaoDesign;

import br.com.eds.api.eds.model.impressao.Impressao;
import br.com.eds.api.eds.model.impressao.NovaImpressao;
import jakarta.persistence.*;


@Entity
@DiscriminatorValue("CRIACAO_DESIGN")
public class CriacaoDesign extends Impressao {

    private String ideiasDesign;
    private String arquivoReferencia;


    public CriacaoDesign(NovaCriacaoDesign novaCriacaoDesign, NovaImpressao dadosImpressao, String caminhoArquivo) {
        super(dadosImpressao,caminhoArquivo);
        this.ideiasDesign = novaCriacaoDesign.ideiasDesign();
        this.arquivoReferencia = caminhoArquivo;
    }

    public CriacaoDesign(){}

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

}
