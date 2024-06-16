package com.aramos.retoCP2024.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResponseDTO {
    private String nombre;
    private String precio;
}
