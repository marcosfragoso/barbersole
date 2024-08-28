package com.edu.ifpb.barbersole.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "agendamento")
@Getter
@Setter
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agendamento_seq")
    @SequenceGenerator(name="agendamento_seq", sequenceName = "agendamento_seq", allocationSize = 1)
    private Long id;

    @Column(name = "servico")
    @NotBlank(message = "Escolha um dos nossos 3 serviços.")
    private String servico;

    @ManyToOne
    @JoinColumn(name = "barbeiro")
    private Usuario barbeiro;

    @ManyToOne
    @JoinColumn(name = "cliente")
    private Usuario cliente;

    @Column(name = "data")
    @NotNull
    //@FutureOrPresent(message = "Você não pode agendar para uma data que já passou.")
    private LocalDate data;

    @Column(name = "status")
    private String status;

    @Column(name = "hora")
    @NotBlank(message = "Escolha um horário.")
    private String hora;
}
