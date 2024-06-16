package com.aramos.retoCP2024.dto.response;

import com.aramos.retoCP2024.entity.Cliente;
import com.aramos.retoCP2024.entity.Producto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {
    private Integer nroPedido;
    private Producto producto;
    private int cantidad;
    private Cliente cliente;
}
