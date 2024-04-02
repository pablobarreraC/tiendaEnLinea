package edu.unimagdalena.tiendaEnLinea.dto.pedido;

import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteToSaveDto;
import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.StatusPedido;

public record PedidoToSaveDto(
     long id,
     ClienteToSaveDto clienteId,
     String fechaPedido,
     String status
    ) 
{}
