package edu.unimagdalena.tiendaEnLinea.repository;

import edu.unimagdalena.tiendaEnLinea.entity.Cliente;
import edu.unimagdalena.tiendaEnLinea.entity.DetalleEnvio;
import edu.unimagdalena.tiendaEnLinea.entity.Pedido;
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

class DetalleEnvioRepositoryTest extends plantillaTestIntegracionPersistencia {
    DetalleEnvioRepository detalleEnvioRepository;
    PedidoRepository pedidoRepository;
    ClienteRepository clienteRepository;

    @Autowired
    public DetalleEnvioRepositoryTest(DetalleEnvioRepository detalleEnvioRepository,
                                      PedidoRepository pedidoRepository,
                                      ClienteRepository clienteRepository) {
        this.detalleEnvioRepository = detalleEnvioRepository;
        this.pedidoRepository=pedidoRepository;
        this.clienteRepository=clienteRepository;
    }

    @BeforeEach
    void setUp() {
        detalleEnvioRepository.deleteAll();
        pedidoRepository.deleteAll();
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

    void conjuntoDetalleEnvio(){
        List<Pedido> pedidos=pedidos();
        detalleEnvioRepository.save(
                DetalleEnvio.builder()
                        .pedidoId(pedidos.get(0))
                        .direccion("Carrera 5ta #5-12")
                        .transportadora("Servientrega")
                        .numeroGuia("10837632")
                        .build());
        detalleEnvioRepository.save(
                DetalleEnvio.builder()
                        .pedidoId(pedidos.get(1))
                        .direccion("Maria Eugenia 39-12")
                        .transportadora("Envia")
                        .numeroGuia("108725432")
                        .build()
        );
        detalleEnvioRepository.flush();
    }

    DetalleEnvio detalleEnvio(){
        Pedido pedido=pedidos().get(0);
        return DetalleEnvio.builder()
                .pedidoId(pedido)
                .direccion("Carrera 17A #30-30")
                .transportadora("Servientrega")
                .numeroGuia("2209383")
                .build();
    }

    @Test
    void givenAnDetalleEnvioWhenSaveThenIdDetalleEnvioIsNotNull(){
        //given
        DetalleEnvio detalleEnvio=detalleEnvio();
        //when
        DetalleEnvio guardado=detalleEnvioRepository.save(detalleEnvio);
        //then
        assertThat(guardado.getId()).isNotNull();
    }

    @Test
    void givenIdDetalleEnvioWhenFindByIdThenDetalleEnvioIsNotNull(){
        //given
        DetalleEnvio detalleEnvio=detalleEnvioRepository.save(detalleEnvio());
        //when
        Optional<DetalleEnvio> encontrado=detalleEnvioRepository.findById(detalleEnvio.getId());
        //then
        assertThat(encontrado).isNotNull();
        assertThat(encontrado.get().getTransportadora()).isEqualTo("Servientrega");
    }

    @Test
    void givenAnIdDetalleEnvioWhenDeteleByIdThenDetalleEnvioIsEmpty(){
        //given
        DetalleEnvio detalleEnvio=detalleEnvioRepository.save(detalleEnvio());
        //when
        detalleEnvioRepository.deleteById(detalleEnvio.getId());
        //then
        Optional<DetalleEnvio> encontrado=detalleEnvioRepository.findById(detalleEnvio.getId());
        assertThat(encontrado).isEmpty();
    }

    @Test
    void givenDetalleEnvioExistingWhenSaveThenDetalleEnvioExistingUpdate(){
        //given
        DetalleEnvio detalleEnvio=detalleEnvioRepository.save(detalleEnvio());
        //when
        detalleEnvio.setDireccion("Las Americas #32-40");
        DetalleEnvio actualizado=detalleEnvioRepository.save(detalleEnvio);
        //then
        assertThat(actualizado.getId()).isEqualTo(detalleEnvio.getId());
        assertThat(actualizado.getDireccion()).isEqualTo("Las Americas #32-40");
    }

    @Test
    void givenASetDetalleEnvioWhenFindByPedidoThenHasSizeIsGreaterThanZero(){
        //given
        conjuntoDetalleEnvio();
        //when
        List<DetalleEnvio> detallesEnvio=detalleEnvioRepository.findByPedidoId(
                detalleEnvioRepository.findById(1L).get().getPedidoId());
        //then
        assertThat(detallesEnvio).hasSizeGreaterThan(0);
        assertThat(detallesEnvio.get(0).getTransportadora()).isEqualTo("Servientrega");
    }

    @Test
    void givenASetDetalleEnvioWhenFindByTransportadoraThenHasSizeIsOne(){
        //given
        conjuntoDetalleEnvio();
        //when
        List<DetalleEnvio> detalleEnvios=detalleEnvioRepository.findByTransportadora("Envia");
        //then
        assertThat(detalleEnvios).hasSize(1);
        assertThat(detalleEnvios).first().hasFieldOrPropertyWithValue("direccion","Maria Eugenia 39-12");
    }

    @Test
    void givenAnStatusWhenFindByStatusThenHasSizeIsOne(){
        //given
        conjuntoDetalleEnvio();
        //when
        List<DetalleEnvio> detalleEnvios=detalleEnvioRepository.findByStatus(StatusPedido.ENVIADO);
        //then
        assertThat(detalleEnvios).hasSize(1);
        assertThat(detalleEnvios).first().hasFieldOrPropertyWithValue("transportadora","Servientrega");
    }
}