package edu.unimagdalena.tiendaEnLinea.service;

import java.util.List;

import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoDto;
import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoToSaveDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.unimagdalena.tiendaEnLinea.dto.product.ProductDto;
import edu.unimagdalena.tiendaEnLinea.dto.product.ProductToSaveDto;
import edu.unimagdalena.tiendaEnLinea.excepción.ProductoNotFoundException;

public interface ProductoService {
    ProductoDto guardarProducto(ProductoToSaveDto product);
    ProductoDto actualizarProductoPorId(Long id, ProductoToSaveDto product);
    ProductoDto buscarProductoPorId(Long id)throws ProductoNotFoundException;
    void removerProduct(Long id);
    List<ProductoDto> buscarTodosProductos();

    List<ProductoDto> buscarProductoPorNombre(String nombre);
    List<ProductoDto> buscarLosProductosQueEstanEnStock();
    List<ProductoDto> buscarLosProductosQueNoSuperenUnPrecioYUnStockDeterminado(Double price,Integer stock);
}
