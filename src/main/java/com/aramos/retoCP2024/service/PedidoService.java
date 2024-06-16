package com.aramos.retoCP2024.service;

import com.aramos.retoCP2024.dto.request.PedidoRequestDTO;
import com.aramos.retoCP2024.dto.response.BaseResponse;
import com.aramos.retoCP2024.dto.response.PedidoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PedidoService {
   BaseResponse crearPedido(PedidoRequestDTO pedidoRequestDTO);
   Page<PedidoResponseDTO> listarTodosLosPedidos(int pagina,int cantidadElementos);
    Page<PedidoResponseDTO> listarTodosLosPedidosApellidosCliente(String Apellidos,int pagina,int cantidadElementos);
   BaseResponse buscarPedidoCodigo(Integer codigoPedido);
    BaseResponse actualizarPedido(Integer codigoPedido,PedidoRequestDTO pedidoRequestDTO);
     BaseResponse eliminarPedido(Integer codigoPedido);
}
