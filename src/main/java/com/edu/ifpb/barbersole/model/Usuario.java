package com.edu.ifpb.barbersole.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name="usuario_seq", sequenceName = "usuario_seq", allocationSize = 1)
    private Long id;

    @Email(message = "O e-mail deve ser válido. Exemplo: exemplo@gmail.com")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotNull(message = "O nome é obrigatório.")
    @NotBlank(message = "O nome não pode ser vazio.")
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

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                '}';
    }
}
