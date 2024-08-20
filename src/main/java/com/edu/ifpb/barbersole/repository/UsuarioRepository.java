package com.edu.ifpb.barbersole.repository;

import com.edu.ifpb.barbersole.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    List<Usuario> findAllByPerfisId(Long perfilId);

    @Query(value = "select u.* from usuario as u\n" +
            "inner join usuario_perfil as up on u.id = up.id_usuario\n" +
            "where up.id_perfil = 1;", nativeQuery = true)
    List<Usuario> findAllBarbers();
}
