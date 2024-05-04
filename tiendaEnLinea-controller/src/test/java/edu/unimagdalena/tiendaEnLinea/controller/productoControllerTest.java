package edu.unimagdalena.tiendaEnLinea.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoDto;
import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.excepción.ProductoNotFoundException;
import edu.unimagdalena.tiendaEnLinea.service.ProductoService;
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

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = productoController.class)
@ExtendWith(MockitoExtension.class)
class productoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    private final ProductoDto productoDto = new ProductoDto(1L, "Producto1", 10.0, 100);
    private final ProductoToSaveDto productoToSaveDtoC=new ProductoToSaveDto(2L,"Carne",16.000d,20);

    @Test
    void obtenerProductos() throws Exception {
        List<ProductoDto> productoDtos = Collections.singletonList(productoDto);
        when(productoService.buscarTodosProductos()).thenReturn(productoDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void guardarProducto() throws Exception {
        ProductoToSaveDto productoToSaveDto = productoToSaveDtoC;
        when(productoService.guardarProducto(productoToSaveDto)).thenReturn(productoDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/productos")
                        .content(objectMapper.writeValueAsString(productoToSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void actualizarProducto() throws Exception {
        Long id = 1L;
        ProductoToSaveDto productoToSaveDto = productoToSaveDtoC;
        when(productoService.actualizarProductoPorId(id, productoToSaveDto)).thenReturn(productoDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/productos/{id}", id)
                        .content(objectMapper.writeValueAsString(productoToSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void buscarProductoPorIdExistente() throws Exception {
        Long id = 1L;
        when(productoService.buscarProductoPorId(id)).thenReturn(productoDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/productos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void buscarProductoPorIdNoExistente() throws Exception {
        Long id = 1L;
        when(productoService.buscarProductoPorId(id)).thenThrow(new ProductoNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/productos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarProductoPorNombre() throws Exception {
        String nombre = "Producto1";
        List<ProductoDto> productoDtos = Collections.singletonList(productoDto);
        when(productoService.buscarProductoPorNombre(nombre)).thenReturn(productoDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/productos/{nombre}", nombre)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void productosEnStock() throws Exception {
        List<ProductoDto> productoDtos = Collections.singletonList(productoDto);
        when(productoService.buscarLosProductosQueEstanEnStock()).thenReturn(productoDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void buscarProductosPorPrecioYStock() throws Exception {
        Double price = 10.0;
        Integer stock = 100;
        List<ProductoDto> productoDtos = Collections.singletonList(productoDto);
        when(productoService.buscarLosProductosQueNoSuperenUnPrecioYUnStockDeterminado(price, stock)).thenReturn(productoDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/productos/{price}/{stock}", price, stock)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }
}