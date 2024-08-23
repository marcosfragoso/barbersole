package com.edu.ifpb.barbersole.controller;

import com.edu.ifpb.barbersole.model.Agendamento;
import com.edu.ifpb.barbersole.model.Usuario;
import com.edu.ifpb.barbersole.service.AgendamentoService;
import com.edu.ifpb.barbersole.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private UserService userService;

    @GetMapping("/agendar")
    public String agendar(HttpServletResponse response, ModelMap model){
        model.addAttribute("agendamento", new Agendamento());
        return "agendar";
    }

    @GetMapping("/")
    public String agendamentos(HttpServletResponse response, ModelMap model, @AuthenticationPrincipal User user){
        Optional<Usuario> u = userService.findByUsername(user.getUsername());
        Usuario usuario = u.get();

        List<Agendamento> agendamentos = agendamentoService.buscarAgendamentosPorCliente(usuario);
        model.addAttribute("agendamentos", agendamentos);
        return "agendamentos";
    }

    @PostMapping("/agendarServico")
    public String agendarServico(@AuthenticationPrincipal User user, @Valid Agendamento agendamento, BindingResult result, RedirectAttributes attr){
        if (result.hasErrors()) {
            return "agendar";
        }
        Optional<Usuario> u = userService.findByUsername(user.getUsername());
        Usuario usuario = u.get();
        agendamento.setCliente(usuario);
        agendamento.setStatus("Agendado");
        agendamentoService.salvar(agendamento);
        attr.addFlashAttribute("sucesso", "Agendamento concluído com sucesso!");
        return "redirect:/agendamentos/agendar";
    }

    @ModelAttribute("barbeiros")
    public List<Usuario> getUsuarios() {
        return userService.listarBarbeiros();
    }

    @GetMapping("/horarios-disponiveis")
    @ResponseBody
    public List<String> getHorariosDisponiveis(@RequestParam Long barbeiroId, @RequestParam String data) {
        return agendamentoService.buscarHorariosDisponiveis(barbeiroId, LocalDate.parse(data));
    }
}
