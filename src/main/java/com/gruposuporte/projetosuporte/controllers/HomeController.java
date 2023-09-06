package com.gruposuporte.projetosuporte.controllers;

import com.gruposuporte.projetosuporte.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RequestMapping("/")
public class HomeController {

    private final UserRepository userRepository;


    @Autowired
    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/")
    public String index(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        var user = userRepository.findByUsername(username);
        model.addAttribute("current_user", user.orElse(null));
        return "index";
    }

}
