package edu.unimagdalena.tiendaEnLinea.service;

import java.util.List;

import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoDto;

import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.excepción.ItemPedidoNotFoundException;


public interface ItemPedidoService {
    ItemPedidoDto guardarItemPedido(ItemPedidoToSaveDto itemPedido);
    ItemPedidoDto actualizarItemPedidoPorId(Long id, ItemPedidoToSaveDto itemPedido);
    ItemPedidoDto buscarItemPedidoPorId(Long id) throws ItemPedidoNotFoundException;
    void removerItemPedido(Long id);
    List<ItemPedidoDto> buscarTodosItemPedidos();


    List<ItemPedidoDto> buscarItemsDelPedidoPorPedidoId( PedidoDto idPedido);
    List<ItemPedidoDto> buscarItemsDelPedidoParaUnProductoEspecífico(ProductoDto idProducto);

    Integer calcularLaSumaDelTotalDeVentasParaUnProducto(ProductoDto idProducto);
}
