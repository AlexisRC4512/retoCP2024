package com.aramos.retoCP2024.entity;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Cliente {
    @NotBlank(message = "El nombre es requerido")
    @Size(max = 250, message = "El nombre no puede exceder los  250 caracteres")
    private String nombres;
    @NotBlank(message = "Los apellidos son requerido")
    @Size(max = 250, message = "El nombre no puede exceder los  250 caracteres")
    private String apellidos;
    @NotBlank(message = "La direccion es requerido")
    @Size(max = 250, message = "La direccion no puede exceder los  250 caracteres")
    private String direccion;
}
