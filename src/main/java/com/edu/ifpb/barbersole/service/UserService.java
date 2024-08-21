package com.edu.ifpb.barbersole.service;

import com.edu.ifpb.barbersole.model.Perfil;
import com.edu.ifpb.barbersole.model.Usuario;
import com.edu.ifpb.barbersole.repository.PerfilRepository;
import com.edu.ifpb.barbersole.repository.UsuarioRepository;

import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = buscaPorUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuário inexistente!"));

        if (!usuario.getStatus().equals("Ativo")) {
            throw new DisabledException("Usuário inativo!");
        }

        User userSpring = new User(
                usuario.getUsername(),
                usuario.getSenha(),
                AuthorityUtils.createAuthorityList(getAuthorities(usuario.getPerfis()))
        );
        return userSpring;
    }

    private String[] getAuthorities(List<Perfil> perfis) {
        String[] authorities = new String[perfis.size()];
        for (int i = 0; i < perfis.size(); i++) {
            authorities[i] = perfis.get(i).getNome();
        }
        return authorities;
    }

    @Transactional(readOnly = true)
    private Optional<Usuario> buscaPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Transactional(readOnly = false)
    public void salvarUsuario(Usuario usuario) {
        String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
        usuario.setDataCad(LocalDate.now());
        usuario.setSenha(crypt);
        usuario.setPerfis(perfilRepository.findAllById(2L));
        usuario.setStatus("Ativo");
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new EntityExistsException();
        }
        usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = false)
    public void salvarBarbeiro(Usuario usuario) {
        String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
        usuario.setDataCad(LocalDate.now());
        usuario.setSenha(crypt);
        usuario.setPerfis(perfilRepository.findAllById(1L));
        usuario.setStatus("Ativo");
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new EntityExistsException();
        }
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public void atualizarUsuario(Usuario usuario) {
        Optional<Usuario> u = usuarioRepository.findByUsername(usuario.getUsername());

        if (u.isPresent()) {
            Usuario usuarioExiste = u.get();
            usuarioExiste.setTelefone(usuario.getTelefone());
            usuarioExiste.setNome(usuario.getNome());
            usuarioRepository.save(usuarioExiste);
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }

    public void deletarUsuario(String username) {
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);

        if (usuario.isPresent()) {
            Usuario u = usuario.get();
            u.setStatus("Inativo");
            usuarioRepository.save(u);
        }
    }

    public void atualizarSenha(Usuario usuario, String novaSenha) {

        String crypt = new BCryptPasswordEncoder().encode(novaSenha);
        usuario.setSenha(crypt);
        usuarioRepository.save(usuario);
    }

    public List<Usuario> listarBarbeiros() {
        return usuarioRepository.findAllBarbers();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public void deletarUsuarioById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            Usuario u = usuario.get();
            u.setStatus("Inativo");
            usuarioRepository.save(u);
        }
    }

    public void ativarUsuarioById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            Usuario u = usuario.get();
            u.setStatus("Ativo");
            usuarioRepository.save(u);
        }
    }



}
