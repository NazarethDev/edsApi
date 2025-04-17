package br.com.eds.api.eds.model.software;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoServicoSoftware {
    Instalação_windows("instalação do windows"),
    Instalação_Linux("instalação do Linux"),
    Instalação_macOS("instalação do macOS"),
    Formatação("formatação"),
    Backup("backup"),
    Otimização("otimização"),
    Problemas_com_vírus("problemas com vírus"),
    Instalação_pacote_office("instalação do pacote office"),
    Segurança("segurança"),
    Atualização_de_drivers("atualização de drivers"),
    Recuperação_de_memória("recuperação de memória"),
    Atualização_do_sistema("atualização do sistema"),
    Redes("redes"),
    Outro("outro");

    public final String nomeServico;

    TipoServicoSoftware(String nomeServico){
        this.nomeServico = nomeServico;
    }

    public String getNomeServico(){
        return nomeServico;
    }

    @JsonValue
    public String toJson(){
        return nomeServico;
    }

    public static TipoServicoSoftware fromJson(String value){
        for (TipoServicoSoftware s : values()){
            if (s.getNomeServico().equalsIgnoreCase(value)){
                return s;
            }
        }
        throw new IllegalArgumentException("Serviço inválido: " + value);
    }
}
