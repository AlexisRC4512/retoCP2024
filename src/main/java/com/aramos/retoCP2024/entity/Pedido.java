package com.aramos.retoCP2024.entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nro_pedido")
    private Integer nroPedido;
    @ManyToOne
    @JoinColumn(name = "codigo_producto")
    private Producto producto;
    private int cantidad;
    @Embedded
    private Cliente cliente;
}
