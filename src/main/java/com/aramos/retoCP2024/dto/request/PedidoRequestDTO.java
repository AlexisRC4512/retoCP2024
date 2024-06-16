package com.aramos.retoCP2024.dto.request;

import com.aramos.retoCP2024.entity.Cliente;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class PedidoRequestDTO {
    @NotBlank(message = "El codigo de Producto es requerido")
    private Integer codigoProducto;
    @NotBlank(message = "La cantidad es requerido")
    @Positive(message = "La cantidad debe ser mayor que cero")
    private int cantidad;
    @NotBlank(message = "Los datos del cliente son requeridos")
    private Cliente cliente;
}
