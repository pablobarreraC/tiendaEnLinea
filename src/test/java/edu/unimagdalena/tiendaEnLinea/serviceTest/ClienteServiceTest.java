package edu.unimagdalena.tiendaEnLinea.serviceTest;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteDto;
import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteMapper;
import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteToSaveDto;
import edu.unimagdalena.tiendaEnLinea.entity.Cliente;
import edu.unimagdalena.tiendaEnLinea.repository.ClienteRepository;
import edu.unimagdalena.tiendaEnLinea.service.ClienteServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {
     @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private  ClienteServiceImpl clienteService;

    Cliente cliente, cliente2, cliente3;
    ClienteDto clienteDto;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .id(1l)
                .nombre("Wilkin Aroca")
                .email("arokWilkin@gmail.com")
                .direccion("Mz 3 ciudad equidad")
                .build();


        cliente2 = Cliente.builder()
                .id(2l)
                .nombre("Pablo Barrera")
                .email("barreraPablo@gmail.com")
                .direccion("El pando calle 3 Cs2")
                .build();

        cliente3 = Cliente.builder()
                .id(3l)
                .nombre("Jorge Centeno")
                .email("centenoJorge@gmail.com")
                .direccion("Garagoa calle 2 Cs 4")
                .build();

        clienteDto = ClienteMapper.instancia.clienteEntityToDto(cliente);

    }

    @SuppressWarnings("null")
    @Test
    void testActualizarCliente() {
        when(clienteRepository.findById(any())).thenReturn(Optional.of(cliente));

        ClienteToSaveDto clienteActualizar= new ClienteToSaveDto(4,
                "Pablo Barrera",
                "pabloBarrera@gmail.com",
                "Calle 29");

        when(clienteRepository.save(any())).thenReturn(cliente);

        when(clienteMapper.clienteEntityToDto(any())).thenReturn(clienteDto);

        ClienteDto clienteActualizado = clienteService.actualizarCliente(1l, clienteActualizar);

        assertThat(clienteActualizado).isNotNull();
    

    }

    @Test
    void testBuscarClientePorEmail() {
        
    }

    @Test
    void testBuscarClientePorId() {

    }

    @Test
    void testBuscarClientesPorDireccion() {

    }

    @Test
    void testBuscarClientesPorPrimerNombre() {

    }

    @Test
    void testEncontrarTodosClientes() {

    }

    @Test
    void givenAnClienteWhenSaveThenIdClienteIsNotNull() {
        when(clienteRepository.save(any())).thenReturn(cliente);
         
        ClienteToSaveDto clienteGuardar = new ClienteToSaveDto(1,
                "Juan Ramirez",
                "juan@gmail.com",
                "Calle 29");
        when(clienteMapper.clienteEntityToDto(any())).thenReturn(clienteDto);
        ClienteDto clienteGuardado = clienteService.guardarCliente(clienteGuardar);
        assertThat(clienteGuardado).isNotNull();
    }

    @Test
    void testRemoverUsuario() {
         Long idCliente = 1l;
        when(clienteRepository.findById(any())).thenReturn(Optional.of(cliente));
        clienteService.removerUsuario(idCliente);

        verify(clienteRepository, times(1)).delete(any());

    }
}
