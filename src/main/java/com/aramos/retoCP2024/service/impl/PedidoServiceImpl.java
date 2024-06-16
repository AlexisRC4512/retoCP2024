package com.aramos.retoCP2024.service.impl;

import com.aramos.retoCP2024.aggregates.Constant;
import com.aramos.retoCP2024.dto.request.PedidoRequestDTO;
import com.aramos.retoCP2024.dto.response.BaseResponse;
import com.aramos.retoCP2024.dto.response.PedidoResponseDTO;
import com.aramos.retoCP2024.entity.Pedido;
import com.aramos.retoCP2024.entity.Producto;
import com.aramos.retoCP2024.excepcionPersonalizada.CustomDatabaseException;
import com.aramos.retoCP2024.excepcionPersonalizada.CustomInternalException;
import com.aramos.retoCP2024.repository.PedidoRepository;
import com.aramos.retoCP2024.repository.ProductoRepository;
import com.aramos.retoCP2024.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    @Override
    public BaseResponse crearPedido(PedidoRequestDTO pedidoRequestDTO) {
       try {
           Pedido objPedido = pedidoRepository.save(convertirPedidoDtoaEntity(pedidoRequestDTO));
           return new BaseResponse(Constant.CODE_SUCCESS,Constant.MESS_SUCCESS_PEDIDO,Optional.of(objPedido));
       } catch (EntityNotFoundException e) {
           log.error(e.getMessage());
           return new BaseResponse(Constant.CODE_ERROR, e.getMessage(), Optional.empty());
       } catch (Exception e) {
           log.error(e.getMessage());
           return new BaseResponse(Constant.CODE_ERROR_DATABASE, Constant.MESS_ERROR_DATABASE, Optional.empty());
       }
    }

    @Override
    public Page<PedidoResponseDTO> listarTodosLosPedidos(int pagina, int cantidadElementos) {
        try {
            Pageable pageable = PageRequest.of(pagina, cantidadElementos);
            Page<Pedido> pedidos = pedidoRepository.findAll(pageable);
            return pedidos.map(this::convertirPedidoEntityaDTO);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new CustomDatabaseException(Constant.MESS_ERROR_DATABASE_ACCESS, e);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomInternalException(Constant.MESS_ERROR_DATABASE, e);
        }
    }

    @Override
    public Page<PedidoResponseDTO> listarTodosLosPedidosApellidosCliente(String apellidos, int pagina, int cantidadElementos) {
        try {
            Pageable pageable = PageRequest.of(pagina, cantidadElementos);
            Page<Pedido> pedidos = pedidoRepository.findByClienteApellidos(apellidos, pageable);
            return pedidos.map(this::convertirPedidoEntityaDTO);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new CustomDatabaseException(Constant.MESS_ERROR_DATABASE_ACCESS, e);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomInternalException(Constant.MESS_ERROR_DATABASE, e);
        }
    }

    @Override
    public BaseResponse buscarPedidoCodigo(Integer codigoPedido) {
        try {
            Pedido pedido = pedidoRepository.findById(codigoPedido)
                    .orElseThrow(() -> new EntityNotFoundException(Constant.MESS_FAILED_FIND_PEDIDO));
            PedidoResponseDTO pedidoResponseDTO = convertirPedidoEntityaDTO(pedido);
            return new BaseResponse(Constant.CODE_SUCCESS, Constant.MESS_SUCCESS_FIND_PEDIDO, Optional.of(pedidoResponseDTO));
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return new BaseResponse(Constant.CODE_ERROR, e.getMessage(), Optional.empty());
        } catch (Exception e) {
            log.error(e.getMessage());
            return new BaseResponse(Constant.CODE_ERROR_DATABASE, Constant.MESS_ERROR_DATABASE, Optional.empty());
        }
    }

    @Override
    public BaseResponse actualizarPedido(Integer codigoPedido, PedidoRequestDTO pedidoRequestDTO) {
        try {
            Pedido pedidoExistente = pedidoRepository.findById(codigoPedido)
                    .orElseThrow(() -> new EntityNotFoundException(Constant.MESS_FAILED_FIND_PEDIDO));
            Producto producto = productoRepository.findById(pedidoRequestDTO.getCodigoProducto())
                    .orElseThrow(() -> new EntityNotFoundException(Constant.MESS_FAILED_FIND_PRODUCTO));
            pedidoExistente.setProducto(producto);
            pedidoExistente.setCantidad(pedidoRequestDTO.getCantidad());
            pedidoExistente.setCliente(pedidoRequestDTO.getCliente());
            Pedido pedidoActualizado = pedidoRepository.save(pedidoExistente);
            PedidoResponseDTO pedidoResponseDTO = convertirPedidoEntityaDTO(pedidoActualizado);
            return new BaseResponse(Constant.CODE_SUCCESS, Constant.MESS_SUCCESS_UPDATE_PEDIDO, Optional.of(pedidoResponseDTO));
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return new BaseResponse(Constant.CODE_ERROR, e.getMessage(), Optional.empty());
        } catch (Exception e) {
            log.error(e.getMessage());
            return new BaseResponse(Constant.CODE_ERROR_DATABASE, Constant.MESS_ERROR_DATABASE, Optional.empty());
        }
    }

    @Override
    public BaseResponse eliminarPedido(Integer codigoPedido) {
        try {
            Pedido pedido = pedidoRepository.findById(codigoPedido)
                    .orElseThrow(() -> new EntityNotFoundException(Constant.MESS_FAILED_FIND_PEDIDO));

            pedidoRepository.delete(pedido);
            return new BaseResponse(Constant.CODE_SUCCESS, Constant.MESS_SUCCESS_DELETE_PEDIDO, Optional.empty());
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return new BaseResponse(Constant.CODE_ERROR, e.getMessage(), Optional.empty());
        } catch (Exception e) {
            log.error(e.getMessage());
            return new BaseResponse(Constant.CODE_ERROR_DATABASE, Constant.MESS_ERROR_DATABASE, Optional.empty());
        }
    }


    public Pedido convertirPedidoDtoaEntity(PedidoRequestDTO pedidoRequestDTO){
        Optional<Producto> producto=productoRepository.findById(pedidoRequestDTO.getCodigoProducto());
        Pedido pedido =Pedido.builder().producto(producto.get()).cantidad(pedidoRequestDTO.getCantidad())
                .cliente(pedidoRequestDTO.getCliente()).build();
        return pedido;
    }
    public PedidoResponseDTO convertirPedidoEntityaDTO(Pedido pedido){
        PedidoResponseDTO pedidoResponseDTO=PedidoResponseDTO.builder().nroPedido(pedido.getNroPedido()).cantidad(pedido.getCantidad())
                .cliente(pedido.getCliente()).producto(pedido.getProducto()).build();
        return pedidoResponseDTO;
    }
}
