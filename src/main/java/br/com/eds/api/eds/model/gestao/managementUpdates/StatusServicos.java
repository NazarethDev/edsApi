package br.com.eds.api.eds.model.gestao.managementUpdates;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusServicos {
    NOVO("novo"),
    CLIENTE_CONTATADO("cliente contatato"),
    EM_ESPERA("em espera"),
    PEDIDO_CONFIRMADO("pedido confirmado"),
    PROCESSANDO("processando"),
    AGUARDA_RETIRADA("aguarda retirada"),
    ENTREGA_SOLICITADA("entrega solicitada"),
    FINALIZADO("finalzado"),
    CANCELADO("cancelado");

    private final String statusPedido;

    StatusServicos(String statusPedido){
        this.statusPedido = statusPedido;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    @JsonValue
    public String toJson(){
        return statusPedido;
    }
    @JsonCreator
    public static StatusServicos fromJson(String value){
        for (StatusServicos s : values()){
            if (s.getStatusPedido().equalsIgnoreCase(value)){
                return s;
            }
        }
        throw new IllegalArgumentException("Status inválido " + value);
    }
}
