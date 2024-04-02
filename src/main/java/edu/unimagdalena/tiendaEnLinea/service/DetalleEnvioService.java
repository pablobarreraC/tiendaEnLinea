package edu.unimagdalena.tiendaEnLinea.service;


import java.util.List;

import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.entity.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.unimagdalena.tiendaEnLinea.dto.detalleEnvio.DetalleEnvioDto;
import edu.unimagdalena.tiendaEnLinea.dto.detalleEnvio.DetalleEnvioToSaveDto;
import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.StatusPedido;
import edu.unimagdalena.tiendaEnLinea.excepción.DetalleEnvioNotFoundException;

public interface DetalleEnvioService {
    DetalleEnvioDto guardarDetalleEnvio(DetalleEnvioToSaveDto detalleEnvio);
    DetalleEnvioDto actualizarDetalleEnvioPorId(Long id, DetalleEnvioToSaveDto detalleEnvio);
    DetalleEnvioDto buscarDetalleEnvioPorId(Long id) throws DetalleEnvioNotFoundException;
    void removerDetallePedido(Long id);

    List<DetalleEnvioDto> buscarTodosDetallesEnvio();


    List<DetalleEnvioDto> buscarLosDetallesDelEnvíoPorPedidoId(PedidoDto pedidoId);
    List<DetalleEnvioDto> buscarLosDetallesDelEnvíoParaUnaTrasportadora(String transportadora);
    List<DetalleEnvioDto> buscarLosDetallesDelEnvioPorStatus(String status);
}
