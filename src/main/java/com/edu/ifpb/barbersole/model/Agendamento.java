package com.edu.ifpb.barbersole.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "agendamento")
@Data
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agendamento_seq")
    @SequenceGenerator(name="agendamento_seq", sequenceName = "agendamento_seq", allocationSize = 1)
    private Long id;

    @Column(name = "servico")
    @NotBlank
    private String servico;

    @ManyToOne
    @JoinColumn(name = "barbeiro")
    private Usuario barbeiro;

    @ManyToOne
    @JoinColumn(name = "cliente")
    private Usuario cliente;

    @Column(name = "data")
    @NotNull
    private LocalDate data;

    @Column(name = "status")
    @NotBlank
    private String status;

    @Column(name = "hora")
    @NotBlank
    private String hora;
}
