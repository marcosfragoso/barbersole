package com.edu.ifpb.barbersole.controller;

import com.edu.ifpb.barbersole.service.AgendamentoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @GetMapping("/agendar")
    public String agendar(HttpServletResponse response){
        return "agendar";
    }
}
