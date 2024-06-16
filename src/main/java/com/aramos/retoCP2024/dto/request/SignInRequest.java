package com.aramos.retoCP2024.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    private String correo;
    private String password;
}
