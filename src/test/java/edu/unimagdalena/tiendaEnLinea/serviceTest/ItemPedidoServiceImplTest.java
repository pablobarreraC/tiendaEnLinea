package edu.unimagdalena.tiendaEnLinea.serviceTest;

import static org.junit.Assert.assertThat;
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

import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoMapper;
import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.entity.ItemPedido;
import edu.unimagdalena.tiendaEnLinea.entity.Producto;
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
        Long idItemPedido = 1l;

        ItemPedidoToSaveDto itemPedidoToSaveDto = new ItemPedidoToSaveDto(
                3L,
                null,
                null,
                20,
                29.00
        );

        when(itemPedidoRepository.findById(idItemPedido)).thenReturn(Optional.of(itemPedido));
        when(itemPedidoRepository.save(any())).thenReturn(itemPedido);

        when(itemPedidoMapper.itemPedidoEntityToDto(any())).thenReturn(itemPedidoDto);

        ItemPedidoDto itemPedidoDto = itemPedidoService.actualizarItemPedidoPorId(idItemPedido, itemPedidoToSaveDto);

        assertThat(itemPedidoDto).isNotNull();

    }

    @Test
    void testBuscarItemPedidoPorId() {
        Long idItemPedido = 1l;

        when(itemPedidoRepository.findById(idItemPedido)).thenReturn(Optional.of(itemPedido));

        when(itemPedidoMapper.itemPedidoEntityToDto(any())).thenReturn(itemPedidoDto);

        ItemPedidoDto itemPedidoDto = itemPedidoService.buscarItemPedidoPorId(idItemPedido);

        assertThat(itemPedidoDto).isNotNull();

    }


    @Test
    void testBuscarItemsDelPedidoParaUnProductoEspecífico() {
        String nombreProducto = "Laptop";
        List<ItemPedido> itemPedidoList = List.of(itemPedido,itemPedido2);

        when(itemPedidoRepository.findByPedidoId(nombreProducto)).thenReturn(itemPedidoList);

        List<ItemPedidoDto> itemPedidoDtoList = itemPedidoService.buscarItemsDelPedidoParaUnProductoEspecífico(nombreProducto);

        assertThat(itemPedidoDtoList).isNotEmpty();
        assertThat(itemPedidoDtoList).hasSize(1);

    }

    @Test
    void testBuscarItemsDelPedidoPorPedidoId() {
        Long idPedido=1l;

        List<ItemPedido> itemPedidoList = List.of(itemPedido,itemPedido2);

        when(itemPedidoRepository.findByPedidoId(idPedido)).thenReturn(itemPedidoList);

        List<ItemPedidoDto> itemPedidoDtoTotal = itemPedidoService.buscarItemsDelPedidoPorPedidoId(idPedido);

        assertThat(itemPedidoDtoTotal).isNotEmpty();
        assertThat(itemPedidoDtoTotal).hasSize(1);


    }

    @Test
    void testBuscarTodosItemPedidos() {
        List<ItemPedido> itemPedidoList = List.of(itemPedido, itemPedido2);

        when(itemPedidoRepository.findAll()).thenReturn(itemPedidoList);

        List<ItemPedidoDto> itemPedidoDtoTotal = itemPedidoService.buscarTodosItemPedidos();

        assertThat(itemPedidoDtoTotal).isNotEmpty();
        assertThat(itemPedidoDtoTotal).hasSize(2);


    }

    @Test
    void testCalcularLaSumaDelTotalDeVentasParaUnProducto() {
        Producto producto = new Producto(null, null, null, null, null)

        when(itemPedidoRepository.calcularTotalVentasDeProducto(producto)).thenReturn(itemPedido.getCantidad());
        Double total = itemPedidoService.calcularLaSumaDelTotalDeVentasParaUnProducto(producto);
        assertThat(total).isEqualTo(20000.0);

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
          Long idItemPedido = 1l;

        when(itemPedidoRepository.findById(idItemPedido)).thenReturn(Optional.of(itemPedido));

        itemPedidoService.removerItemPedido(idItemPedido);

        verify(itemPedidoRepository, times(1)).delete(itemPedido);

    }
}
