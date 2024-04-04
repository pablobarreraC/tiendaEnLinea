package edu.unimagdalena.tiendaEnLinea.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.unimagdalena.tiendaEnLinea.dto.detalleEnvio.DetalleEnvioDto;
import edu.unimagdalena.tiendaEnLinea.dto.detalleEnvio.DetalleEnvioMapper;
import edu.unimagdalena.tiendaEnLinea.dto.detalleEnvio.DetalleEnvioToSaveDto;
import edu.unimagdalena.tiendaEnLinea.entity.DetalleEnvio;
import edu.unimagdalena.tiendaEnLinea.entity.Pedido;
import edu.unimagdalena.tiendaEnLinea.repository.DetalleEnvioRepository;
import edu.unimagdalena.tiendaEnLinea.service.DetalleEnvioServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DetalleEnvioServiceImplTest {
    @Mock
    private DetalleEnvioRepository detalleEnvioRepository;

    @Mock
    private DetalleEnvioMapper detalleEnvioMapper;

    @InjectMocks
    private DetalleEnvioServiceImpl detalleEnvioService;

    DetalleEnvio detalleEnvio, detalleEnvio2;
    DetalleEnvioDto detalleEnvioDto;
    Pedido pedido;

    @BeforeEach
    void setUp() {
        //detalleEnvio = new DetalleEnvio();
        pedido.setDetalleEnvio(detalleEnvio);
        pedido.setId(1l);
        detalleEnvio.setId(1L);
        detalleEnvio.setDireccion("Calle 2");
        detalleEnvio.setNumeroGuia("345");
        detalleEnvio.setTransportadora("Envia");
        detalleEnvio.setPedidoId(pedido);


        detalleEnvio2.setId(2L);
        detalleEnvio2.setDireccion("Calle 3");
        detalleEnvio2.setNumeroGuia("346");
        detalleEnvio2.setTransportadora("Coordinadora");

        detalleEnvioDto = DetalleEnvioMapper.instancia.detalleEnvioEntityToDto(detalleEnvio);
    }

    @Test
    void testActualizarDetalleEnvioPorId() {
        Long idDetalle = 1l;

        DetalleEnvioToSaveDto detalleEnvioToSaveDto = new DetalleEnvioToSaveDto(
                1L,
                null,
                "Calle 29",
                "Coordinadora",
                "2345"
        );

        when(detalleEnvioRepository.findById(idDetalle)).thenReturn(Optional.of(detalleEnvio));
        when(detalleEnvioRepository.save(any())).thenReturn(detalleEnvio);

        when(detalleEnvioMapper.detalleEnvioEntityToDto(any())).thenReturn(detalleEnvioDto);

        DetalleEnvioDto detalleDtoG = detalleEnvioService.actualizarDetalleEnvioPorId(idDetalle, detalleEnvioToSaveDto);

        assertThat(detalleDtoG).isNotNull();

    }

    @Test
    void testBuscarDetalleEnvioPorId() {
       Long idDetalle=1L;
        when(detalleEnvioRepository.findById(idDetalle)).thenReturn(Optional.of(detalleEnvio));

        when(detalleEnvioMapper.detalleEnvioEntityToDto(any())).thenReturn(detalleEnvioDto);

        DetalleEnvioDto detalleDtoG = detalleEnvioService.buscarDetalleEnvioPorId(idDetalle);

        assertThat(detalleDtoG).isNotNull();

    }

    @Test
    void testBuscarLosDetallesDelEnvioPorStatus() {
        String statusPedido = "ENVIADO";
        List<DetalleEnvio> detalleEnvioList = List.of(detalleEnvio);

        when(detalleEnvioRepository.findByStatus(statusPedido)).thenReturn(detalleEnvioList);

        List<DetalleEnvioDto> detalleEnvioDtos = detalleEnvioService.buscarLosDetallesDelEnvioPorStatus(statusPedido);
        assertThat(detalleEnvioDtos).isNotEmpty();
        assertThat(detalleEnvioDtos).hasSize(1);

    }

    @Test
    void testBuscarLosDetallesDelEnvíoParaUnaTrasportadora() {
        String transportadora = "Envia";

        List<DetalleEnvio> detalleEnvioList = List.of(detalleEnvio,detalleEnvio2);

        when(detalleEnvioRepository.findByTransportadora(transportadora)).thenReturn(detalleEnvioList);

        when(detalleEnvioMapper.detalleEnvioEntityToDto(any())).thenReturn(detalleEnvioDto);

        List<DetalleEnvioDto> detalleEnvioDtos = detalleEnvioService.buscarLosDetallesDelEnvíoParaUnaTrasportadora(transportadora);

        assertThat(detalleEnvioDtos).isNotEmpty();
        assertThat(detalleEnvioDtos).hasSize(1);

    }

    @Test
    void testBuscarLosDetallesDelEnvíoPorPedidoId() {
    }
    

    @Test
    void testBuscarTodosDetallesEnvio() {
        List<DetalleEnvio> detalleEnvioList = List.of(detalleEnvio, detalleEnvio2);

        when(detalleEnvioRepository.findAll()).thenReturn(detalleEnvioList);

        List<DetalleEnvioDto> detalleEnvioDtos = detalleEnvioService.buscarTodosDetallesEnvio();

        assertThat(detalleEnvioDtos).isNotEmpty();
        assertThat(detalleEnvioDtos).hasSize(2);
    }

    @Test
    void testGuardarDetalleEnvio() {
         when(detalleEnvioRepository.save(any())).thenReturn(detalleEnvio);

        DetalleEnvioToSaveDto detalleEnvioToSaveDto = new DetalleEnvioToSaveDto(3l,
         null,
          "Calle 3",
           "Envia",
           "437");
           when(detalleEnvioMapper.detalleEnvioEntityToDto(any())).thenReturn(detalleEnvioDto);

        DetalleEnvioDto detalleDtoG = detalleEnvioService.guardarDetalleEnvio(detalleEnvioToSaveDto);

        assertThat(detalleDtoG).isNotNull();
    }

    @Test
    void testRemoverDetalleEnvio() {
        Long idDetalle = 1l;

        when(detalleEnvioRepository.findById(idDetalle)).thenReturn(Optional.of(detalleEnvio));

        detalleEnvioService.removerDetalleEnvio(idDetalle);

        verify(detalleEnvioRepository, times(1)).delete(detalleEnvio);
    }
}
