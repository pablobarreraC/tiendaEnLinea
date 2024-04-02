package edu.unimagdalena.tiendaEnLinea.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.excepción.ItemPedidoNotFoundException;


public interface ItemPedidoService {
    ItemPedidoDto guardarItemPedido(ItemPedidoToSaveDto itemPedido);
    ItemPedidoDto actualizarItemPedidoPorId(Long id, ItemPedidoToSaveDto itemPedido);
    ItemPedidoDto buscarItemPedidoPorId(Long id) throws ItemPedidoNotFoundException;
    void removerItemPedido(Long id);
    List<ItemPedidoDto> buscarTodosItemPedidos();


    List<ItemPedidoDto> buscarItemsDelPedidoPorPedidoId( Long idPedido);
    List<ItemPedidoDto> buscarItemsDelPedidoParaUnProductoEspecífico(Long idProducto);

    Integer calcularLaSumaDelTotalDeVentasParaUnProducto(Long idProducto);
}
