package br.com.eds.api.eds.model.impressao;

public record UpdatePrint(
        String nomeCliente,
        String contatoCliente,
        MaterialImpressao materialImpressao,
        Dimensao dimensao,
        Integer unidades,
        String ladosImpressao,
        String coresImpressao

) {
}
