package edu.unimagdalena.tiendaEnLinea.serviceTest;

import static org.junit.Assert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoMapper;
import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.entity.ItemPedido;
import edu.unimagdalena.tiendaEnLinea.repository.ItemPedidoRepository;
import edu.unimagdalena.tiendaEnLinea.service.ItemPedidoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ItemPedidoServiceImplTest {
    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @Mock
    private ItemPedidoMapper itemPedidoMapper;

    @InjectMocks
    private ItemPedidoServiceImpl itemPedidoService;

    ItemPedido itemPedido, itemPedido2;
    ItemPedidoDto itemPedidoDto;

    @BeforeEach
    void setUp() {
        itemPedido = new ItemPedido();
        itemPedido.setId(1L);
        itemPedido.setCantidad(5);
        itemPedido.setPrecioUnitario( 100.00);

        itemPedido2.setCantidad(10);
        itemPedido2.setId(2l);
        itemPedido2.setPrecioUnitario(200.00);


        itemPedidoDto = ItemPedidoMapper.instancia.itemPedidoEntityToDto(itemPedido);
    }


    @Test
    void testActualizarItemPedidoPorId() {

    }

    @Test
    void testBuscarItemPedidoPorId() {
        Long idItem = 1l;

        when(itemPedidoRepository.findById(idItem)).thenReturn(itemPedido);

        when(itemPedidoMapper.toDto(any())).thenReturn(itemPedidoDto);

        ItemPedidoDto itemDtoG = itemPedidoService.buscarItemPedidoPorId(idItem);

        assertThat(itemDtoG).isNotNull();

    }

    @Test
    void testBuscarItemsDelPedidoParaUnProductoEspecífico() {

    }

    @Test
    void testBuscarItemsDelPedidoPorPedidoId() {

    }

    @Test
    void testBuscarTodosItemPedidos() {

    }

    @Test
    void testCalcularLaSumaDelTotalDeVentasParaUnProducto() {

    }

    @Test
    void testGuardarItemPedido() {
        when(itemPedidoRepository.save(any())).thenReturn(itemPedido);

        ItemPedidoToSaveDto itemPedidoToSave = new ItemPedidoToSaveDto(1L,
        null,
        null,
         10,
          100.00);

        when(itemPedidoMapper.itemPedidoEntityToDto(any())).thenReturn(itemPedidoDto);

        ItemPedidoDto savedItemPedido = itemPedidoService.guardarItemPedido(itemPedidoToSave);

        assertThat(savedItemPedido).isNotNull();

    }

    @Test
    void testRemoverItemPedido() {

    }
}
