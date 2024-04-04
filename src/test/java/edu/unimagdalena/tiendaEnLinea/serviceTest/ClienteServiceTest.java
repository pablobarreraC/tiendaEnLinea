package edu.unimagdalena.tiendaEnLinea.serviceTest;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties.RSocket.Client;

import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteDto;
import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteMapper;
import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteToSaveDto;
import edu.unimagdalena.tiendaEnLinea.entity.Cliente;
import edu.unimagdalena.tiendaEnLinea.repository.ClienteRepository;
import edu.unimagdalena.tiendaEnLinea.service.ClienteServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {
        
    @Mock
    private ClienteMapper clienteMapper;
     @Mock
    private ClienteRepository clienteRepository;


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
                "El pando calle 3 Cs2");

        when(clienteRepository.save(any())).thenReturn(cliente);

        when(clienteMapper.clienteEntityToDto(any())).thenReturn(clienteDto);

        ClienteDto clienteActualizado = clienteService.actualizarCliente(1l, clienteActualizar);

        assertThat(clienteActualizado).isNotNull();
    

    }

    @Test
    void testBuscarClientePorEmailDevolveraUnCliente() {
        List<Cliente> clientes = List.of(cliente, cliente2,cliente3);
        String emailCliente = "barreraPablo@gmail.com";

        when(clienteRepository.findByEmail(any())).thenReturn(clientes);

        when(clienteMapper.clienteEntityToDto(any())).thenReturn(clienteDto);

        ClienteDto clienteDto = clienteService.buscarClientePorEmail(emailCliente);

        assertThat(clienteDto).isNotNull();
        
    }

    @Test
    void testBuscarClientePorIdDevolveraUnCliente() {
        when(clienteRepository.findById(any())).thenReturn(Optional.of(cliente));

        when(clienteMapper.clienteEntityToDto(any())).thenReturn(clienteDto);
        ClienteDto clienteEncontrado = clienteService.buscarClientePorId(1l);
        assertThat(clienteEncontrado).isNotNull();
    }

    @Test
    void testBuscarClientesPorDireccionDevuleveUnClientes() {
        List<Cliente> clientes = List.of(cliente,cliente2, cliente3);
        String direccionCliente = "Garagoa calle 2 Cs 4";

        when(clienteRepository.findByDireccion(any())).thenReturn(clientes);

        List<ClienteDto> clienteDtos = clienteService.buscarClientesPorDireccion(direccionCliente);

        assertThat(clienteDtos).isNotEmpty();
        assertThat(clienteDtos).hasSize(1);


    }

    @Test
    void testBuscarClientesPorPrimerNombreDevolveraClientes() {
        List<Cliente> clientes = List.of(cliente,cliente2, cliente3);
        String nombreCliente = "Wilkin";

        when(clienteRepository.findByNombreStartingWith(any())).thenReturn(clientes);

        List<ClienteDto> clienteDtos = clienteService.buscarClientesPorPrimerNombre(nombreCliente);

        assertThat(clienteDtos).isNotEmpty();
        assertThat(clienteDtos).hasSize(1);
    }

    @Test
    void testEncontrarTodosClientesDevolveraUnaListaClientes() {
        List<Cliente> clientes = List.of(cliente, cliente2, cliente3);

        when(clienteRepository.findAll()).thenReturn(clientes);

        List<ClienteDto> clienteDtos = clienteService.encontrarTodosClientes();

        assertThat(clienteDtos).isNotEmpty();
        assertThat(clienteDtos).hasSize(3);
    }

    @Test
    void givenAnClienteWhenSaveThenIdClienteIsNotNull() {
        when(clienteRepository.save(any())).thenReturn(cliente);

        ClienteToSaveDto clienteToSave = new ClienteToSaveDto(1L, "Pablo", "pablo@gmail.com", "Calle 2")

        when(clienteMapper.clienteEntityToDto(any())).thenReturn(clienteDto);

        ClienteDto guardarCliente = clienteService.guardarCliente(clienteToSave);

        assertThat(guardarCliente).isNotNull();
    }

    @Test
    void testRemoverUsuarioDevolveraUnMensaje() {
        Long idCliente = 1l;
        when(clienteRepository.findById(any())).thenReturn(Optional.of(cliente));
        clienteService.removerUsuario(idCliente);

        verify(clienteRepository, times(1)).delete(any());
    }
}
