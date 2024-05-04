package edu.unimagdalena.tiendaEnLinea.dto.cliente;

import java.util.Collections;
import java.util.List;

import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;

public record ClienteDto(
    long id,
    String nombre,
    String email,
    String direccion,
    String rol,
    String password,
    List<PedidoDto> pedidos 
) {
  public List<PedidoDto> pedidos(){
        return Collections.unmodifiableList(pedidos);
    }
}
