package edu.unimagdalena.tiendaEnLinea.controller;

import org.junit.jupiter.api.BeforeEach;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteDto;
import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteToSaveDto;
import edu.unimagdalena.tiendaEnLinea.excepción.ClienteNotFoundException;
import edu.unimagdalena.tiendaEnLinea.service.ClienteService;
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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = clienteController.class)
@ExtendWith(MockitoExtension.class)
class clienteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private final ClienteDto clienteDto = new ClienteDto(1L, "Juan", "juan@example.com", "Calle Principal", Collections.emptyList());
    private final ClienteToSaveDto clienteToSaveDtoC=new ClienteToSaveDto(2L,"Jose","jose@gmail.com","Calle 8 #32-90");

    @Test
    void obtenerClientes() throws Exception {
        List<ClienteDto> clienteDtos = Collections.singletonList(clienteDto);
        when(clienteService.encontrarTodosClientes()).thenReturn(clienteDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void guardarCliente() throws Exception {
        ClienteToSaveDto clienteToSaveDto = clienteToSaveDtoC;
        when(clienteService.guardarCliente(clienteToSaveDto)).thenReturn(clienteDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/clientes")
                        .content(objectMapper.writeValueAsString(clienteToSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").exists());
    }

    @Test
    void actualizarClientePorId() throws Exception {
        Long id = 1L;
        ClienteToSaveDto clienteToSaveDto = clienteToSaveDtoC;
        when(clienteService.actualizarCliente(id, clienteToSaveDto)).thenReturn(clienteDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/clientes/{id}", id)
                        .content(objectMapper.writeValueAsString(clienteToSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").exists());
    }

    @Test
    void obtenerClientePorIdExistente() throws Exception {
        Long id = 1L;
        when(clienteService.buscarClientePorId(id)).thenReturn(clienteDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clientes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").exists());
    }

    @Test
    void obtenerClientePorIdNoExistente() throws Exception {
        Long id = 1L;
        when(clienteService.buscarClientePorId(id)).thenThrow(new ClienteNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clientes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarClientePorId() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/clientes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerClientePorEmail() throws Exception {
        String email = "test@example.com";
        when(clienteService.buscarClientePorEmail(email)).thenReturn(clienteDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clientes/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").exists());
    }

    @Test
    void obtenerClientesPorDireccion() throws Exception {
        String direccion = "Calle Principal";
        List<ClienteDto> clienteDtos = Collections.singletonList(clienteDto);
        when(clienteService.buscarClientesPorDireccion(direccion)).thenReturn(clienteDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clientes/{direccion}", direccion)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void obtenerClientesPorPrimerNombre() throws Exception {
        String nombre = "Juan";
        List<ClienteDto> clienteDtos = Collections.singletonList(clienteDto);
        when(clienteService.buscarClientesPorPrimerNombre(nombre)).thenReturn(clienteDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clientes/{nombre}", nombre)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }
}