package edu.unimagdalena.tiendaEnLinea.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.excepción.PedidoNotFoundException;
import edu.unimagdalena.tiendaEnLinea.service.PedidoService;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = pedidoController.class)
@ExtendWith(MockitoExtension.class)
class pedidoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    private final PedidoDto pedidoDto = new PedidoDto(1L, Collections.emptyList(), null, LocalDateTime.now().toString(), null, "PENDIENTE");
    private final PedidoToSaveDto pedidoToSaveDtoC=new PedidoToSaveDto(2L,null,LocalDateTime.now().toString(),"PENDIENTE");
    private final ClienteDto clienteDtoC=new ClienteDto(1L,"Wilkin","wilkin@example.com","Ciudad Equidad",null);
    @Test
    void obtenerPedidos() throws Exception {
        List<PedidoDto> pedidoDtos = Collections.singletonList(pedidoDto);
        when(pedidoService.buscarTodosPedidos()).thenReturn(pedidoDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void guardarPedido() throws Exception {
        PedidoToSaveDto pedidoToSaveDto = pedidoToSaveDtoC;
        when(pedidoService.guardarPedido(pedidoToSaveDto)).thenReturn(pedidoDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/pedidos")
                        .content(objectMapper.writeValueAsString(pedidoToSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void eliminarPedidoPorId() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/pedidos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void actualizarPedido() throws Exception {
        Long id = 1L;
        PedidoToSaveDto pedidoToSaveDto = pedidoToSaveDtoC;
        when(pedidoService.actualizarPedidoPorId(id, pedidoToSaveDto)).thenReturn(pedidoDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/pedidos/{id}", id)
                        .content(objectMapper.writeValueAsString(pedidoToSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void buscarPedidoPorIdExistente() throws Exception {
        Long id = 1L;
        when(pedidoService.buscarPedidoPorId(id)).thenReturn(pedidoDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/pedidos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void buscarPedidoPorIdNoExistente() throws Exception {
        Long id = 1L;
        when(pedidoService.buscarPedidoPorId(id)).thenThrow(new PedidoNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/pedidos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerPedidosEntreFechas() throws Exception {
        LocalDateTime fechaInicial = LocalDateTime.now().minusDays(1);
        LocalDateTime fechaFinal = LocalDateTime.now();
        List<PedidoDto> pedidoDtos = Collections.singletonList(pedidoDto);
        when(pedidoService.buscarPedidosEntreDosFecha(fechaInicial, fechaFinal)).thenReturn(pedidoDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/pedidos/{fechaInicial}/{fechaFinal}", fechaInicial, fechaFinal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void obtenerPedidosPorClienteYEstado() throws Exception {
        ClienteDto clienteDto = clienteDtoC;
        String status = "PENDIENTE";
        List<PedidoDto> pedidoDtos = Collections.singletonList(pedidoDto);
        when(pedidoService.buscarPedidosPorClienteYUnEstado(clienteDto, status)).thenReturn(pedidoDtos);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/pedidos/{status}", status)
                        .content(objectMapper.writeValueAsString(clienteDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void obtenerPedidoPorCliente() throws Exception {
        ClienteDto clienteDto = clienteDtoC;
        List<PedidoDto> pedidoDtos = Collections.singletonList(pedidoDto);
        when(pedidoService.findPedidoByClienteWithItems(clienteDto)).thenReturn(pedidoDtos);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/pedidos")
                        .content(objectMapper.writeValueAsString(clienteDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }
}