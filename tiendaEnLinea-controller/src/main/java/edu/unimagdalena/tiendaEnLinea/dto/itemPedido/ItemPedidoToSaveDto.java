package edu.unimagdalena.tiendaEnLinea.dto.itemPedido;


import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoToSaveDto;

public record ItemPedidoToSaveDto(
    Long id,
    PedidoToSaveDto pedidoId,
    ProductoToSaveDto productoId,
    Integer cantidad,
    Double precioUnitario
){}
