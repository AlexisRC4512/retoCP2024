package com.aramos.retoCP2024.service.impl;

import com.aramos.retoCP2024.aggregates.RoleUser;
import com.aramos.retoCP2024.dto.request.SignInRequest;
import com.aramos.retoCP2024.dto.request.SignUpRequest;
import com.aramos.retoCP2024.dto.response.AutenticacionResponse;
import com.aramos.retoCP2024.entity.Usuario;
import com.aramos.retoCP2024.repository.UsuarioRepository;
import com.aramos.retoCP2024.service.AutenticacionService;
import com.aramos.retoCP2024.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacionServiceImpl implements AutenticacionService {
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    @Override
    public AutenticacionResponse signin(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getCorreo(),signInRequest.getPassword()));
        var user = usuarioRepository.findByCorreo(signInRequest.getCorreo())
                .orElseThrow(() -> new IllegalArgumentException("Email no valido"));

        var jwt = jwtService.generateToken(user);
        AutenticacionResponse authenticationResponse =  new AutenticacionResponse();
        authenticationResponse.setToken(jwt);
        return authenticationResponse;
    }

    @Override
    public Usuario signUpUser(SignUpRequest signUpRequest) {
        Usuario usuario = new Usuario();
        usuario.setNombres(signUpRequest.getNombres());
        usuario.setApellidos(signUpRequest.getApellidos());
        usuario.setCorreo(signUpRequest.getCorreo());
        usuario.setRolUsuario(String.valueOf(RoleUser.USER));
        usuario.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        return usuarioRepository.save(usuario);
    }
}
