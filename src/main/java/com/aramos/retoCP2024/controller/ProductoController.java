package com.aramos.retoCP2024.controller;

import com.aramos.retoCP2024.dto.request.ProductoRequestDTO;
import com.aramos.retoCP2024.dto.response.BaseResponse;
import com.aramos.retoCP2024.dto.response.ProductoResponseDTO;
import com.aramos.retoCP2024.service.ProductoService;
import com.aramos.retoCP2024.util.ProductoProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/productos")
@RequiredArgsConstructor
public class ProductoController {

    @Autowired
    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<BaseResponse> crearProducto(@RequestBody ProductoRequestDTO productoRequestDTO) {
        BaseResponse response = productoService.crearProducto(productoRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping
    public ResponseEntity<Page<ProductoResponseDTO>> listarProductos(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "cantidadElementos", defaultValue = "10") int cantidadElementos) {
        Page<ProductoResponseDTO> productos = productoService.listarProductos(pagina, cantidadElementos);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/porPrecio")
    public ResponseEntity<Page<ProductoProjection>> listarProductoPrecio(
            @RequestParam("precioMinimo") double precioMinimo,
            @RequestParam("precioMaximo") double precioMaximo,
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "cantidadElementos", defaultValue = "10") int cantidadElementos) {
        Page<ProductoProjection> productos = productoService.listarProductoPrecio(precioMinimo, precioMaximo, pagina, cantidadElementos);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/porNombre")
    public ResponseEntity<Page<ProductoProjection>> listarProductoNombre(
            @RequestParam("nombre") String nombre,
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "cantidadElementos", defaultValue = "10") int cantidadElementos) {
        Page<ProductoProjection> productos = productoService.listarProductoNombre(nombre, pagina, cantidadElementos);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/{codigoProducto}")
    public ResponseEntity<BaseResponse> buscarProductoCodigo(@PathVariable("codigoProducto") Integer codigoProducto) {
        BaseResponse response = productoService.buscarProductoCodigo(codigoProducto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @DeleteMapping("/{codigoProducto}")
    public ResponseEntity<BaseResponse> eliminarProducto(@PathVariable("codigoProducto") Integer codigoProducto) {
        BaseResponse response = productoService.elminarProducto(codigoProducto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @PutMapping("/{codigoProducto}")
    public ResponseEntity<BaseResponse> actualizarProducto(
            @PathVariable("codigoProducto") Integer codigoProducto,
            @RequestBody ProductoRequestDTO productoRequestDTO) {
        BaseResponse response = productoService.actualizarProducto(codigoProducto, productoRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}