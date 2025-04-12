package br.com.eds.api.eds.model.impressao;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MaterialImpressao {
    MATERIAL1("Papel sulfite 90g"),
    MATERIAL2("Papel sulfite 75g"),
    MATERIAL3("Papel sulfite 120g"),
    MATERIAL4("Papel monolúcido 90g"),
    MATERIAL5("Papel fotográfico auto adesivo 130g"),
    MATERIAL6("Papel fotográfico 180g"),
    MATERIAL7("Papel offset 180g"),
    MATERIAL8("Papel opaline 240g"),
    MATERIAL9("Papel canson 240g"),
    MATERIAL10("Papel canson 300g"),
    MATERIAL11("Papel fotográfico dupla face 180g"),
    MATERIAL12("Alta alvura 150g"),
    MATERIAL13("Couché brilho 90g"),
    MATERIAL14("Couché brilho 115g"),
    MATERIAL15("Couché brilho 150g"),
    MATERIAL16("Couché brilho 250g"),
    MATERIAL17("Couché fosco 115g"),
    MATERIAL18("Couché fosco 150g"),
    MATERIAL19("Couché fosco 170g"),
    MATERIAL20("Reciclado 90g"),
    MATERIAL21("Reciclado 240g"),
    MATERIAL22("Kraft 135g");

    private final String material;

    MaterialImpressao(String material){
        this.material = material;
    }

    public String getMaterial(){
        return material;
    }

    @JsonCreator
    public static MaterialImpressao fromJson(String value){
        for (MaterialImpressao m : values()){
            if (m.getMaterial().equalsIgnoreCase(value)){
                return m;
            }
        }
        throw new IllegalArgumentException("Material inválido " + value);
    }

}
