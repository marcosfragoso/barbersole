package com.edu.ifpb.barbersole.controller;

import com.edu.ifpb.barbersole.model.Usuario;
import com.edu.ifpb.barbersole.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.ModelMap;

import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UserService userService;

    @GetMapping("/editar")
    public String preEditar(@AuthenticationPrincipal User user, ModelMap model) {
        String username = user.getUsername();
        Optional<Usuario> usuario = userService.findByUsername(username);
        model.addAttribute("usuario", usuario);
        return "/editar";
    }
}
