package edu.unimagdalena.tiendaEnLinea.repository;

import edu.unimagdalena.tiendaEnLinea.entity.Cliente;
import edu.unimagdalena.tiendaEnLinea.entity.Pago;
import edu.unimagdalena.tiendaEnLinea.entity.Pedido;
import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.MetodoPago;
import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.StatusPedido;
import edu.unimagdalena.tiendaEnLinea.plantillaTestIntegracionPersistencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PagoRepositoryTest extends plantillaTestIntegracionPersistencia {
    PagoRepository pagoRepository;
    PedidoRepository pedidoRepository;
    ClienteRepository clienteRepository;

    @Autowired
    public PagoRepositoryTest(PagoRepository pagoRepository,PedidoRepository pedidoRepository,
                              ClienteRepository clienteRepository) {
        this.pagoRepository = pagoRepository;
        this.pedidoRepository=pedidoRepository;
        this.clienteRepository=clienteRepository;
    }


    @BeforeEach
    void setUp() {
        pagoRepository.deleteAll();
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

    void conjuntoPagos(){
        List<Pedido> pedidos=pedidos();
        pagoRepository.save(Pago.builder()
                .pedidoId(pedidos.get(0))
                .totalPago(35.000d)
                .fechaPago(LocalDate.now())
                .metodoPago(MetodoPago.EFECTIVO).build());
        pagoRepository.save(
                Pago.builder()
                        .pedidoId(pedidos.get(1))
                        .totalPago(35.000d)
                        .fechaPago(LocalDate.now())
                        .metodoPago(MetodoPago.PSE).build());
        pagoRepository.flush();
    }

    Pago pago(){
        Pedido pedido=pedidos().get(0);
        return Pago.builder()
                .pedidoId(pedido)
                .totalPago(35.000d)
                .fechaPago(LocalDate.now())
                .metodoPago(MetodoPago.EFECTIVO).build();
    }

    @Test
    void givenAnPagoWhenSaveThenIdPagoIsNotNull(){
        //given
        Pago pago=pago();
        //when
        Pago guardado=pagoRepository.save(pago);
        //then
        assertThat(guardado.getId()).isNotNull();
    }

    @Test
    void givenAnPagoWhenDeleteByIdThenPagoIsEmpty(){
        //given
        Pago pago=pagoRepository.save(pago());
        //when
        pagoRepository.deleteById(pago.getId());
        //then
        Optional<Pago> encontrado=pagoRepository.findById(pago.getId());
        assertThat(encontrado).isEmpty();
    }

    @Test
    void givenPagoExistingWhenSaveThenPagoExistingUpdate(){
        //given
        Pago pago=pagoRepository.save(pago());
        //when
        pago.setTotalPago(5.000d);
        Pago actualizado=pagoRepository.save(pago);
        //then
        assertThat(actualizado.getTotalPago()).isEqualTo(5.000d);
        assertThat(actualizado.getMetodoPago()).isEqualTo(pago.getMetodoPago());
    }

    @Test
    void givenIdPagoWhenFindByIdThenPagoIsNotNull(){
        //given
        Pago pago=pagoRepository.save(pago());
        //when
        Optional<Pago> encontrado=pagoRepository.findById(pago.getId());
        //then
        assertThat(encontrado).isNotNull();
    }

    @Test
    void givenASetPagoWhenFindByFechaPagoThenHasSizeGreaterThanZero(){
        //given
        conjuntoPagos();
        //when
        List<Pago> pagos=pagoRepository.findByFechaPagoBetween(
                LocalDate.of(2023,3,12),
                LocalDate.of(2024,5,20)
        );
        //then
        assertThat(pagos).hasSizeGreaterThan(0);
        assertThat(pagos).first().hasFieldOrPropertyWithValue("metodoPago",MetodoPago.EFECTIVO);
    }

    @Test
    void givenASetPagoWhenFindByPedidoAndMetodoPagoThenHasSizeOne(){
        //given
        conjuntoPagos();
        //when
        List<Pago> pagos=pagoRepository.findByPedidoIdAndMetodoPagoIs(
                pedidoRepository.findById(2L).get(),MetodoPago.PSE
        );
        //then
        assertThat(pagos).hasSize(1);
        assertThat(pagos).first().hasFieldOrPropertyWithValue("metodoPago",MetodoPago.PSE);
    }
}