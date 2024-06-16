package com.aramos.retoCP2024.repository;

import com.aramos.retoCP2024.dto.response.PedidoResponseDTO;
import com.aramos.retoCP2024.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido,Integer> {
    Page<Pedido> findByClienteApellidos(String apellidos, Pageable pageable);
}
