package com.metaphorce.challenge.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthController {

    @PostMapping("/jwt")
    public String obtenerJwt() {
        return "Obteniendo JWT desde endpoint público";
    }
}
