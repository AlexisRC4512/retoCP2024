package com.aramos.retoCP2024.controller;

import com.aramos.retoCP2024.dto.request.SignInRequest;
import com.aramos.retoCP2024.dto.request.SignUpRequest;
import com.aramos.retoCP2024.dto.response.AutenticacionResponse;
import com.aramos.retoCP2024.entity.Usuario;
import com.aramos.retoCP2024.service.AutenticacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/autenticacion")
@RequiredArgsConstructor
public class AutenticacionController {
    private final AutenticacionService autenticacionService;

    @PostMapping("/signupuser")
    public ResponseEntity<Usuario> signUpUser(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(autenticacionService.signUpUser(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<AutenticacionResponse> signin(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(autenticacionService.signin(signInRequest));
    }
}
