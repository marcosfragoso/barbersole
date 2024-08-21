package com.edu.ifpb.barbersole.repository;

import com.edu.ifpb.barbersole.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
}
