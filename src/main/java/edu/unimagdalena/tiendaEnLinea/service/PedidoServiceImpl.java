package edu.unimagdalena.tiendaEnLinea.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoMapper;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.entity.Pedido;
import edu.unimagdalena.tiendaEnLinea.excepción.NotAbleToDeleteException;
import edu.unimagdalena.tiendaEnLinea.excepción.PedidoNotFoundException;
import edu.unimagdalena.tiendaEnLinea.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService{
  
  /*   private final PedidoMapper pedidoMapper;
    private final PedidoRepository pedidoRepository;

    public PedidoServiceImpl(PedidoMapper pedidoMapper, PedidoRepository pedidoRepository) {
        this.pedidoMapper = pedidoMapper;
        this.pedidoRepository = pedidoRepository;
    }


    @Override
    public PedidoDto guardarPedido(PedidoToSaveDto pedidoDto) {
        Pedido pedido = pedidoMapper.pedidoSaveDtoToPedidoEntity(pedidoDto);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        return pedidoMapper.pedidoEntityToPedidoDto(pedidoGuardado);
    }

    @Override
    public PedidoDto buscarPedidoPorId(Long id) throws PedidoNotFoundException {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado"));
        return pedidoMapper.pedidoEntityToPedidoDto(pedido);
    }

    @Override
    public List<PedidoDto> buscarTodosPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .map(pedido -> pedidoMapper.pedidoEntityToPedidoDto(pedido))
                .toList();
    }

    @Override
    public PedidoDto actualizarPedidoPorId(Long id, PedidoToSaveDto pedidoDto) {
        Pedido pedidoInDb = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado"));

        pedidoInDb.setFechaPedido(pedidoDto.fechaPedido());
        pedidoInDb.setStatus(pedidoDto.status());

        Pedido pedidoGuardado = pedidoRepository.save(pedidoInDb);

        return pedidoMapper.pedidoEntityToPedidoDto(pedidoGuardado);
    }

    @Override
    public void removerPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new NotAbleToDeleteException("Pedido no encontrado"));
        pedidoRepository.delete(pedido);
    }

    @Override
    public List<PedidoDto> buscarPedidosEntreDosFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Pedido> pedidos = pedidoRepository.findPedidoByFechaPedidoBetween(fechaInicio, fechaFin);
        if (pedidos.isEmpty()) {
            throw new PedidoNotFoundException("No se encontraron pedidos en el rango de fechas");
        }
        return pedidos.stream()
                .map(pedido -> pedidoMapper.pedidoEntityToPedidoDto(pedido))
                .toList();
    }

    @Override
    public List<PedidoDto> buscarPedidosPorClienteYUnEstado(Long idCliente, PedidoStatus status) {
        List<Pedido> pedidos = pedidoRepository.findPedidoByClienteAndStatus(idCliente, status);
        if (pedidos.isEmpty()) {
            throw new PedidoNotFoundException("No se encontraron pedidos con el cliente y estado especificados");
        }
        return pedidos.stream()
                .map(pedido -> pedidoMapper.pedidoEntityToPedidoDto(pedido))
                .toList();
    }

    @Override
    public List<PedidoDto> findPedidoByClienteWithItems(Long idCliente) {
        List<Pedido> pedidos = pedidoRepository.findPedidoByClienteWithItems(idCliente);
        if (pedidos.isEmpty()) {
            throw new PedidoNotFoundException("No se encontraron pedidos con el cliente especificado");
        }
        return pedidos.stream()
                .map(pedido -> pedidoMapper.pedidoEntityToPedidoDto(pedido))
                .toList();
    }
 */
}
