package com.aramos.retoCP2024.service;

import com.aramos.retoCP2024.aggregates.Constant;
import com.aramos.retoCP2024.dto.request.PedidoRequestDTO;
import com.aramos.retoCP2024.dto.response.BaseResponse;
import com.aramos.retoCP2024.dto.response.PedidoResponseDTO;
import com.aramos.retoCP2024.entity.Cliente;
import com.aramos.retoCP2024.entity.Pedido;
import com.aramos.retoCP2024.entity.Producto;
import com.aramos.retoCP2024.excepcionPersonalizada.CustomDatabaseException;
import com.aramos.retoCP2024.repository.PedidoRepository;
import com.aramos.retoCP2024.repository.ProductoRepository;
import com.aramos.retoCP2024.service.impl.PedidoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PedidoServiceTest {
    @Autowired
    private PedidoServiceImpl pedidoService;

    @MockBean
    private PedidoRepository pedidoRepository;

    @MockBean
    private ProductoRepository productoRepository;

    private PedidoRequestDTO pedidoRequestDTO;
    private Pedido pedido;
    private Producto producto;
    private Cliente cliente;
    @BeforeEach
    void setUp() {
        producto = new Producto();

        pedidoRequestDTO = new PedidoRequestDTO();
        pedidoRequestDTO.setCodigoProducto(1);
        pedidoRequestDTO.setCantidad(2);
         cliente= Cliente.builder().nombres("juan")
                .apellidos("rojo").direccion("Av123").build();
        pedido = new Pedido();
        pedido.setProducto(producto);
        pedido.setCliente(cliente);
        pedido.setCantidad(2);
    }
    @Test
    void testCrearPedidoExito() {
        when(productoRepository.findById(anyInt())).thenReturn(Optional.of(producto));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        BaseResponse response = pedidoService.crearPedido(pedidoRequestDTO);
        assertEquals(Constant.CODE_SUCCESS, response.getCode());
        assertTrue(response.getData().isPresent());
    }
    @Test
    void testCrearPedidoError() {
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        BaseResponse response = pedidoService.crearPedido(pedidoRequestDTO);
        assertEquals(Constant.CODE_ERROR_DATABASE, response.getCode());
    }

    @Test
    void testCrearPedidoEntidadNoEncontrada() {
        when(pedidoRepository.save(any(Pedido.class))).thenThrow(EntityNotFoundException.class);

        BaseResponse response = pedidoService.crearPedido(pedidoRequestDTO);

        assertEquals(Constant.CODE_ERROR_DATABASE, response.getCode());
        assertFalse(response.getData().isPresent());
    }

    @Test
    void testCrearPedidoExcepcion() {
        when(pedidoRepository.save(any(Pedido.class))).thenThrow(RuntimeException.class);

        BaseResponse response = pedidoService.crearPedido(pedidoRequestDTO);

        assertEquals(Constant.CODE_ERROR_DATABASE, response.getCode());
        assertFalse(response.getData().isPresent());
    }

    @Test
    void testListarTodosLosPedidosExito() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Pedido> pedidos = new PageImpl<>(Collections.singletonList(pedido), pageable, 1);
        when(pedidoRepository.findAll(pageable)).thenReturn(pedidos);

        Page<PedidoResponseDTO> response = pedidoService.listarTodosLosPedidos(0, 10);

        assertEquals(1, response.getTotalElements());
    }

    @Test
    void testBuscarPedidoCodigoExito() {
        when(pedidoRepository.findById(anyInt())).thenReturn(Optional.of(pedido));

        BaseResponse response = pedidoService.buscarPedidoCodigo(1);

        assertEquals(Constant.CODE_SUCCESS, response.getCode());
        assertTrue(response.getData().isPresent());
    }

    @Test
    void testBuscarPedidoCodigoEntidadNoEncontrada() {
        when(pedidoRepository.findById(anyInt())).thenReturn(Optional.empty());

        BaseResponse response = pedidoService.buscarPedidoCodigo(1);

        assertEquals(Constant.CODE_ERROR, response.getCode());
        assertFalse(response.getData().isPresent());
    }

    @Test
    void testBuscarPedidoCodigoExcepcion() {
        when(pedidoRepository.findById(anyInt())).thenThrow(RuntimeException.class);

        BaseResponse response = pedidoService.buscarPedidoCodigo(1);

        assertEquals(Constant.CODE_ERROR_DATABASE, response.getCode());
        assertFalse(response.getData().isPresent());
    }
}
