package br.com.eds.api.eds.model.domiciliar;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Periodo {
    MANHA("manhã"),
    TARDE("tarde"),
    NOITE("noite"),
    INDIFERENTE("indiferente");

    private final String periodoDia;

    Periodo(String periodo){
        this.periodoDia = periodo;
    }

    public String getPeriodoDia(){
        return periodoDia;
    }

    @JsonValue
    public String toJson(){
        return periodoDia;
    }

    public static Periodo fromJson(String valor){
        for (Periodo p : values()){
            if (p.getPeriodoDia().equalsIgnoreCase(valor)){
                return p;
            }
        }
        throw new IllegalArgumentException("Período inválido: " + valor);
    }
}
