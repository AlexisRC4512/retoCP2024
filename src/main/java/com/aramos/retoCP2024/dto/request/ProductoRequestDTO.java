package com.aramos.retoCP2024.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoRequestDTO {
    @NotBlank(message = "El codigo de Producto es requerido")
    @Size(max = 250, message = "El nombre no puede exceder los  250 caracteres")
    private String nombre;
    @NotBlank(message = "El codigo de Producto es requerido")
    @DecimalMin(value = "0.01", message = "El precio debe ser igual o mayor que 0.01")
    private double precio;
}
