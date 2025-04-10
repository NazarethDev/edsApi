package br.com.eds.api.eds.model.software;

import br.com.eds.api.eds.model.conserto.Fabricante;
import br.com.eds.api.eds.model.domiciliar.NovoEmDomicilio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record NewSoftSer(
        @NotBlank
        String nomeCliente,
        @NotBlank
        String contatoCliente,
        @NotNull
        String emailCliente,
        String contatoAlternativoCliente,
        String cpf,
        String detalhesServico,
        @NotNull
        TipoDispositivo dispositivo,
        @NotNull
        List<TipoServicoSoftware> servicos,
        @NotBlank String tempoUso,
        NovoEmDomicilio domicilio,
        Fabricante fabricante
) {
}
