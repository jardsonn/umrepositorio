package com.gruposuporte.projetosuporte.util;

import com.gruposuporte.projetosuporte.dto.CallRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class CreateCallValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CallRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CallRequest callRequest = (CallRequest) target;
        if (StringUtils.isEmptyOrWhitespace(callRequest.title())) {
            errors.rejectValue("title", "Titulo Vazio, preencha o campo!");
        }
        if (StringUtils.isEmptyOrWhitespace(callRequest.description())) {
            errors.rejectValue("description", "Descrição Vazia, preencha o campo!");
        }
        if (callRequest.title().length() > 60) {
            errors.rejectValue("title", "Tamanho Excedido. Maximo de 60 caracteres");
        }
        if (callRequest.description().length() > 700) {
            errors.rejectValue("description", "Tamanho Excedido. Maximo de 700 caracteres");
        }

    }
}
