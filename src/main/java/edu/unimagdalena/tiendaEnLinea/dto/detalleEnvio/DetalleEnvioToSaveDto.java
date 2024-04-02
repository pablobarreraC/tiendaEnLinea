package edu.unimagdalena.tiendaEnLinea.dto.detalleEnvio;

import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;

public record DetalleEnvioToSaveDto(long id,
    PedidoDto pedidoId,
    String direccion,
    String transportadora,
    String numeroGuia)
{}
