package com.gruposuporte.projetosuporte.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class PerfilController {
    @GetMapping("/perfil")
    public String perfil(){
        return "profile";
    }

}
