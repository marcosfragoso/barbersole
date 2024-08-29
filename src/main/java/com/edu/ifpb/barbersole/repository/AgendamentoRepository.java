package com.edu.ifpb.barbersole.repository;

import com.edu.ifpb.barbersole.model.Agendamento;
import com.edu.ifpb.barbersole.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    @Query(value = "select agendamento.* from agendamento\n" +
            "inner join usuario on :barbeiroId = agendamento.barbeiro\n" +
            "where agendamento.status != 'Cancelado' and agendamento.data = :data;", nativeQuery = true)
    List<Agendamento> findByBarbeiroIdAndData(@Param("barbeiroId") Long barbeiroId, @Param("data") LocalDate data);

    List<Agendamento> findByCliente(Usuario cliente);

    List<Agendamento> findByBarbeiro(Usuario barbeiro);

    @Query(value = "select count(agendamento.id) from agendamento " +
            "where agendamento.servico = :servico and agendamento.status != 'Cancelado' and agendamento.barbeiro = :barbeiroId", nativeQuery = true)
    Long countByServico(@Param("servico") String servico, @Param("barbeiroId") Long barbeiroId);

    @Query(value = "select count(agendamento.id) from agendamento " +
            "where agendamento.status = :status and agendamento.barbeiro = :barbeiroId", nativeQuery = true)
    Long countByStatus(@Param("status") String status, @Param("barbeiroId") Long barbeiroId);

    @Query(value = "select count(agendamento.id) from agendamento where agendamento.barbeiro = :barbeiroId", nativeQuery = true)
    Long countAgendamentos(@Param("barbeiroId") Long barbeiroId);
}
