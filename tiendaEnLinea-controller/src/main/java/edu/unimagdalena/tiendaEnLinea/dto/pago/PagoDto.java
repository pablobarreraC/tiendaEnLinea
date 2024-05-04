package edu.unimagdalena.tiendaEnLinea.dto.pago;

import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.MetodoPago;

public record PagoDto(
    Long id,
    Double totalPago,
    String fechaPago,
    String metodoPago
) 
{}
