package com.aramos.retoCP2024.service;

import com.aramos.retoCP2024.dto.request.ProductoRequestDTO;
import com.aramos.retoCP2024.dto.response.BaseResponse;
import com.aramos.retoCP2024.dto.response.ProductoResponseDTO;
import com.aramos.retoCP2024.util.ProductoProjection;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductoService {
    BaseResponse crearProducto(ProductoRequestDTO productoRequestDTO);
    Page<ProductoResponseDTO> listarProductos(int pagina,int cantidadElementos);
    Page<ProductoProjection>listarProductoPrecio(double precioMinimo, double precioMaximo, int pagina, int cantidadElementos);

    Page<ProductoProjection>listarProductoNombre(String nombre,int pagina,int cantidadElementos);
    BaseResponse buscarProductoCodigo(Integer codigoProducto);
    BaseResponse elminarProducto(Integer codigoProducto);

    BaseResponse actualizarProducto(Integer codigoProducto,ProductoRequestDTO productoRequestDTO);
}
