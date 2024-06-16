package com.aramos.retoCP2024.service.impl;

import com.aramos.retoCP2024.aggregates.Constant;
import com.aramos.retoCP2024.dto.request.ProductoRequestDTO;
import com.aramos.retoCP2024.dto.response.BaseResponse;
import com.aramos.retoCP2024.dto.response.ProductoResponseDTO;
import com.aramos.retoCP2024.entity.Producto;
import com.aramos.retoCP2024.excepcionPersonalizada.CustomInternalException;
import com.aramos.retoCP2024.repository.ProductoRepository;
import com.aramos.retoCP2024.service.ProductoService;
import com.aramos.retoCP2024.util.ProductoProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;
    @Override
    public BaseResponse crearProducto(ProductoRequestDTO productoRequestDTO) {
        try {
            Producto producto = Producto.builder()
                    .nombre(productoRequestDTO.getNombre())
                    .precio(productoRequestDTO.getPrecio())
                    .build();
            Producto productoCreado = productoRepository.save(producto);
            return new BaseResponse(Constant.CODE_SUCCESS, Constant.MESS_SUCCESS_PRODUCTO_CREACION, Optional.of(productoCreado));
        } catch (Exception e) {
            return new BaseResponse(Constant.CODE_ERROR_DATABASE, Constant.MESS_ERROR_DATABASE, Optional.empty());
        }
    }

    @Override
    public Page<ProductoResponseDTO> listarProductos(int pagina, int cantidadElementos) {
        try {
            Pageable pageable = PageRequest.of(pagina, cantidadElementos);
            Page<Producto> productos = productoRepository.findAll(pageable);
            return productos.map(this::convertirProductoEntityaDTO);
        } catch (Exception e) {
            throw new CustomInternalException(Constant.MESS_ERROR_DATABASE, e);
        }
    }

    @Override
    public Page<ProductoProjection> listarProductoPrecio(double precioMinimo, double precioMaximo, int pagina, int cantidadElementos) {
        try {
            Pageable pageable = PageRequest.of(pagina, cantidadElementos);
            return productoRepository.findByPrecioBetween(precioMinimo, precioMaximo, pageable);
        } catch (Exception e) {
            throw new CustomInternalException(Constant.MESS_ERROR_DATABASE, e);
        }
    }

    @Override
    public Page<ProductoProjection> listarProductoNombre(String nombre, int pagina, int cantidadElementos) {
        try {
            Pageable pageable = PageRequest.of(pagina, cantidadElementos);
            Page<ProductoProjection> productos = productoRepository.findByNombreContaining(nombre, pageable);
            return productos;
        } catch (Exception e) {
            throw new CustomInternalException(Constant.MESS_ERROR_DATABASE, e);
        }
    }

    @Override
    public BaseResponse buscarProductoCodigo(Integer codigoProducto) {
        try {
            Producto producto = productoRepository.findById(codigoProducto)
                    .orElseThrow(() -> new EntityNotFoundException(Constant.MESS_FAILED_FIND_PRODUCTO));
            return new BaseResponse(Constant.CODE_SUCCESS,Constant.MESS_SUCCESS_FIND_PRODUCTO,Optional.of(producto));
        } catch (EntityNotFoundException e) {
            throw new CustomInternalException(Constant.MESS_FAILED_FIND_PRODUCTO, e);
        } catch (Exception e) {
            throw new CustomInternalException(Constant.MESS_ERROR_DATABASE, e);
        }
    }

    @Override
    public BaseResponse elminarProducto(Integer codigoProducto) {
        try {
         productoRepository.deleteById(codigoProducto);
            return new BaseResponse(Constant.CODE_SUCCESS, Constant.MESS_SUCCESS_DELETE_PRODUCTO, Optional.empty());
        } catch (Exception e) {
            return new BaseResponse(Constant.CODE_ERROR_DATABASE, Constant.MESS_ERROR_DATABASE, Optional.empty());
        }
    }

    @Override
    public BaseResponse actualizarProducto(Integer codigoProducto, ProductoRequestDTO productoRequestDTO) {
        try {
            Producto productoExistente = productoRepository.findById(codigoProducto)
                    .orElseThrow(() -> new EntityNotFoundException(Constant.MESS_FAILED_FIND_PRODUCTO));

            productoExistente.setNombre(productoRequestDTO.getNombre());
            productoExistente.setPrecio(productoRequestDTO.getPrecio());
            Producto productoActualizado = productoRepository.save(productoExistente);
            ProductoResponseDTO productoResponseDTO = convertirProductoEntityaDTO(productoActualizado);
            return new BaseResponse(Constant.CODE_SUCCESS, Constant.MESS_SUCCESS_UPDATE_PRODUCTO, Optional.of(productoResponseDTO));
        } catch (EntityNotFoundException e) {
            return new BaseResponse(Constant.CODE_ERROR, e.getMessage(), Optional.empty());
        } catch (Exception e) {
            return new BaseResponse(Constant.CODE_ERROR_DATABASE, Constant.MESS_ERROR_DATABASE, Optional.empty());
        }
    }

    private ProductoResponseDTO convertirProductoEntityaDTO(Producto producto) {
        return ProductoResponseDTO.builder()
                .nombre(producto.getNombre())
                .precio(String.valueOf(producto.getPrecio()))
                .build();
    }
}
