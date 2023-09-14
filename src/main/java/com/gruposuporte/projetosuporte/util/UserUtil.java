package com.gruposuporte.projetosuporte.util;

import com.gruposuporte.projetosuporte.data.User;
import com.gruposuporte.projetosuporte.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class UserUtil {
    private UserRepository userRepository;

    @Autowired
    public UserUtil(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        String username = authentication.getName();
        var user = userRepository.findByUsername(username);
        return user.orElse(null);
    }

    public String getFormattedDate(Date date) {
        Date currentDate = new Date();
        long timeDifferenceInMillis = currentDate.getTime() - date.getTime();
        long days = TimeUnit.MILLISECONDS.toDays(timeDifferenceInMillis);
        long hours = TimeUnit.MILLISECONDS.toHours(timeDifferenceInMillis) - days * 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifferenceInMillis) - TimeUnit.HOURS.toMinutes(hours);
        String message;
        if (days > 0) {
            message = "Criada há " + days + (days == 1 ? " dia atrás" : " dias atrás");
        } else if (hours > 0) {
            message = "Criada há " + hours + (hours == 1 ? " hora atrás" : " horas atrás");
        } else if (minutes > 0) {
            message = "Criada há " + minutes + (minutes == 1 ? " minuto atrás" : " minutos atrás");
        } else {
            message = "Criada agora mesmo.";
        }
        return message;
    }

    public String getFormattedMessageDate(Date date) {
        return getFormattedDate(date).replace("Criada","Enviada");

    }
}


















