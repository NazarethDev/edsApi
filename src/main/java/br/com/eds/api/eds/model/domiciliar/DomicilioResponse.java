package br.com.eds.api.eds.model.domiciliar;

import java.time.LocalDate;

public record DomicilioResponse(
        Long id,
        String logradouro,
        String cep,
        String complemento,
        Periodo periodo,
        LocalDate data
) {
    public DomicilioResponse(Domiciliar domiciliar){
        this(
                domiciliar.getId(),
                domiciliar.getLogradouro(),
                domiciliar.getCep(),
                domiciliar.getComplemento(),
                domiciliar.getPeriodo(),
                domiciliar.getData()
        );
    }
}