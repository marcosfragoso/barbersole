package com.edu.ifpb.barbersole.service;

import com.edu.ifpb.barbersole.model.Usuario;
import com.edu.ifpb.barbersole.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void enviarEmail(String para, String assunto, String mensagem) {
        try {
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
        } catch (UsernameNotFoundException ue) {
            log.error("Usuário não existente. Username: {}.", para);
        } catch (Exception e) {
            log.error("Erro ao enviar o e-mail", e);
        }

    }
}
