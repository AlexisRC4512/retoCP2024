package com.aramos.retoCP2024.service;

import com.aramos.retoCP2024.dto.request.SignInRequest;
import com.aramos.retoCP2024.dto.request.SignUpRequest;
import com.aramos.retoCP2024.dto.response.AutenticacionResponse;
import com.aramos.retoCP2024.entity.Usuario;

public interface AutenticacionService {
    AutenticacionResponse signin(SignInRequest signInRequest);
    Usuario signUpUser(SignUpRequest signUpRequest);


}
