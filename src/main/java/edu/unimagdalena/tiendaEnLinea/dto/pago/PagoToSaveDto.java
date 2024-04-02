package edu.unimagdalena.tiendaEnLinea.dto.pago;

import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.MetodoPago;

public record PagoToSaveDto(
    Long id,
    PedidoToSaveDto pedidoId,
    Double totalPago,
    String fechaPago,
    String metodoPago
)
{}
