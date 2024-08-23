package com.edu.ifpb.barbersole.repository;

import com.edu.ifpb.barbersole.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByBarbeiroIdAndData(Long barbeiroId, LocalDate data);
}
