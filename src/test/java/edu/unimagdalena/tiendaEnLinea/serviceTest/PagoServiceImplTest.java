package edu.unimagdalena.tiendaEnLinea.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.unimagdalena.tiendaEnLinea.dto.pago.PagoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pago.PagoMapper;
import edu.unimagdalena.tiendaEnLinea.dto.pago.PagoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.entity.Pago;
import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.MetodoPago;
import edu.unimagdalena.tiendaEnLinea.repository.PagoRepository;
import edu.unimagdalena.tiendaEnLinea.service.PagoServiceImpl;


@ExtendWith(MockitoExtension.class)
public class PagoServiceImplTest {
      @Mock
    private PagoRepository pagoRepository;

    @Mock
    private PagoMapper pagoMapper;

    @InjectMocks
    private PagoServiceImpl pagoService;

    Pago pago,pago1;
    PagoDto pagoDto;

    @BeforeEach
    void setUp() {
        pago= new Pago(1l, null, 100.00, LocalDateTime.now(),MetodoPago.NEQUI );
        pago1= new Pago(2l, null, 150.00, LocalDateTime.now(),MetodoPago.TARJETA_CREDITO );

        pagoDto = PagoMapper.instancia.pagoEntityToDto(pago);
    }

    @Test
    void testActualizarPagoPorId() {
        Long idPago = 1l;
        PagoToSaveDto pagoToSaveDto = new PagoToSaveDto(
                3lL,
                null,
                100.50,
                LocalDateTime.now(),
                "NEQUI"
        );
        when(pagoRepository.findById(idPago)).thenReturn(Optional.of(pago));
        when(pagoRepository.save(any())).thenReturn(pago);

        when(pagoMapper.pagoEntityToDto(any())).thenReturn(pagoDto);

        PagoDto pagoDtoG = pagoService.actualizarPagoPorId(idPago, pagoToSaveDto);

        assertThat(pagoDtoG).isNotNull();


    }

    @Test
    void testBuscarPagoPorId() {
        Long idPago = 1l;
        when(pagoRepository.findById(idPago)).thenReturn(Optional.of(pago));

        when(pagoMapper.pagoEntityToDto(any())).thenReturn(pagoDto);

        PagoDto pagoBuscadoDto = pagoService.buscarPagoPorId(idPago);

        assertThat(pagoBuscadoDto).isNotNull();
    }

    @Test
    void testBuscarTodosPagos() {
        List<Pago> pagos = List.of(pago, pago1);

        when(pagoRepository.findAll()).thenReturn(pagos);

        List<PagoDto> pagoList = pagoService.buscarTodosPagos();

        assertThat(pagoList).isNotEmpty();
        assertThat(pagoList).hasSize(2);


    }

    @Test
    void testGuardarPago() {
         when(pagoRepository.save(any())).thenReturn(pago);

        PagoToSaveDto pagoToSaveDto = new PagoToSaveDto(1l, null, 100.00, LocalDateTime.now(),MetodoPago.NEQUI );

        when(pagoMapper.pagoEntityToDto(any())).thenReturn(pagoDto);

        PagoDto pagoGuardado = pagoService.guardarPago(pagoToSaveDto);

        assertThat(pagoGuardado).isNotNull();

    }

    @Test
    void testRecuperarPagosDentroDeUnRangoDeFecha() {
        List<Pago> pagos = List.of(pago, pago1);

        when(pagoRepository.findByFechaPagoBetween(any(), any())).thenReturn(pagos);

        List<PagoDto> pagoDtoList = pagoService.recuperarPagosDentroDeUnRangoDeFecha(any(), any());

        assertThat(pagoDtoList).isNotEmpty();
        assertThat(pagoDtoList).hasSize(2);

    }

    @Test
    void testRecuperarPagosPorUnIdentificadorDeUnaOrdenYMétodoDePago() {
        List<Pago> pagos = List.of(pago,pago1);

        when(pagoRepository.findByPedidoIdAndMetodoPagoIs(idPedido, metodoPago)).thenReturn(pagos);

        List<PagoDto> pagoDtoList = pagoService.recuperarPagosPorUnIdentificadorDeUnaOrdenYMétodoDePago(idPedido, metodoPago);

        assertThat(pagoDtoList).isNotEmpty();
        assertThat(pagoDtoList).hasSize(1);

    }

    @Test
    void testRemoverPago() {
         Long idPago = 1l;

        when(pagoRepository.findById(idPago)).thenReturn(Optional.of(pago));

        pagoService.removerPago(idPago);

        verify(pagoRepository, times(1)).delete(pago);

    }
}
