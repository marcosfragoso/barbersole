package com.edu.ifpb.barbersole.controller;

import com.edu.ifpb.barbersole.model.Usuario;
import com.edu.ifpb.barbersole.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("usuario", usuario.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado")));
        return "/editar";
    }

    @GetMapping("/editar/{id}")
    public String preEditarBarbeiro(@PathVariable("id") Long codigo, ModelMap model) {
        Optional<Usuario> barbeiro = userService.findById(codigo);
        model.addAttribute("usuario", barbeiro.get());
        return "/editarBarbeiro";
    }

    @PostMapping("/editarBarbeiro")
    public String editarBarbeiro(@Valid Usuario usuario, RedirectAttributes attr, BindingResult result) {
        if (result.hasErrors()) {
            return "/usuarios/editar";
        }
        Optional<Usuario> u = userService.findByUsername(usuario.getUsername());
        u.get().setNome(usuario.getNome());
        u.get().setTelefone(usuario.getTelefone());
        userService.atualizarUsuario(u.get());
        attr.addFlashAttribute("sucesso", "Dados alterados com sucesso!");
        return "redirect:/barbeiros/barbeiroLista";
    }

    @PostMapping("/editar")
    public String editar(@AuthenticationPrincipal User user, @Valid Usuario usuario, RedirectAttributes attr, BindingResult result) {
        if (result.hasErrors()) {
            return "/usuarios/editar";
        }
        usuario.setUsername(user.getUsername());
        userService.atualizarUsuario(usuario);
        attr.addFlashAttribute("sucesso", "Dados alterados com sucesso!");
        return "redirect:/usuarios/editar";
    }

    @GetMapping("/excluir")
    public String excluir(@AuthenticationPrincipal User user, HttpSession session) {
        userService.deletarUsuario(user.getUsername());
        session.invalidate();
        return "redirect:/login";
    }
}
