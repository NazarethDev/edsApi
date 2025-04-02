package br.com.eds.api.eds.model.gestao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class GestaoUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String email;
    private String passwaord;
}
