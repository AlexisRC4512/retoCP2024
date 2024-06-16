package com.aramos.retoCP2024.repository;

import com.aramos.retoCP2024.dto.response.ProductoResponseDTO;
import com.aramos.retoCP2024.entity.Producto;
import com.aramos.retoCP2024.util.ProductoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Integer> {

    Page<ProductoProjection> findByPrecioBetween(double precioMin, double precioMax, Pageable pageable);

    Page<ProductoProjection> findByNombreContaining(String nombre , Pageable pageable);
}
