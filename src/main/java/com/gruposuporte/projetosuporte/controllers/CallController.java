package com.gruposuporte.projetosuporte.controllers;

import com.gruposuporte.projetosuporte.data.Call;
import com.gruposuporte.projetosuporte.data.Message;
import com.gruposuporte.projetosuporte.data.UserRole;
import com.gruposuporte.projetosuporte.dto.CallRequest;
import com.gruposuporte.projetosuporte.dto.ChatMessage;
import com.gruposuporte.projetosuporte.repository.CallRepository;
import com.gruposuporte.projetosuporte.repository.ChatMessageRepository;
import com.gruposuporte.projetosuporte.repository.UserRepository;
import com.gruposuporte.projetosuporte.util.CreateCallValidator;
import com.gruposuporte.projetosuporte.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

@Controller
public class CallController {
    private final CreateCallValidator createCallValidator;
    private UserRepository userRepository;
    private UserUtil userUtil;
    private CallRepository callRepository;
    private ChatMessageRepository chatMessageRepository;

    @Autowired //instanciar a classe UserRepository
    public CallController(UserRepository userRepository, CreateCallValidator createCallValidator, UserUtil userUtil, CallRepository callRepository, ChatMessageRepository chatMessageRepository) {
        this.userRepository = userRepository;
        this.createCallValidator = createCallValidator;
        this.userUtil = userUtil;
        this.callRepository = callRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    @GetMapping("/realizar-call")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        var user = userRepository.findByUsername(username);
        model.addAttribute("current_user", user.orElse(null));
        return "realizar-call";
    }

    @PostMapping("/create-call")
    public String createCall(@ModelAttribute("call") CallRequest callRequest, RedirectAttributes attributes, BindingResult bindingResult) {
        createCallValidator.validate(callRequest, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/realizar-call";
        }
        var user = userUtil.getCurrentUser();
        if (user == null) {
            return "redirect:/login";
        }
        var call = new Call(new Date(), callRequest.title(), true, callRequest.description(), user);
        callRepository.save(call);

        return "redirect:/support-chat/" + call.getId();
    }

    @GetMapping("/support-chat/{call-id}")
    public String supportChat(@PathVariable("call-id") UUID callId, @ModelAttribute("chatMessage") ChatMessage chatMessage, Model model) {
        var callOptional = callRepository.findById(callId);
        if (callOptional.isEmpty()) {
            return "redirect:/";
        }
        var user = userUtil.getCurrentUser();
        var call = callOptional.get();
        var createdDate = userUtil.getFormattedDate(call.getData());
        var messages = chatMessageRepository.getMessageByCall(call).stream().sorted(Comparator.comparing(Message::getDatetime)).toList();
        if (!call.getCostumer().getId().equals(user.getId()) && user.getRole() != UserRole.AGENT) {
            return "redirect:/";
        }
        model.addAttribute("currentUser", user);
        model.addAttribute("currentCall", call);
        model.addAttribute("createdDate", createdDate);
        model.addAttribute("messages", messages);
        model.addAttribute("userUtils", userUtil);

        return "support-chat-room";

    }


}
