package edu.unimagdalena.tiendaEnLinea.dto.pedido;
import java.util.Collections;
import java.util.List;

import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteDto;
import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pago.PagoDto;
import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.MetodoPago;
import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.StatusPedido;
import edu.unimagdalena.tiendaEnLinea.dto.detalleEnvio.DetalleEnvioDto;

public record PedidoDto(
    long id,
    List<ItemPedidoDto> itemsPedido,
    PagoDto pago,
    String fechaPedido,
    DetalleEnvioDto detalleEnvio,
    String status
) {
  
    public List<ItemPedidoDto> itemsPedido(){
        return Collections.unmodifiableList(itemsPedido());
    }
}
