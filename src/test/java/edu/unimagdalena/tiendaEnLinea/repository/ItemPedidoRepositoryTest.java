package edu.unimagdalena.tiendaEnLinea.repository;

import edu.unimagdalena.tiendaEnLinea.entity.Cliente;
import edu.unimagdalena.tiendaEnLinea.entity.ItemPedido;
import edu.unimagdalena.tiendaEnLinea.entity.Pedido;
import edu.unimagdalena.tiendaEnLinea.entity.Producto;
import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.StatusPedido;
import edu.unimagdalena.tiendaEnLinea.plantillaTestIntegracionPersistencia;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemPedidoRepositoryTest extends plantillaTestIntegracionPersistencia {
    ItemPedidoRepository itemPedidoRepository;
    PedidoRepository pedidoRepository;
    ProductoRepository productoRepository;
    ClienteRepository clienteRepository;

    @Autowired
    public ItemPedidoRepositoryTest(ItemPedidoRepository itemPedidoRepository, PedidoRepository pedidoRepository, ProductoRepository productoRepository, ClienteRepository clienteRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
    }

    @BeforeEach
    void setUp() {
        itemPedidoRepository.deleteAll();
        pedidoRepository.deleteAll();
        productoRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    List<Cliente> clientes(){
        Cliente cliente1=clienteRepository.save(
                Cliente.builder()
                        .nombre("Pablo")
                        .email("pablo@gmail.com")
                        .direccion("Carrera 8").build());
        Cliente cliente2=clienteRepository.save(
                Cliente.builder()
                        .nombre("Wilkin")
                        .email("wilkin@gmail.com")
                        .direccion("Carrera 20 #12-4").build()
        );
        clienteRepository.flush();

        return Arrays.asList(cliente1,cliente2);
    }

    List<Pedido> pedidos(){
        List<Cliente> clientes=clientes();
        Pedido pedido1=pedidoRepository.save(
                Pedido.builder()
                        .clienteId(clientes.get(0))
                        .fechaPedido(LocalDateTime.now())
                        .status(StatusPedido.ENVIADO).build());
        Pedido pedido2=pedidoRepository.save(
                Pedido.builder()
                        .clienteId(clientes.get(1))
                        .fechaPedido(LocalDateTime.now())
                        .status(StatusPedido.PENDIENTE).build());
        pedidoRepository.flush();

        return Arrays.asList(pedido1,pedido2);
    }

    List<Producto> productos(){
        Producto producto1=productoRepository.save(
                Producto.builder()
                .nombre("Producto1")
                .price(40.000d)
                .stock(30)
                .build());
        Producto producto2=productoRepository.save(
                Producto.builder()
                .nombre("Producto2")
                .price(30.000d)
                .stock(10)
                .build());
        productoRepository.flush();

        return Arrays.asList(producto1,producto2);
    }

    void conjuntoItemsPedidos(){
        List<Pedido> pedidos=pedidos();
        List<Producto> productos=productos();

        itemPedidoRepository.save(
                ItemPedido.builder()
                        .pedidoId(pedidos.get(0))
                        .productoId(productos.get(0))
                        .cantidad(10)
                        .precioUnitario(20.000d)
                        .build());
        itemPedidoRepository.save(
                ItemPedido.builder()
                        .pedidoId(pedidos.get(1))
                        .productoId(productos.get(1))
                        .cantidad(20)
                        .precioUnitario(24.000d)
                        .build());
        itemPedidoRepository.flush();
    }

    ItemPedido itemPedido(){
        return ItemPedido.builder()
                .pedidoId(pedidos().get(0))
                .productoId(productos().get(0))
                .cantidad(20)
                .precioUnitario(200.000d)
                .build();
    }

    @Test
    void givenAnItemPedidoWhenSaveThenIdItemPedidoIsNotNull(){
        //given
        ItemPedido itemPedido=itemPedido();
        //when
        ItemPedido guardado=itemPedidoRepository.save(itemPedido);
        //then
        assertThat(guardado.getId()).isNotNull();
    }

    @Test
    void givenItemPedidoExistingWhenSaveThenPedidoExistingUpdate(){
        //given
        ItemPedido itemPedido=itemPedidoRepository.save(itemPedido());
        //when
        itemPedido.setCantidad(200);
        ItemPedido actualizado=itemPedidoRepository.save(itemPedido);
        //then
        assertThat(actualizado.getCantidad()).isEqualTo(200);
        assertThat(actualizado.getId()).isEqualTo(itemPedido.getId());
    }

    @Test
    void givenIdItemPedidoWhenDeleteByIdThenItemPedidoIsEmpty(){
        //given
        ItemPedido itemPedido=itemPedidoRepository.save(itemPedido());
        //when
        itemPedidoRepository.deleteById(itemPedido.getId());
        //then
        Optional<ItemPedido> encontrado=itemPedidoRepository.findById(itemPedido.getId());
        assertThat(encontrado).isEmpty();
    }

    @Test
    void givenAnItemPedidoWhenFindByIdThenIdItemPedidoIsNotNull(){
        //given
        ItemPedido itemPedido=itemPedidoRepository.save(itemPedido());
        //when
        Optional<ItemPedido> encontrado=itemPedidoRepository.findById(itemPedido.getId());
        //then
        assertThat(encontrado.get().getId()).isNotNull();
        assertThat(encontrado.get().getCantidad()).isEqualTo(20);
    }

    @Test
    void givenASetItemsPedidoWhenFindByPedidoIdThenHasSizeIsGreaterThanZero(){
        //given
        conjuntoItemsPedidos();
        //when
        List<ItemPedido> itemPedidos=itemPedidoRepository.findByPedidoId(
                pedidoRepository.findById(1L).get());
        //then
        assertThat(itemPedidos).hasSizeGreaterThan(0);
        assertThat(itemPedidos).first().hasFieldOrPropertyWithValue("cantidad",10);
    }

    @Test
    void givenASetItemsPedidoWhenFindByProductoIdThenHasSizeIsGreaterThanZero(){
        //given
        conjuntoItemsPedidos();
        //when
        List<ItemPedido> itemPedidos=itemPedidoRepository.findByProductoId(
                productoRepository.findById(1L).get());
        //then
        assertThat(itemPedidos).hasSizeGreaterThan(0);
        assertThat(itemPedidos).first().hasFieldOrPropertyWithValue("precioUnitario",20.000d);
    }

    @Test
    void givenASetItemsPedidoWhenCalcularTotalVentasDeProductoThenTotalIsGreaterThanZero(){
        //given
        ItemPedido itemPedido=itemPedidoRepository.save(itemPedido());
        //when
        Integer total=itemPedidoRepository.calcularTotalVentasDeProducto(
                productoRepository.findById(1L).get());
        //then
        assertThat(total).isGreaterThan(0);
    }
}