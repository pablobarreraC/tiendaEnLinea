package edu.unimagdalena.tiendaEnLinea.controller;

import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoDto;
import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.excepción.ProductoNotFoundException;
import edu.unimagdalena.tiendaEnLinea.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class productoController {
    private final ProductoService productoService;

    public productoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<ProductoDto>> obtenerProductos() {
        List<ProductoDto> productos = productoService.buscarTodosProductos();
        return ResponseEntity.ok().body(productos);
    }

    @PostMapping
    public ResponseEntity<ProductoDto> guardarProducto(@RequestBody ProductoToSaveDto producto) {
        ProductoDto productoDto = productoService.guardarProducto(producto);
        return ResponseEntity.ok().body(productoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> actualizarProducto(@PathVariable Long id, @RequestBody ProductoToSaveDto producto) {
        ProductoDto actualizado = productoService.actualizarProductoPorId(id, producto);
        return ResponseEntity.ok().body(actualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> buscarProductoPorId(@PathVariable Long id) {
        try {
            ProductoDto productoDto = productoService.buscarProductoPorId(id);
            return ResponseEntity.ok().body(productoDto);
        } catch (ProductoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarProductoPorId(@PathVariable Long id) {
        productoService.removerProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<List<ProductoDto>> buscarProductoPorNombre(@PathVariable String nombre) {
        List<ProductoDto> productoDtos = productoService.buscarProductoPorNombre(nombre);
        return ResponseEntity.ok().body(productoDtos);
    }

    @GetMapping
    public ResponseEntity<List<ProductoDto>> productosEnStock() {
        List<ProductoDto> productoDtos = productoService.buscarLosProductosQueEstanEnStock();
        return ResponseEntity.ok().body(productoDtos);
    }

    @GetMapping("/{price}/{stock}")
    public ResponseEntity<List<ProductoDto>> buscarProductosPorPrecioYStock(@PathVariable Double price, @PathVariable Integer stock) {
        List<ProductoDto> productoDtos = productoService.buscarLosProductosQueNoSuperenUnPrecioYUnStockDeterminado(price, stock);
        return ResponseEntity.ok().body(productoDtos);
    }
}
