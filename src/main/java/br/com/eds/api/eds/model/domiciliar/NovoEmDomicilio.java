package br.com.eds.api.eds.model.domiciliar;

import java.time.LocalDate;

public record NovoEmDomicilio(
        String logradouro,
        String numeroCasa,
        String cep,
        String complemento,
        Periodo periodo,
        LocalDate data
) {
}
