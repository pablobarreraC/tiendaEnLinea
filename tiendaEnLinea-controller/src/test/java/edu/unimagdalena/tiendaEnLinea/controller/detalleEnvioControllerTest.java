package edu.unimagdalena.tiendaEnLinea.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unimagdalena.tiendaEnLinea.dto.detalleEnvio.DetalleEnvioDto;
import edu.unimagdalena.tiendaEnLinea.dto.detalleEnvio.DetalleEnvioToSaveDto;
import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pago.PagoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.excepción.DetalleEnvioNotFoundException;
import edu.unimagdalena.tiendaEnLinea.service.DetalleEnvioService;
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

@WebMvcTest(controllers = detalleEnvioController.class)
@ExtendWith(MockitoExtension.class)
class detalleEnvioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DetalleEnvioService detalleEnvioService;

    @Autowired
    private ObjectMapper objectMapper;

    private final DetalleEnvioDto detalleEnvioDto = new DetalleEnvioDto(1L, "Calle Principal", "Transportadora X", "123456789");
    private final PagoDto pagoDto=new PagoDto(1L,25.000d, LocalDate.now().toString(),"PSE");
    private final DetalleEnvioDto detalleEnvioDto1=new DetalleEnvioDto(2L,"Calle 20","Servientrega","1004746");
    private  final PedidoDto pedidoDtoC=new PedidoDto(1L,new ArrayList<ItemPedidoDto>(),pagoDto, LocalDateTime.now().toString(),detalleEnvioDto1,"ENVIADO");
    private final DetalleEnvioToSaveDto detalleEnvioToSaveDtoC=new DetalleEnvioToSaveDto(2L,pedidoDtoC,"Calle 30 #40-20","Envia","102938373");
    @Test
    void obtenerDetallesEnvio() throws Exception {
        List<DetalleEnvioDto> detallesEnvioDtos = Collections.singletonList(detalleEnvioDto);
        when(detalleEnvioService.buscarTodosDetallesEnvio()).thenReturn(detallesEnvioDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/detallesEnvio")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void guardarDetalleEnvio() throws Exception {
        DetalleEnvioToSaveDto detalleEnvioToSaveDto = detalleEnvioToSaveDtoC;
        when(detalleEnvioService.guardarDetalleEnvio(detalleEnvioToSaveDto)).thenReturn(detalleEnvioDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/detallesEnvio")
                        .content(objectMapper.writeValueAsString(detalleEnvioToSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.direccion").exists());
    }

    @Test
    void actualizarDetalleEnvioPorId() throws Exception {
        Long id = 1L;
        DetalleEnvioToSaveDto detalleEnvioToSaveDto = detalleEnvioToSaveDtoC;
        when(detalleEnvioService.actualizarDetalleEnvioPorId(id, detalleEnvioToSaveDto)).thenReturn(detalleEnvioDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/detallesEnvio/{id}", id)
                        .content(objectMapper.writeValueAsString(detalleEnvioToSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.direccion").exists());
    }

    @Test
    void obtenerDetalleEnvioPorIdExistente() throws Exception {
        Long id = 1L;
        when(detalleEnvioService.buscarDetalleEnvioPorId(id)).thenReturn(detalleEnvioDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/detallesEnvio/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.direccion").exists());
    }

    @Test
    void obtenerDetalleEnvioPorIdNoExistente() throws Exception {
        Long id = 1L;
        when(detalleEnvioService.buscarDetalleEnvioPorId(id)).thenThrow(new DetalleEnvioNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/detallesEnvio/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerDetallesEnvioPorPedido() throws Exception {
        PedidoDto pedidoDto =  pedidoDtoC;
        List<DetalleEnvioDto> detallesEnvioDtos = Collections.singletonList(detalleEnvioDto);
        when(detalleEnvioService.buscarLosDetallesDelEnvíoPorPedidoId(pedidoDto)).thenReturn(detallesEnvioDtos);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/detallesEnvio")
                        .content(objectMapper.writeValueAsString(pedidoDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void obtenerDetallesEnvioPorTransportadora() throws Exception {
        String transportadora = "Transportadora X";
        List<DetalleEnvioDto> detallesEnvioDtos = Collections.singletonList(detalleEnvioDto);
        when(detalleEnvioService.buscarLosDetallesDelEnvíoParaUnaTrasportadora(transportadora)).thenReturn(detallesEnvioDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/detallesEnvio/{transportadora}", transportadora)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void obtenerDetallesEnvioPorStatus() throws Exception {
        String status = "ENVIADO";
        List<DetalleEnvioDto> detallesEnvioDtos = Collections.singletonList(detalleEnvioDto);
        when(detalleEnvioService.buscarLosDetallesDelEnvioPorStatus(status)).thenReturn(detallesEnvioDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/detallesEnvio/{status}", status)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }
}