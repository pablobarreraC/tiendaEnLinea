package edu.unimagdalena.tiendaEnLinea.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteDto;
import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteMapper;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoMapper;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.entity.Cliente;
import edu.unimagdalena.tiendaEnLinea.entity.Pedido;
import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.StatusPedido;
import edu.unimagdalena.tiendaEnLinea.excepción.NotAbleToDeleteException;
import edu.unimagdalena.tiendaEnLinea.excepción.PedidoNotFoundException;
import edu.unimagdalena.tiendaEnLinea.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService{
  
     private final PedidoMapper pedidoMapper;
    private final PedidoRepository pedidoRepository;

    public PedidoServiceImpl(PedidoMapper pedidoMapper, PedidoRepository pedidoRepository) {
        this.pedidoMapper = pedidoMapper;
        this.pedidoRepository = pedidoRepository;
    }


    @Override
    public PedidoDto guardarPedido(PedidoToSaveDto pedidoDto) {
        Pedido pedido = pedidoMapper.pedidoToSaveDtoToEntity(pedidoDto);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        return pedidoMapper.pedidoEntityToDto(pedidoGuardado);
    }

    @Override
    public PedidoDto buscarPedidoPorId(Long id) throws PedidoNotFoundException {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado"));
        return pedidoMapper.pedidoEntityToDto(pedido);
    }

    @Override
    public List<PedidoDto> buscarTodosPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .map(pedido -> pedidoMapper.pedidoEntityToDto(pedido))
                .toList();
    }

    @Override
    public PedidoDto actualizarPedidoPorId(Long id, PedidoToSaveDto pedidoDto) {
        Pedido pedido=pedidoMapper.pedidoToSaveDtoToEntity(pedidoDto);
        Pedido pedidoInDb = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado"));

        pedidoInDb.setFechaPedido(pedido.getFechaPedido());
        pedidoInDb.setStatus(pedido.getStatus());

        Pedido pedidoGuardado = pedidoRepository.save(pedidoInDb);

        return pedidoMapper.pedidoEntityToDto(pedidoGuardado);
    }

    @Override
    public void removerPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new NotAbleToDeleteException("Pedido no encontrado"));
        pedidoRepository.delete(pedido);
    }

    @Override
    public List<PedidoDto> buscarPedidosEntreDosFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Pedido> pedidos = pedidoRepository.findByFechaPedidoBetween(fechaInicio, fechaFin);
        if (pedidos.isEmpty()) {
            throw new PedidoNotFoundException("No se encontraron pedidos en el rango de fechas");
        }
        return pedidos.stream()
                .map(pedido -> pedidoMapper.pedidoEntityToDto(pedido))
                .toList();
    }

    @Override
    public List<PedidoDto> buscarPedidosPorClienteYUnEstado(ClienteDto idCliente, String status) {
        Cliente cliente=ClienteMapper.instancia.clienteDtoToEntity(idCliente);


        List<Pedido> pedidos = pedidoRepository.findByClienteIdAndStatusIs(cliente, status);
        if (pedidos.isEmpty()) {
            throw new PedidoNotFoundException("No se encontraron pedidos con el cliente y estado especificados");
        }
        return pedidos.stream()
                .map(pedido -> pedidoMapper.pedidoEntityToDto(pedido))
                .toList();
    }

    @Override
    public List<PedidoDto> findPedidoByClienteWithItems(ClienteDto idCliente) {
        Cliente cliente= ClienteMapper.instancia.clienteDtoToEntity(idCliente);
        List<Pedido> pedidos = pedidoRepository.findPedidoAndItemsPedidoByClienteId(cliente);
        if (pedidos.isEmpty()) {
            throw new PedidoNotFoundException("No se encontraron pedidos con el cliente especificado");
        }
        return pedidos.stream()
                .map(pedido -> pedidoMapper.pedidoEntityToDto(pedido))
                .toList();
    }

}
