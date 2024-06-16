package com.aramos.retoCP2024.service;

import com.aramos.retoCP2024.aggregates.Constant;
import com.aramos.retoCP2024.dto.request.ProductoRequestDTO;
import com.aramos.retoCP2024.dto.response.BaseResponse;
import com.aramos.retoCP2024.dto.response.ProductoResponseDTO;
import com.aramos.retoCP2024.entity.Producto;
import com.aramos.retoCP2024.repository.ProductoRepository;
import com.aramos.retoCP2024.util.ProductoProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductoServiceTest {
    @Autowired
    private ProductoService productoService;
    @MockBean
    private ProductoRepository productoRepository;
    @Test
    public void testCrearProducto() {
        ProductoRequestDTO requestDTO = new ProductoRequestDTO("Producto Test", 100.0);
        Producto producto = new Producto(1, "Producto Test", 100.0);
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        BaseResponse response = productoService.crearProducto(requestDTO);
        assertEquals(Constant.CODE_SUCCESS, response.getCode());
        assertTrue(response.getData().isPresent());
        assertEquals(producto, response.getData().get());
    }
    @Test
    public void testCrearProducto_Error() {
        ProductoRequestDTO requestDTO = new ProductoRequestDTO("Producto Test", 100.0);
        Producto producto = new Producto();
        when(productoRepository.save(any(Producto.class))).thenThrow();
        BaseResponse response = productoService.crearProducto(requestDTO);
        assertEquals(Constant.CODE_ERROR_DATABASE, response.getCode());
        assertEquals(Constant.MESS_ERROR_DATABASE, response.getMessage());
    }
    @Test
    public void testListarProductos() {
        int pagina = 0;
        int cantidadElementos = 5;
        Pageable pageable = PageRequest.of(pagina, cantidadElementos);
        List<Producto> listaProductos = List.of(new Producto(1, "Producto Test", 100.0));
        Page<Producto> pageProductos = new PageImpl<>(listaProductos, pageable, listaProductos.size());
        when(productoRepository.findAll(pageable)).thenReturn(pageProductos);
        Page<ProductoResponseDTO> response = productoService.listarProductos(pagina, cantidadElementos);
        assertEquals(listaProductos.size(), response.getContent().size());
    }
    @Test
    public void testListarProductoPrecio() {
        double precioMinimo = 50.0;
        double precioMaximo = 150.0;
        int pagina = 0;
        int cantidadElementos = 5;
        Pageable pageable = PageRequest.of(pagina, cantidadElementos);
        List<ProductoProjection> listaProductos = new ArrayList<>();
        ProductoProjection producto1 = mock(ProductoProjection.class);
        ProductoProjection producto2 = mock(ProductoProjection.class);
        listaProductos.add(producto1);
        listaProductos.add(producto2);
        Page<ProductoProjection> pageProductos = new PageImpl<>(listaProductos, pageable, listaProductos.size());
        when(productoRepository.findByPrecioBetween(precioMinimo, precioMaximo, pageable)).thenReturn(pageProductos);
        Page<ProductoProjection> response = productoService.listarProductoPrecio(precioMinimo, precioMaximo, pagina, cantidadElementos);
        assertEquals(listaProductos.size(), response.getContent().size());
        assertTrue(response.getContent().containsAll(listaProductos));
    }

    @Test
    public void testListarProductoNombre() {
        String nombre = "Test";
        int pagina = 0;
        int cantidadElementos = 5;
        Pageable pageable = PageRequest.of(pagina, cantidadElementos);
        List<ProductoProjection> listaProductos = new ArrayList<>();
        ProductoProjection producto1 = mock(ProductoProjection.class);
        ProductoProjection producto2 = mock(ProductoProjection.class);
        listaProductos.add(producto1);
        listaProductos.add(producto2);
        Page<ProductoProjection> pageProductos = new PageImpl<>(listaProductos, pageable, listaProductos.size());
        when(productoRepository.findByNombreContaining(nombre, pageable)).thenReturn(pageProductos);
        Page<ProductoProjection> response = productoService.listarProductoNombre(nombre, pagina, cantidadElementos);
        assertEquals(listaProductos.size(), response.getContent().size());
        assertTrue(response.getContent().containsAll(listaProductos));
    }

    @Test
    public void testBuscarProductoCodigo() {
        Integer codigoProducto = 1;
        Producto producto = new Producto(1, "Producto Test", 100.0);
        when(productoRepository.findById(codigoProducto)).thenReturn(Optional.of(producto));
        BaseResponse response = productoService.buscarProductoCodigo(codigoProducto);
        assertEquals(Constant.CODE_SUCCESS, response.getCode());
    }

    @Test
    public void testElminarProducto() {
        Integer codigoProducto = 1;
        BaseResponse response = productoService.elminarProducto(codigoProducto);
        assertEquals(Constant.CODE_SUCCESS, response.getCode());
    }

    @Test
    public void testActualizarProducto() {
        Integer codigoProducto = 1;
        ProductoRequestDTO requestDTO = new ProductoRequestDTO("Producto Actualizado", 200.0);
        Producto productoExistente = new Producto(1, "Producto Test", 100.0);
        when(productoRepository.findById(codigoProducto)).thenReturn(Optional.of(productoExistente));
        productoExistente.setNombre(requestDTO.getNombre());
        productoExistente.setPrecio(requestDTO.getPrecio());
        when(productoRepository.save(productoExistente)).thenReturn(productoExistente);
        BaseResponse response = productoService.actualizarProducto(codigoProducto, requestDTO);
        assertEquals(Constant.CODE_SUCCESS, response.getCode());
        assertTrue(response.getData().isPresent());
        ProductoResponseDTO productoResponseDTO = (ProductoResponseDTO) response.getData().get();
        assertEquals(requestDTO.getNombre(), productoResponseDTO.getNombre());
    }
}
