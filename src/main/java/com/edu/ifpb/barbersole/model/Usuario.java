package com.edu.ifpb.barbersole.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name="usuario_seq", sequenceName = "usuario_seq", allocationSize = 1)
    private Long id;

    @Email(message = "O e-mail deve ser válido. Exemplo: exemplo@gmail.com")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotNull(message = "O nome é obrigatório.")
    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "senha_hash")
    private String senha;

    @Column(name = "status")
    private String status;

    @Past(message = "Data inválida. Deve ser uma data no passado.")
    @Column(name = "data_nasc")
    private LocalDate dataNasc;


    @Column(name = "data_cad")
    private LocalDate dataCad;

    @ManyToMany
    @JoinTable(
            name = "usuario_perfil",
            joinColumns = { @JoinColumn(name = "id_usuario") },
            inverseJoinColumns = { @JoinColumn (name = "id_perfil")}
    )
    private List<Perfil> perfis;
}
