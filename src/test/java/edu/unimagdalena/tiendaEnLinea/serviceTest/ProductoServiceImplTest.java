package edu.unimagdalena.tiendaEnLinea.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
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

import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoDto;
import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoMapper;
import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.entity.Producto;
import edu.unimagdalena.tiendaEnLinea.repository.ProductoRepository;
import edu.unimagdalena.tiendaEnLinea.service.ProductoServiceImpl;


@ExtendWith(MockitoExtension.class)
public class ProductoServiceImplTest {
     @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ProductoMapper productoMapper;

    @InjectMocks
    private ProductoServiceImpl productoService;

    Producto producto, producto2;
    ProductoDto productoDto;

    @BeforeEach
    void setUp() {
        producto = Producto.builder()
                .nombre("Laptop")
                .price(2000.0)
                .stock(20)
                .build();

        producto2 = Producto.builder()
                .nombre("Mesa")
                .price(300.0)
                .stock(15)
                .build();

        productoDto = ProductoMapper.instancia.productoEntityToDto(producto);

    }
    @Test
    void testActualizarProductoPorId() {


    }

    @Test
    void testBuscarLosProductosQueEstanEnStock() {

    }

    @Test
    void testBuscarLosProductosQueNoSuperenUnPrecioYUnStockDeterminado() {

    }

    @Test
    void testBuscarProductoPorId() {

    }

    @Test
    void testBuscarProductoPorNombre() {

    }

    @Test
    void testBuscarTodosProductos() {
        List<Producto> productos = List.of(producto, producto2);

        when(productoRepository.findAll()).thenReturn(productos);

        List<ProductoDto> productosDto = productoService.buscarTodosProductos();

        assertThat(productosDto).isNotEmpty();

        assertThat(productosDto).hasSize(2);

    }

    @Test
    void testGuardarProducto() {
        when(productoRepository.save(any())).thenReturn(producto);

        ProductoToSaveDto productoSaveDto = new ProductoToSaveDto(4l
        ,"Teclado gamer", 650.0, 50 );


        when(productoMapper.productoEntityToDto(any())).thenReturn(productoDto);

        ProductoDto productoGuardadoDto = productoService.guardarProducto(productoSaveDto);

        assertThat(productoGuardadoDto).isNotNull();

    }

    @Test
    void testRemoverProduct() {
         Long idProducto = 1l;

        when(productoRepository.findById(idProducto)).thenReturn(Optional.of(producto));

        productoService.removerProduct(idProducto);

        verify(productoRepository, times(1)).delete(any());
    }
}
