package br.com.eds.api.eds.model.criacaoDesign;
import br.com.eds.api.eds.model.impressao.NovaImpressao;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record NovaCriacaoDesign(
        @NotBlank String ideiasDesign,
        @NotBlank NovaImpressao dadosImpressao,
        MultipartFile arquivo
) {
        public NovaCriacaoDesign(String ideiasDesign, NovaImpressao dadosImpressao, MultipartFile arquivo) {
                this.ideiasDesign = ideiasDesign;
                this.dadosImpressao = new NovaImpressao(
                        dadosImpressao.nomeCliente(),
                        dadosImpressao.contatoCliente(),
                        dadosImpressao.emailCliente(),
                        dadosImpressao().contatoAlternativoCliente(),
                        dadosImpressao.cpf(),
                        dadosImpressao.materialImpressao(),
                        dadosImpressao.unidades(),
                        dadosImpressao.ladosImpressao(),
                        dadosImpressao().coresImpressao(),
                        dadosImpressao.produto()
                );
                this.arquivo = arquivo;
        }
}