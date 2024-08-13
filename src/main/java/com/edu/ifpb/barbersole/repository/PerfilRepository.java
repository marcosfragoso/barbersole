package com.edu.ifpb.barbersole.repository;

import com.edu.ifpb.barbersole.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    List<Perfil> findAllById(Long id);
}
