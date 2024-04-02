package edu.unimagdalena.tiendaEnLinea.service;

import java.time.LocalDateTime;
import java.util.List;

import edu.unimagdalena.tiendaEnLinea.dto.pago.PagoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pago.PagoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.MetodoPago;
import edu.unimagdalena.tiendaEnLinea.excepción.PagoNotFoundException;

public interface PagoService{
    PagoDto guardarPago(PagoToSaveDto pago);
    PagoDto actualizarPagoPorId(Long id, PagoToSaveDto pago);
    PagoDto buscarPagoPorId(Long id) throws PagoNotFoundException;
    void removerPago(Long id);
    List<PagoDto> buscarTodosPagos();

    
    List<PagoDto>recuperarPagosDentroDeUnRangoDeFecha(LocalDateTime fechaIni, LocalDateTime fechaFin);
    List<PagoDto> recuperarPagosPorUnIdentificadorDeUnaOrdenYMétodoDePago(PedidoDto idPedido, String metodoPago);

}
