package com.edu.ifpb.barbersole.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "perfil")
@Data
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfil_seq")
    @SequenceGenerator(name="perfil_seq", sequenceName = "perfil_seq", allocationSize = 1)
    private Long id;

    @Column
    private String nome;

    @ManyToMany(mappedBy = "perfis")
    @JsonIgnore
    private List<Usuario> usuarios;
}
