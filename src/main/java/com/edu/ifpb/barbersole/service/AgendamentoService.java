package com.edu.ifpb.barbersole.service;


import com.edu.ifpb.barbersole.model.Agendamento;
import com.edu.ifpb.barbersole.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public void salvar(Agendamento agendamento) {
        agendamentoRepository.save(agendamento);
    }
}
