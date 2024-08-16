package com.edu.ifpb.barbersole.service;

import com.edu.ifpb.barbersole.model.Usuario;
import com.edu.ifpb.barbersole.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void enviarEmail(String para, String assunto, String mensagem) {
        Optional<Usuario> u = usuarioRepository.findByUsername(para);
        if (u.isPresent()) {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(para);
            email.setSubject(assunto);
            email.setText(mensagem);
            email.setFrom("barbersoleapp@gmail.com");
            mailSender.send(email);
        } else {
            throw new UsernameNotFoundException("Usuário não existe!");
        }
    }
}
