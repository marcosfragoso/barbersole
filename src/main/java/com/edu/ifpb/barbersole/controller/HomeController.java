package com.edu.ifpb.barbersole.controller;

import com.edu.ifpb.barbersole.model.Usuario;
import com.edu.ifpb.barbersole.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping({"/"})
    public String index(HttpServletResponse response) {
        return "index";
    }

    @GetMapping({"/register"})
    public String register(HttpServletResponse response, ModelMap model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr){
        if (result.hasErrors()) {
            return "register";
        }
        userService.salvarUsuario(usuario);
        attr.addFlashAttribute("sucesso", "Usuário registrado com sucesso!");
        return "redirect:/register";
    }

    @GetMapping({"/login"})
    public String login(HttpServletResponse response) {
        return "login";
    }

    @GetMapping({"/home"})
    public String home(HttpServletResponse response) {
        return "home";
    }

    @GetMapping({"/login-error"})
    public String loginError(ModelMap model, HttpServletRequest resp) {
        HttpSession session = resp.getSession();
        String lastException = String.valueOf(session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION"));

        if (lastException.contains(SessionAuthenticationException.class.getName())) {
            model.addAttribute("alerta", "erro");
            model.addAttribute("titulo", "Acesso recusado!");
            model.addAttribute("texto", "Você já está logado em outro dispositivo.");
            model.addAttribute("subtexto", "Faça logout ou espere sua sessão expirar.");
            return "login";
        }
        model.addAttribute("alerta", "erro");
        model.addAttribute("titulo", "Credenciais inválidas!");
        model.addAttribute("texto", "Login ou senha incorretos, tente novamente.");
        model.addAttribute("subtexto", "Acesso permitido apenas para cadastros já ativados.");
        return "login";
    }

    public String sessaoExpirada(ModelMap model) {
        model.addAttribute("alerta", "erro");
        model.addAttribute("titulo", "Acesso recusado!");
        model.addAttribute("texto", "Sua sessão expirou.");
        model.addAttribute("subtexto", "Você logou em outro dispositivo.");
        return "login";
    }
}
