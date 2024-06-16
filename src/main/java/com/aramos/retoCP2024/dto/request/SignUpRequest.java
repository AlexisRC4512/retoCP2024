package com.aramos.retoCP2024.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String nombres;
    private String apellidos;
    private String correo;
    private String password;
}
