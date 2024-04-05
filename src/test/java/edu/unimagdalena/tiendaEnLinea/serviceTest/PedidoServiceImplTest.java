
package edu.unimagdalena.tiendaEnLinea.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoMapper;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.entity.Cliente;
import edu.unimagdalena.tiendaEnLinea.entity.Pedido;
import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.StatusPedido;
import edu.unimagdalena.tiendaEnLinea.repository.PedidoRepository;
import edu.unimagdalena.tiendaEnLinea.service.PedidoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceImplTest {
     @Mock
    private PedidoMapper pedidoMapper;
    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    private Pedido pedido, pedido2, pedido3;
    private PedidoDto pedidoDto;

    @BeforeEach
    void setUp() {
        pedido = Pedido.builder()
                .status(StatusPedido.ENTREGADO)
                .id(1l)
                .clienteId(null)
                .detalleEnvio(null)
                .fechaPedido(LocalDateTime.now())
                .pago(null)
                .itemsPedido(null)
                .build();

        pedido2 = Pedido.builder()
        .status(StatusPedido.ENVIADO)
        .id(2l)
        .clienteId(null)
        .detalleEnvio(null)
        .fechaPedido(LocalDateTime.now())
        .pago(null)
        .itemsPedido(null)
        .build();


        pedido3 = Pedido.builder()
        .status(StatusPedido.PENDIENTE)
        .id(3l)
        .clienteId(null)
        .detalleEnvio(null)
        .fechaPedido(LocalDateTime.now())
        .pago(null)
        .itemsPedido(null)
        .build();

        pedidoDto = PedidoMapper.instancia.pedidoEntityToDto(pedido);
    }


 @Test
    void testActualizarPedidoPorId() {
        when(pedidoRepository.findById(any())).thenReturn(Optional.of(pedido2));

        PedidoToSaveDto pedido= new PedidoToSaveDto(4L,null,LocalDateTime.now().toString(),"ENTREGADO");

        when(pedidoMapper.pedidoEntityToDto(any())).thenReturn(pedidoDto);;

        PedidoDto pedidoActualizado = pedidoService.actualizarPedidoPorId(1l, pedido);

        assertThat(pedidoActualizado).isNotNull();
    }

    @Test
    void testBuscarPedidoPorId() {
        when(pedidoRepository.findById(any())).thenReturn(Optional.of(pedido));

        when(pedidoMapper.pedidoEntityToDto(any())).thenReturn(pedidoDto);

        PedidoDto pedidoEncontrado = pedidoService.buscarPedidoPorId(1l);

        assertThat(pedidoEncontrado).isNotNull();


    }

    @Test
    void testBuscarTodosPedidos() {
        List<Pedido> pedidos = List.of(pedido, pedido2, pedido3);

        when(pedidoRepository.findAll()).thenReturn(pedidos);

        List<PedidoDto> pedidosDto = pedidoService.buscarTodosPedidos();

        assertThat(pedidosDto).isNotEmpty();
        assertThat(pedidosDto).hasSize(3);

    }

    @Test
    void testGuardarPedido() {
        PedidoToSaveDto pedidoAGuardar= new PedidoToSaveDto(1L,null,LocalDateTime.now().toString(),"ENTREGADO");
        when(pedidoMapper.pedidoToSaveDtoToEntity(pedidoAGuardar)).thenReturn(pedido);

        when(pedidoMapper.pedidoEntityToDto(any())).thenReturn(pedidoDto); 

        PedidoDto pedidoGuardado = pedidoService.guardarPedido(pedidoAGuardar);

        assertThat(pedidoGuardado).isNotNull();

    }

    @Test
    void testfindPedidoByClienteWithItems() {
        Cliente cliente = clienteList();

        List<Pedido> pedidos = List.of(pedido,pedido2,pedido3);

        when(pedidoRepository.findPedidoAndItemsPedidoByClienteId(cliente)).thenReturn(pedidos);

        List<PedidoDto> pedidoDtoList = pedidoService.findPedidoByClienteWithItems(cliente);

        assertThat(pedidoDtoList).hasSize(1);

    }

    @Test
    void testbuscarPedidosEntreDosFecha() {
        List<Pedido> pedidos = List.of(pedido, pedido);

        when(pedidoRepository.findByFechaPedidoBetween(any(), any())).thenReturn(pedidos);

        List<PedidoDto> pedidoDtoList = pedidoService.buscarPedidosEntreDosFecha(any(), any());

        assertThat(pedidoDtoList).hasSize(2);

    }
    

    @Test
    void testBuscarPedidosPorClienteYUnEstado() {
        
        List<Pedido> pedidos = List.of(pedido);

        when(pedidoRepository.findByClienteIdAndStatusIs(cliente, statusPedido)).thenReturn(pedidos);

        List<PedidoDto> pedidoDtoList = pedidoService.buscarPedidosPorClienteYUnEstado(cliente, statusPedido);

        assertThat(pedidoDtoList).hasSize(1);

    }

    @Test
    void testRemoverPedido() {
        Long idPedido = 1l;
        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedido));
        pedidoService.removerPedido(idPedido);

        verify(pedidoRepository, times(1)).delete(any());

    }
}
