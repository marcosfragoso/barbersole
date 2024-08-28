package com.edu.ifpb.barbersole.service;


import com.edu.ifpb.barbersole.model.Agendamento;
import com.edu.ifpb.barbersole.model.Usuario;
import com.edu.ifpb.barbersole.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {


    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private EmailService emailService;

    public void salvar(Agendamento agendamento) {
        agendamentoRepository.save(agendamento);


        String subject = "Confirmação de Agendamento - BarberSole App";
        String message = "Olá,\n\nVocê reservou o seguinte agendamento: \n\n\n" +
                "Barbeiro: " + agendamento.getBarbeiro().getNome() + "\n" +
                "Serviço: " + agendamento.getServico() + "\n" +
                "Data: " + agendamento.getData() + "\n" +
                "Horário: " + agendamento.getHora() + "\n\n\n" +
                "Por favor, clique no link abaixo para confirmar: \n" +
                "http://localhost:8080/home \n\n" +
                "Se você não fez esse agendamento, por favor ignore este e-mail.";

        emailService.enviarEmail(agendamento.getCliente().getUsername(), subject, message);
    }

    public List<String> buscarHorariosDisponiveis(Long barbeiroId, LocalDate data) {
        List<String> todosHorarios = List.of(
                "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
                "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
                "15:00", "15:30", "16:00", "16:30", "17:00"
        );
        List<String> horariosIndisponiveis = agendamentoRepository
                .findByBarbeiroIdAndData(barbeiroId, data)
                .stream()
                .map(Agendamento::getHora)
                .collect(Collectors.toList());



        List<String> horariosDisponiveis = todosHorarios.stream()
                .filter(horario -> !horariosIndisponiveis.contains(horario))
                .collect(Collectors.toList());


        return horariosDisponiveis;
    }

    public List<Agendamento> buscarAgendamentosPorCliente(Usuario usuario) {
        return agendamentoRepository.findByCliente(usuario);
    }

    public Optional<Agendamento> buscarAgendamentoPorId(Long codigo) {
        return agendamentoRepository.findById(codigo);
    }

    public Agendamento atualizarAgendamento(Agendamento agendamento) {
        agendamento.setStatus("Agendado");
        return agendamentoRepository.save(agendamento);
    }

    public Agendamento alterarAgendamento(Agendamento agendamento) {
        return agendamentoRepository.save(agendamento);
    }
}
