package br.com.eds.api.eds.model.impressao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Produto {
    CARTÃO_DE_VISITAS("Cartão de visitas"),
    FLYER_1("Flyer 105x148mm"),
    FLYER_2("Flyer 105x210mm"),
    FLYER_3("Flyer 148x210mm"),
    FLYER_4("Flyer 210x294mm"),
    FLYER_5("Flyer 297x420mm"),
    CALENDARIO_1("Calendário A4 com foto"),
    CALENDARIO_2("Calendário A4 sem foto"),
    CALENDARIO_3("Calendário A4 com foto em um dos lados"),
    CALENDARIO_4("Calendário 15x20 sem foto"),
    CALENDARIO_5("Calendário 6,8x7,5 cm com foto (geladeira)"),
    CALDENDARIO_6("Calendário 9x15 com foto"),
    CALENDÁRIO_7("Calendário 15x20 com foto"),
    CALENDÁRIO_8("Calendário 15x20 sem foto"),
    CALENDÁRIO_9("Calendário com 12 fotos"),
    CALENDARIO_10("Calendário de mesa A4 colado"),
    MENU("Menu de mesa A4 3 faces"),
    FOLHAS_AVULSAS("Folhas avulsas"),
    ENCADERNAÇÃO("Encadernação"),
    FOTOS("Fotos"),
    FOLHAS("Folhas avulsas"),
    FOTOGRAFIA("Fotografias"),
    OUTRO("Outro");

    private final String descricaoProduto;

    Produto(String descricao) {
        this.descricaoProduto = descricao;
    }

    public String getDescricaoProduto(){
        return descricaoProduto;
    }

    @JsonValue
    public String toJson() {
        return descricaoProduto;
    }

    @JsonCreator
    public static Produto fromJson(String value) {
        for (Produto p : values()) {
            if (p.getDescricaoProduto().equalsIgnoreCase(value)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Produto inválido: " + value);
    }
}
