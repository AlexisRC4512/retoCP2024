package com.aramos.retoCP2024.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class BaseResponse {
    private int code;
    private String message;
    private Optional data ;
}
