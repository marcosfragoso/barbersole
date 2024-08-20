package com.edu.ifpb.barbersole.service;

import com.edu.ifpb.barbersole.model.Token;
import com.edu.ifpb.barbersole.model.Usuario;
import com.edu.ifpb.barbersole.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserService userService;

    public Token gerarToken(Usuario usuario) {
        Token token = new Token();
        token.setToken(UUID.randomUUID().toString());
        Random random = new Random();
        int numero = 100000 + random.nextInt(900000);
        token.setCodigo(Integer.toString(numero));
        token.setExpiryDate(LocalDateTime.now().plusHours(2));
        token.setUsuario(usuario);
        token.setValid(true);
        return tokenRepository.save(token);
    }

    public Optional<Token> validarToken(String token) {
        return tokenRepository.findByToken(token)
                .filter(t -> t.isValid() && t.getExpiryDate().isAfter(LocalDateTime.now()));
    }

    public void invalidarToken(Token token) {
        token.setValid(false);
        tokenRepository.save(token);
    }

    public boolean validarCodigo(String token, String codigo) {
        Optional<Token> t = tokenRepository.findByToken(token);
        if (t.isPresent()) {
            if (t.get().getCodigo().equals(codigo)) return true;
        }
        return false;
    }
}
