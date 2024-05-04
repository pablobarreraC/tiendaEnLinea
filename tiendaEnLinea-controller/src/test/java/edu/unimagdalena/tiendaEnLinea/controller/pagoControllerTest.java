package edu.unimagdalena.tiendaEnLinea.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteToSaveDto;
import edu.unimagdalena.tiendaEnLinea.dto.detalleEnvio.DetalleEnvioDto;
import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pago.PagoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pago.PagoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.excepción.PagoNotFoundException;
import edu.unimagdalena.tiendaEnLinea.service.PagoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = pagoController.class)
@ExtendWith(MockitoExtension.class)
class pagoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    @Autowired
    private ObjectMapper objectMapper;

    private final ClienteToSaveDto clienteToSaveDtoC=new ClienteToSaveDto(1L,"Jose","jose@example.com","Carrera 30");
    private final PedidoToSaveDto pedidoToSaveDtoC=new PedidoToSaveDto(2L,clienteToSaveDtoC,LocalDateTime.now().toString(),"ENVIADO");
    private final PagoToSaveDto pagoToSaveDtoC=new PagoToSaveDto(2L,pedidoToSaveDtoC,10.000d,LocalDate.now().toString(),"EFECTIVO");
    private final PagoDto pagoDtoC = new PagoDto(1L,20.500d,LocalDate.now().toString(),"PSE");
    private final DetalleEnvioDto detalleEnvioDtoC=new DetalleEnvioDto(1L,"Calle 43-12","Envia","198271");
    private final PedidoDto pedidoDtoC=new PedidoDto(2L, new ArrayList<ItemPedidoDto>(),pagoDtoC,LocalDateTime.now().toString(),detalleEnvioDtoC,"PENDIENTE");
    @Test
    void obtenerPagos() throws Exception {
        List<PagoDto> pagoDtos = Collections.singletonList(pagoDtoC);
        when(pagoService.buscarTodosPagos()).thenReturn(pagoDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/pagos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void guardarPago() throws Exception {
        PagoToSaveDto pagoToSaveDto = pagoToSaveDtoC;
        when(pagoService.guardarPago(pagoToSaveDto)).thenReturn(pagoDtoC);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/pagos")
                        .content(objectMapper.writeValueAsString(pagoToSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void eliminarPagoPorId() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/pagos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void actualizarPagoPorId() throws Exception {
        Long id = 1L;
        PagoToSaveDto pagoToSaveDto = pagoToSaveDtoC;
        when(pagoService.actualizarPagoPorId(id, pagoToSaveDto)).thenReturn(pagoDtoC);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/pagos/{id}", id)
                        .content(objectMapper.writeValueAsString(pagoToSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void obtenerPagoPorIdExistente() throws Exception {
        Long id = 1L;
        when(pagoService.buscarPagoPorId(id)).thenReturn(pagoDtoC);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/pagos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void obtenerPagoPorIdNoExistente() throws Exception {
        Long id = 1L;
        when(pagoService.buscarPagoPorId(id)).thenThrow(new PagoNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/pagos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerPagosEntreFechas() throws Exception {
        LocalDateTime fechaInicial = LocalDateTime.now().minusDays(1);
        LocalDateTime fechaFinal = LocalDateTime.now();
        List<PagoDto> pagoDtos = Collections.singletonList(pagoDtoC);
        when(pagoService.recuperarPagosDentroDeUnRangoDeFecha(fechaInicial, fechaFinal)).thenReturn(pagoDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/pagos/{fechaInicial}/{fechaFinal}", fechaInicial, fechaFinal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void obtenerPagosPorPedidoYMetodoDePago() throws Exception {
        PedidoDto pedidoDto = pedidoDtoC;
        String metodoPago = "Tarjeta de crédito";
        List<PagoDto> pagoDtos = Collections.singletonList(pagoDtoC);
        when(pagoService.recuperarPagosPorUnIdentificadorDeUnaOrdenYMétodoDePago(pedidoDto, metodoPago)).thenReturn(pagoDtos);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/pagos/{metodoPago}", metodoPago)
                        .content(objectMapper.writeValueAsString(pedidoDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }
}