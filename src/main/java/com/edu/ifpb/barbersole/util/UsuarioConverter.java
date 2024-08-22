package com.edu.ifpb.barbersole.util;

import com.edu.ifpb.barbersole.model.Usuario;
import com.edu.ifpb.barbersole.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConverter implements Converter<String, Usuario> {

    @Autowired
    private UserService userService;

    @Override
    public Usuario convert(String text) {
        if (text.isEmpty()) return null;
        Long id = Long.valueOf(text);
        return userService.findById(id).orElse(null);
    }
}
