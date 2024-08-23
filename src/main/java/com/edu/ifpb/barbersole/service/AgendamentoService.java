package com.edu.ifpb.barbersole.service;


import com.edu.ifpb.barbersole.model.Agendamento;
import com.edu.ifpb.barbersole.model.Usuario;
import com.edu.ifpb.barbersole.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {


    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public void salvar(Agendamento agendamento) {
        agendamentoRepository.save(agendamento);
    }

    public List<String> buscarHorariosDisponiveis(Long barbeiroId, LocalDate data) {
        List<String> todosHorarios = List.of("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00");

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
}
