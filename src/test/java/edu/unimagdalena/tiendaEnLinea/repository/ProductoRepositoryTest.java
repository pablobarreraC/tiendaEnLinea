package edu.unimagdalena.tiendaEnLinea.repository;

import edu.unimagdalena.tiendaEnLinea.entity.Producto;
import edu.unimagdalena.tiendaEnLinea.plantillaTestIntegracionPersistencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
}