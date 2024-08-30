package com.edu.ifpb.barbersole.controller;

import com.edu.ifpb.barbersole.model.Agendamento;
import com.edu.ifpb.barbersole.model.Token;
import com.edu.ifpb.barbersole.model.Usuario;
import com.edu.ifpb.barbersole.service.AgendamentoService;
import com.edu.ifpb.barbersole.service.EmailService;
import com.edu.ifpb.barbersole.service.TokenService;
import com.edu.ifpb.barbersole.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AgendamentoService agendamentoService;

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
    public String home(HttpServletResponse response, ModelMap model, @AuthenticationPrincipal User user) {
        List<Usuario> barbeiros = userService.listarBarbeiros();
        model.addAttribute("barbeiros", barbeiros);

        Optional<Usuario> u = userService.findByUsername(user.getUsername());
        Usuario usuario = u.get();

        boolean isCliente = usuario.getPerfis().stream()
                .anyMatch(perfil -> "CLIENTE".equals(perfil.getNome()));

        boolean isAdmin = usuario.getPerfis().stream()
                .anyMatch(perfil -> "ADMIN".equals(perfil.getNome()));

        boolean isBarbeiro = usuario.getPerfis().stream()
                .anyMatch(perfil -> "BARBEIRO".equals(perfil.getNome()));

        List<Agendamento> agendamentos;
        Map<String, Long> cortesMap = new LinkedHashMap<>();

        if (isBarbeiro) {
            agendamentos = agendamentoService.buscarAgendamentosPorBarbeiro(usuario);


            cortesMap.put("Corte", agendamentoService.getQuantidadeByServico("Corte", usuario.getId()));
            cortesMap.put("Barba", agendamentoService.getQuantidadeByServico("Barba", usuario.getId()));
            cortesMap.put("Corte e barba", agendamentoService.getQuantidadeByServico("Corte e barba", usuario.getId()));
            model.addAttribute("cortesMap", cortesMap);


            Long qntdCancelado = agendamentoService.countAgendamentosPorStatus("Cancelado", usuario.getId());
            Long qntdConfirmado = agendamentoService.countAgendamentosPorStatus("Confirmado", usuario.getId());
            Long qntdAgendado = agendamentoService.countAgendamentosPorStatus("Agendado", usuario.getId());
            Long qntdTotal = agendamentoService.countAgendamentos(usuario.getId());

            double canceladoPorcentagem = (qntdCancelado.doubleValue() / qntdTotal.doubleValue()) * 100;
            double confirmadoPorcentagem = (qntdConfirmado.doubleValue() / qntdTotal.doubleValue()) * 100;
            double agendadoPorcentagem = (qntdAgendado.doubleValue() / qntdTotal.doubleValue()) * 100;

            model.addAttribute("Cancelado", canceladoPorcentagem);
            model.addAttribute("Confirmado", confirmadoPorcentagem);
            model.addAttribute("Agendado", agendadoPorcentagem);

            model.addAttribute("CanceladoQtd", qntdCancelado);
            model.addAttribute("ConfirmadoQtd", qntdConfirmado);
            model.addAttribute("AgendadoQtd", qntdAgendado);

        } else {
            agendamentos = agendamentoService.buscarAgendamentosPorCliente(usuario);
        }
        model.addAttribute("agendamentos", agendamentos);
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

    @GetMapping({"/remember"})
    public String remember(HttpServletResponse response) {
        return "remember";
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam("username") String email, RedirectAttributes redirectAttributes) {
        Usuario usuario = userService.findByUsername(email).orElse(null);
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Usuário não encontrado.");
            return "redirect:/remember";
        }

        Token token = tokenService.gerarToken(usuario);
        String subject = "Recuperação de Senha - BarberSole App";
        String message = "Olá,\n\nVocê solicitou a recuperação de sua senha. " +
                "Por favor, clique no link abaixo e digite o código: " + token.getCodigo() + " para redefinir sua senha:\n\n" +
                "http://localhost:8080/reset?token=" + token.getToken() + "\n\n" +
                "Se você não fez essa solicitação, por favor ignore este e-mail.";

        try {
            emailService.enviarEmail(email, subject, message);
            redirectAttributes.addFlashAttribute("success", "E-mail de recuperação enviado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Falha ao enviar o e-mail. Tente novamente.");
        }

        return "redirect:/";
    }

    @GetMapping("/reset")
    public String showResetForm(@RequestParam("token") String token, ModelMap model) {
        Optional<Token> optionalToken = tokenService.validarToken(token);
        if (optionalToken.isEmpty()) {
            model.addAttribute("erro", "Token inválido ou expirado!");
            return "remember";
        }
        model.addAttribute("token", token);
        return "reset";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("token") String token, @RequestParam("password") String password, @RequestParam("codigo") String codigo, RedirectAttributes attr) {

        Optional<Token> optionalToken = tokenService.validarToken(token);

        if (optionalToken.isEmpty()) {
            attr.addFlashAttribute("erro", "Token inválido ou expirado!");
            return "redirect:/remember";
        }
        Usuario usuario = optionalToken.get().getUsuario();
        if (usuario.getId().equals(optionalToken.get().getUsuario().getId())) {
            if (tokenService.validarCodigo(token, codigo)) {
                userService.atualizarSenha(usuario, password);
                tokenService.invalidarToken(optionalToken.get()); // Invalidar o token após o uso
                attr.addFlashAttribute("sucesso", "Senha alterada com sucesso!");
                return "sucesso";
            } else {
                attr.addFlashAttribute("codigoError", "O código digitado não confere.");
            }
        }
        return "redirect:/reset?token=" + token;
    }

}
