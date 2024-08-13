package com.edu.ifpb.barbersole.controller;

import com.edu.ifpb.barbersole.exception.AcessoNegadoException;
import jakarta.persistence.EntityExistsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(EntityExistsException.class)
    public ModelAndView usuarioNaoEncontradoException(EntityExistsException ex) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("error", "Usuário já existe.");
        return model;
    }

    public ModelAndView acessoNegadoException(AcessoNegadoException ex) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("status", 403);
        model.addObject("error", "Operação não pode ser realizada.");
        model.addObject("message", ex.getMessage());
        return model;
    }
}
