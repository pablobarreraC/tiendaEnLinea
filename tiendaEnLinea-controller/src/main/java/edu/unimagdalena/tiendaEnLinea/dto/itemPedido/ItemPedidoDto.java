package edu.unimagdalena.tiendaEnLinea.dto.itemPedido;

import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoDto;
public record ItemPedidoDto(
    Long id,
    ProductoDto productoId,
    Integer cantidad,
    Double precioUnitario
) 
{}
