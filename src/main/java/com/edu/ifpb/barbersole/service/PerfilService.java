package com.edu.ifpb.barbersole.service;

import com.edu.ifpb.barbersole.model.Perfil;
import com.edu.ifpb.barbersole.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    public List<Perfil> findAllById(Long id) {
        return perfilRepository.findAllById(id);
    }
}
