package edu.unimagdalena.tiendaEnLinea.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteToSaveDto;
import edu.unimagdalena.tiendaEnLinea.dto.detalleEnvio.DetalleEnvioDto;
import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.dto.pago.PagoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoDto;
import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.entity.Pago;
import edu.unimagdalena.tiendaEnLinea.excepción.ItemPedidoNotFoundException;
import edu.unimagdalena.tiendaEnLinea.service.ItemPedidoService;
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

@WebMvcTest(controllers = itemPedidoController.class)
@ExtendWith(MockitoExtension.class)
class itemPedidoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemPedidoService itemPedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    private final ProductoDto productoDtoC=new ProductoDto(1L,"Arroz",2.500d,10);
    private final ItemPedidoDto itemPedidoDto = new ItemPedidoDto(1L, productoDtoC, 2, 10.0);
    private final ClienteToSaveDto clienteToSaveDtoC=new ClienteToSaveDto(1L,"Pablo","pablo@example.com","Carrera 15");
    private final ProductoToSaveDto productoToSaveDtoC=new ProductoToSaveDto(1L,"Huevos",14.000d,10);
    private final PedidoToSaveDto pedidoToSaveDtoC=new PedidoToSaveDto(1L,clienteToSaveDtoC, LocalDateTime.now().toString(),"ENVIADO");
    private final PagoDto pagoDtoC=new PagoDto(1L,20.000d, LocalDate.now().toString(),"PSE");
    private final DetalleEnvioDto detalleEnvioDtoC=new DetalleEnvioDto(1L,"Calle 43-12","Envia","198271");
    private final PedidoDto pedidoDtoC=new PedidoDto(2L,new ArrayList<ItemPedidoDto>(),pagoDtoC,pedidoToSaveDtoC.fechaPedido(),detalleEnvioDtoC,"ENVIADO");
    private final ItemPedidoToSaveDto itemPedidoToSaveDtoC=new ItemPedidoToSaveDto(2L,pedidoToSaveDtoC,productoToSaveDtoC,20,20.000d);
    @Test
    void obtenerItemsPedido() throws Exception {
        List<ItemPedidoDto> itemsPedidoDtos = Collections.singletonList(itemPedidoDto);
        when(itemPedidoService.buscarTodosItemPedidos()).thenReturn(itemsPedidoDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/itemsPedido")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void guardarItemPedido() throws Exception {
        ItemPedidoToSaveDto itemPedidoToSaveDto = itemPedidoToSaveDtoC;
        when(itemPedidoService.guardarItemPedido(itemPedidoToSaveDto)).thenReturn(itemPedidoDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/itemsPedido")
                        .content(objectMapper.writeValueAsString(itemPedidoToSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void actualizarItemPedidoPorId() throws Exception {
        Long id = 1L;
        ItemPedidoToSaveDto itemPedidoToSaveDto = itemPedidoToSaveDtoC;
        when(itemPedidoService.actualizarItemPedidoPorId(id, itemPedidoToSaveDto)).thenReturn(itemPedidoDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/itemsPedido/{id}", id)
                        .content(objectMapper.writeValueAsString(itemPedidoToSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void obtenerItemPedidoPorIdExistente() throws Exception {
        Long id = 1L;
        when(itemPedidoService.buscarItemPedidoPorId(id)).thenReturn(itemPedidoDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/itemsPedido/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void obtenerItemPedidoPorIdNoExistente() throws Exception {
        Long id = 1L;
        when(itemPedidoService.buscarItemPedidoPorId(id)).thenThrow(new ItemPedidoNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/itemsPedido/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarPorId() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/itemsPedido/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarItemsPorPedido() throws Exception {
        PedidoDto pedidoDto = pedidoDtoC;
        List<ItemPedidoDto> itemsPedidoDtos = Collections.singletonList(itemPedidoDto);
        when(itemPedidoService.buscarItemsDelPedidoPorPedidoId(pedidoDto)).thenReturn(itemsPedidoDtos);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/itemsPedido")
                        .content(objectMapper.writeValueAsString(pedidoDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void buscarItemsPorProducto() throws Exception {
        ProductoDto productoDto = productoDtoC;
        List<ItemPedidoDto> itemsPedidoDtos = Collections.singletonList(itemPedidoDto);
        when(itemPedidoService.buscarItemsDelPedidoParaUnProductoEspecífico(productoDto)).thenReturn(itemsPedidoDtos);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/itemsPedido")
                        .content(objectMapper.writeValueAsString(productoDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void obtenerVentasPorProducto() throws Exception {
        ProductoDto productoDto = productoDtoC;
        Integer total = 100;
        when(itemPedidoService.calcularLaSumaDelTotalDeVentasParaUnProducto(productoDto)).thenReturn(total);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/itemsPedido")
                        .content(objectMapper.writeValueAsString(productoDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(total));
    }
}