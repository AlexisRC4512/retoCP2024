package com.aramos.retoCP2024.service.impl;


import com.aramos.retoCP2024.entity.Usuario;
import com.aramos.retoCP2024.repository.UsuarioRepository;
import com.aramos.retoCP2024.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Optional<Usuario> usuario=usuarioRepository.findByCorreo(username);
                return usuarioRepository.findByCorreo(username).orElseThrow( ()->
                        new UsernameNotFoundException("Usuario no encontrado"));
            }
        };
    }
}
