package br.com.eds.api.eds.controller;

import br.com.eds.api.eds.model.gestao.user.DadosAutenticacao;
import br.com.eds.api.eds.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DadosAutenticacao dados){
        return userService.entrar(dados);
    }
}
