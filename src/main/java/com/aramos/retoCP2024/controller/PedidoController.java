package com.aramos.retoCP2024.controller;

import com.aramos.retoCP2024.dto.request.PedidoRequestDTO;
import com.aramos.retoCP2024.dto.response.BaseResponse;
import com.aramos.retoCP2024.dto.response.PedidoResponseDTO;
import com.aramos.retoCP2024.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/pedidos")
@RequiredArgsConstructor
@Slf4j
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<BaseResponse> crearPedido(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        BaseResponse response = pedidoService.crearPedido(pedidoRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping
    public ResponseEntity<Page<PedidoResponseDTO>> listarTodosLosPedidos(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "cantidadElementos", defaultValue = "10") int cantidadElementos) {
        Page<PedidoResponseDTO> pedidos = pedidoService.listarTodosLosPedidos(pagina, cantidadElementos);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/buscarPorApellidos")
    public ResponseEntity<Page<PedidoResponseDTO>> listarTodosLosPedidosApellidosCliente(
            @RequestParam("apellidos") String apellidos,
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "cantidadElementos", defaultValue = "10") int cantidadElementos) {
        Page<PedidoResponseDTO> pedidos = pedidoService.listarTodosLosPedidosApellidosCliente(apellidos, pagina, cantidadElementos);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/{codigoPedido}")
    public ResponseEntity<BaseResponse> buscarPedidoCodigo(@PathVariable("codigoPedido") Integer codigoPedido) {
        BaseResponse response = pedidoService.buscarPedidoCodigo(codigoPedido);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @PutMapping("/{codigoPedido}")
    public ResponseEntity<BaseResponse> actualizarPedido(
            @PathVariable("codigoPedido") Integer codigoPedido,
            @RequestBody PedidoRequestDTO pedidoRequestDTO) {
        BaseResponse response = pedidoService.actualizarPedido(codigoPedido, pedidoRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
    @DeleteMapping("/{codigoPedido}")
    public ResponseEntity<BaseResponse> eliminarPedido(
            @PathVariable("codigoPedido") Integer codigoPedido) {
        BaseResponse response = pedidoService.eliminarPedido(codigoPedido);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}
