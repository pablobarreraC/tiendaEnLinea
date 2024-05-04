package edu.unimagdalena.tiendaEnLinea.service;

import java.time.LocalDateTime;
import java.util.List;

import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteDto;

import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.excepción.PedidoNotFoundException;

public interface PedidoService {
    PedidoDto guardarPedido(PedidoToSaveDto pedido);
    PedidoDto actualizarPedidoPorId(Long id, PedidoToSaveDto pedido);
    PedidoDto buscarPedidoPorId(Long id) throws PedidoNotFoundException;
    void removerPedido(Long id);
    List<PedidoDto> buscarTodosPedidos();

    List<PedidoDto> buscarPedidosEntreDosFecha(LocalDateTime fechaInicial,LocalDateTime fechaFinal);

    List<PedidoDto> buscarPedidosPorClienteYUnEstado(ClienteDto idCliente, String statusPedido);

    List<PedidoDto> findPedidoByClienteWithItems(ClienteDto idCliente);
}
