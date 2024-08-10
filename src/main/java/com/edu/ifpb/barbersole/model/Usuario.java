package com.edu.ifpb.barbersole.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name="usuario_seq", sequenceName = "usuario_seq", allocationSize = 1)
    private Long id;

    @Email
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "nome")
    private String nome;

    @Column(name = "senha_hash")
    private String senha;

    @Column(name = "status")
    private String status;

    @ManyToMany
    @JoinTable(
            name = "usuario_perfil",
            joinColumns = { @JoinColumn(name = "id_usuario") },
            inverseJoinColumns = { @JoinColumn (name = "id_perfil")}
    )
    private List<Perfil> perfis;
}
