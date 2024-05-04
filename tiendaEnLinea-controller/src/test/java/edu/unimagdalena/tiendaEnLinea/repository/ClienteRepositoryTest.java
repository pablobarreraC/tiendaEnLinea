package edu.unimagdalena.tiendaEnLinea.repository;

import edu.unimagdalena.tiendaEnLinea.entity.Cliente;
import edu.unimagdalena.tiendaEnLinea.plantillaTestIntegracionPersistencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ClienteRepositoryTest extends plantillaTestIntegracionPersistencia {
    ClienteRepository clienteRepository;

    @Autowired
    public ClienteRepositoryTest(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
    }

    void conjuntoClientes(){
        clienteRepository.save(
                Cliente.builder()
                        .nombre("Pablo")
                        .email("pablo@gmail.com")
                        .direccion("Carrera 17A").build());
        clienteRepository.save(
                Cliente.builder()
                        .nombre("Jose")
                        .email("jose@gmail.com")
                        .direccion("El cisne 1").build());
        clienteRepository.save(
                Cliente.builder()
                        .nombre("Pablo Jose")
                        .email("pablojose@gmail.com")
                        .direccion("Carrera 5ta").build());
        clienteRepository.flush();
    }

    Cliente cliente(){
        return Cliente.builder()
                .nombre("Pablo")
                .email("pablo@gmail.com")
                .direccion("Carrera 17A").build();
    }

    @Test
    void givenAnClienteWhenSaveThenIdClienteIsNotNull(){
        //given
        Cliente cliente=cliente();
        //when
        Cliente guardado=clienteRepository.save(cliente);
        //then
        assertThat(guardado.getId()).isNotNull();
    }

    @Test
    void givenAnClienteWhenDeleteByIdThenClienteIsEmpty(){
        //given
        Cliente cliente=clienteRepository.save(cliente());
        //when
        clienteRepository.deleteById(cliente.getId());
        //then
        Optional<Cliente> encontrado=clienteRepository.findById(cliente.getId());
        assertThat(encontrado).isEmpty();
    }

    @Test
    void givenAnClienteWhenFindByIdThenIdClienteIsNotNull(){
        //given
        Cliente cliente=clienteRepository.save(cliente());
        //when
        Optional<Cliente> encontrado=clienteRepository.findById(cliente.getId());
        //then
        assertThat(encontrado.get().getId()).isNotNull();
    }

    @Test
    void givenClienteExistingWhenSaveThenClienteExistingUpdate(){
        //given
        Cliente cliente=clienteRepository.save(cliente());
        //when
        cliente.setNombre("Pablo Andrés");
        Cliente actualizado=clienteRepository.save(cliente);
        //then
        assertThat(actualizado.getNombre()).isEqualTo("Pablo Andrés");
    }

    @Test
    void givenASetClienteWhenFindByEmailThenHasSizeIsOne(){
        //given
        conjuntoClientes();
        //when
        List<Cliente> clientes=clienteRepository.findByEmail("jose@gmail.com");
        //then
        assertThat(clientes).hasSize(1);
        assertThat(clientes).first().hasFieldOrPropertyWithValue("nombre","Jose");
    }

    @Test
    void givenASetClienteWhenFindByDireccionThenHasSizeGreaterThanZero(){
        //given
        conjuntoClientes();
        //when
        List<Cliente> clientes=clienteRepository.findByDireccion("Carrera 17A");
        //then
        assertThat(clientes).hasSizeGreaterThan(0);
        assertThat(clientes).first().hasFieldOrPropertyWithValue("nombre","Pablo");
    }

    @Test
    void givenASetClientesWhenFindByNombreThenHasSizeIsTwo(){
        //given
        conjuntoClientes();
        //when
        List<Cliente> clientes=clienteRepository.findByNombreStartingWith("Pablo");
        //then
        assertThat(clientes).hasSize(2);
        assertThat(clientes.get(1)).hasFieldOrPropertyWithValue("nombre","Pablo Jose");
    }
}