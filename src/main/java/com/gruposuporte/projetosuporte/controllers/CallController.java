package com.gruposuporte.projetosuporte.controllers;

import com.gruposuporte.projetosuporte.dto.CallRequest;
import com.gruposuporte.projetosuporte.repository.UserRepository;
import com.gruposuporte.projetosuporte.util.CreateCallValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CallController {
    private final CreateCallValidator createCallValidator;
    private UserRepository userRepository;


    @Autowired //instanciar a classe UserRepository
    public CallController(UserRepository userRepository, CreateCallValidator createCallValidator) {
        this.userRepository = userRepository;
        this.createCallValidator = createCallValidator;
    }

    @GetMapping("/realizar-call")
    public String index(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        var user = userRepository.findByUsername(username);
        model.addAttribute("current_user", user.orElse(null));
        return "realizar-call";
    }
    @PostMapping("/create-call")
    public String createCall(@ModelAttribute("call") CallRequest callRequest, RedirectAttributes attributes, BindingResult bindingResult){

        return null;
    }

}
