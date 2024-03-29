package edu.unimagdalena.tiendaEnLinea.repository;

import edu.unimagdalena.tiendaEnLinea.entity.Cliente;
import edu.unimagdalena.tiendaEnLinea.entity.ItemPedido;
import edu.unimagdalena.tiendaEnLinea.entity.Pedido;
import edu.unimagdalena.tiendaEnLinea.entity.Producto;
import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.StatusPedido;
import edu.unimagdalena.tiendaEnLinea.plantillaTestIntegracionPersistencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PedidoRepositoryTest extends plantillaTestIntegracionPersistencia {
    PedidoRepository pedidoRepository;
    ClienteRepository clienteRepository;

    @Autowired
    public PedidoRepositoryTest(PedidoRepository pedidoRepository,ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository=clienteRepository;
    }

    List<Cliente> creacionClientes(){
        Cliente cliente1=clienteRepository.save(Cliente.builder()
                    .nombre("Pablo")
                    .email("pablo@gmail.com")
                    .direccion("Carrera 7").build());

        Cliente cliente2=clienteRepository.save(Cliente.builder()
                    .nombre("Jose")
                .email("jose@gmail.com")
                .direccion("Calle 20 #25-8").build());

        clienteRepository.flush();

        return Arrays.asList(cliente1,cliente2);
    }

    Pedido pedido(){
        Pedido pedido=Pedido.builder()
                .clienteId(creacionClientes().get(0))
                .fechaPedido(LocalDateTime.now())
                .status(StatusPedido.ENVIADO).build();
        return pedido;
    }

    void creacionConjuntoPedidos(){
        List<Cliente> clientes=creacionClientes();

        Pedido pedido1=Pedido.builder()
                .clienteId(clientes.get(0))
                .fechaPedido(LocalDateTime.now())
                .status(StatusPedido.ENVIADO).build();
        pedidoRepository.save(pedido1);

        Pedido pedido2=Pedido.builder()
                .clienteId(clientes.get(1))
                .fechaPedido(LocalDateTime.now())
                .status(StatusPedido.PENDIENTE).build();
        pedidoRepository.save(pedido2);

        pedidoRepository.flush();
    }

    @BeforeEach
    void setUp() {
        pedidoRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    @Test
    void givenAnPedidoWhenSaveThenIdPedidoIsNotNull(){
        //given
        Pedido pedido=pedido();
        //when
        Pedido guardado=pedidoRepository.save(pedido);
        //then
        assertThat(guardado.getId()).isNotNull();
    }

    @Test
    void givenIdPedidoWhenDeleteByIdThenPedidoIsEmpty(){
        //given
        Pedido pedido=pedidoRepository.save(pedido());
        //when
        pedidoRepository.deleteById(pedido.getId());
        //then
        Optional<Pedido> encontrado=pedidoRepository.findById(pedido.getId());
        assertThat(encontrado).isEmpty();
    }

    @Test
    void givenPedidoExistingWhenSaveThenPedidoExistingUpdate(){
        //given
        Pedido pedido=pedidoRepository.save(pedido());
        //when
        pedido.setStatus(StatusPedido.ENTREGADO);
        Pedido actualizado=pedidoRepository.save(pedido);
        //then
        assertThat(actualizado.getStatus()).isEqualTo(StatusPedido.ENTREGADO);
    }

    @Test
    void givenIdPedidoWhenGetByIdThenPedidoIsNotNull(){
        //given
        Pedido pedido=pedidoRepository.save(pedido());
        //when
        Optional<Pedido> encontrado=pedidoRepository.findById(pedido.getId());
        //then
        assertThat(encontrado.get().getId()).isNotNull();
        assertThat(encontrado.get().getStatus()).isEqualTo(StatusPedido.ENVIADO);
    }

    @Test
    void givenASetPedidoWhenFindByFechaPedidoThenHasSizeIsGreaterThanZero(){
        //given
        creacionConjuntoPedidos();
        //when
        List<Pedido> pedidos=pedidoRepository.findByFechaPedidoBetween(
                LocalDateTime.of(2024,3,27,11,35,14),
                LocalDateTime.now()
        );
        //then
        assertThat(pedidos).hasSizeGreaterThan(0);
        assertThat(pedidos).hasSize(2);
    }

    @Test
    void givenASetPedidoWhenFindByClienteAndStatusThenHasSizeIsEqualsToOne(){
        //given
        creacionConjuntoPedidos();
        //when
        Optional<Cliente> cliente=clienteRepository.findById(1L);
        List<Pedido> pedidos=pedidoRepository.findByClienteIdAndStatusIs(
                cliente.get(),StatusPedido.ENVIADO
        );
        //then
        assertThat(pedidos).hasSize(1);
        assertThat(pedidos).first().hasFieldOrPropertyWithValue("status",StatusPedido.ENVIADO);
    }

    @Test
    void givenASetPedidoWhenFindPedidoAndItemsThenHasSizeIsEqualsToOne(){
        //given
        Cliente cliente=clienteRepository.save(
                Cliente.builder()
                        .nombre("Pablo")
                        .email("pablo@gmail.com")
                        .direccion("Carrera 7ma").build());
        clienteRepository.flush();
        Pedido pedido=pedidoRepository.save(
                Pedido.builder()
                        .fechaPedido(LocalDateTime.now())
                        .clienteId(cliente)
                        .status(StatusPedido.ENVIADO).build());
        pedidoRepository.flush();
        //when
        List<Pedido> pedidos=pedidoRepository.findPedidoAndItemsPedidoByClienteId(cliente);
        //then
        assertThat(pedidos).hasSize(1);
    }
}