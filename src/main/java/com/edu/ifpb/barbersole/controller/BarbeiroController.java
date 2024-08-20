package com.edu.ifpb.barbersole.controller;

import com.edu.ifpb.barbersole.model.Usuario;
import com.edu.ifpb.barbersole.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/barbeiros")
public class BarbeiroController {

    @Autowired
    private UserService userService;

    @GetMapping({"/register"})
    public String register(HttpServletResponse response, ModelMap model) {
        model.addAttribute("barbeiro", new Usuario());
        return "barbeiro";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr){
        if (result.hasErrors()) {
            return "barbeiros/register";
        }
        userService.salvarBarbeiro(usuario);
        attr.addFlashAttribute("sucesso", "Barbeiro registrado com sucesso!");
        return "redirect:/barbeiros/register";
    }

    @GetMapping("/barbeiroLista")
    public String barbeiroLista(ModelMap model) {
        List<Usuario> barbeiros = userService.listarBarbeiros();
        model.addAttribute("barbeiros", barbeiros);
        return "barbeiroLista";
    }
}
