package edu.unimagdalena.tiendaEnLinea.repository;

import com.github.dockerjava.api.command.ListServicesCmd;
import edu.unimagdalena.tiendaEnLinea.entity.Producto;
import edu.unimagdalena.tiendaEnLinea.plantillaTestIntegracionPersistencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ProductoRepositoryTest extends plantillaTestIntegracionPersistencia {
    ProductoRepository productoRepository;

    @Autowired
    public ProductoRepositoryTest(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    Producto producto(){
        Producto producto=Producto.builder().nombre("Producto1")
                .price(45.000d)
                .stock(20)
                .build();
        return producto;
    }

    void crearConjuntoProductos(){
        Producto producto1=Producto.builder()
                .nombre("Producto1")
                .price(40.000d)
                .stock(30)
                .build();
        productoRepository.save(producto1);
        Producto producto2=Producto.builder()
                .nombre("Producto2")
                .price(30.000d)
                .stock(10)
                .build();
        productoRepository.save(producto2);
        productoRepository.flush();
    }
    @BeforeEach
    void setUp() {
        productoRepository.deleteAll();
    }

    @Test
    void givenAnProductWhenSaveThenProductIdIsNotNull(){
        //given
        Producto producto=producto();
        //when
        Producto guardado=productoRepository.save(producto);
        //then
        assertThat(guardado.getId()).isNotNull();
    }

    @Test
    void givenAnIdProductWhenGetByIdThenProductIsNotNull(){
        //given
        Producto producto=productoRepository.save(producto());
        //when
        Optional<Producto> encontrado=productoRepository.findById(producto.getId());
        //then
        assertThat(encontrado).isNotNull();
    }

    @Test
    void givenAnProductWhenDeleteByIdThenProductIsEmpty(){
        //given
        Producto producto=productoRepository.save(producto());
        //when
        productoRepository.deleteById(producto.getId());
        //then
        Optional<Producto> encontrado=productoRepository.findById(producto.getId());
        assertThat(encontrado).isEmpty();
    }

    @Test
    void givenAnProductExistingWhenSaveThenUpdateFieldsDifferent(){
        //given
        Producto producto=productoRepository.save(producto());
        //when
        producto.setNombre("Producto1Actualizado");
        Producto actualizado=productoRepository.save(producto);
        //then
        assertThat(actualizado.getNombre()).isEqualTo("Producto1Actualizado");
    }

    @Test
    void givenASetProductWhenFindByNameContainingThenHasSizeGreaterThanZero(){
        //given
        crearConjuntoProductos();
        //when
        List<Producto> productos=productoRepository.findByNombreContainingIgnoreCase("Producto");
        //then
        assertThat(productos).hasSizeGreaterThan(0);
        assertThat(productos).first().hasFieldOrPropertyWithValue("nombre","Producto1");
    }

    @Test
    void givenAllProductsWhenFindByStockIsGreaterThanZeroThenHasSizeGreaterThanZero(){
        //given
        crearConjuntoProductos();
        //when
        List<Producto> productos=productoRepository.findByStockIsGreaterThanZero();
        //then
        assertThat(productos).hasSizeGreaterThan(0);
        assertThat(productos).first().hasFieldOrPropertyWithValue("price",40.000d);
    }

    @Test
    void givenPriceAndStockWhenFindByPriceAndStockThenHasSizeIsOne(){
        //given
        crearConjuntoProductos();
        Double price=30.000d;
        Integer stock=10;
        //when
        List<Producto> productos=productoRepository.findByPriceLessOrEqualsThanAndStockIs(price,stock);
        //then
        assertThat(productos).hasSize(1);
        assertThat(productos).first().hasFieldOrPropertyWithValue("nombre","Producto2");
    }
}