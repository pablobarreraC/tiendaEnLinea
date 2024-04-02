package edu.unimagdalena.tiendaEnLinea.service;

import java.util.List;
import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteDto;
import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteToSaveDto;
import edu.unimagdalena.tiendaEnLinea.excepción.ClienteNotFoundException;

public interface ClienteService {
    ClienteDto guardarCliente(ClienteToSaveDto cliente);
    ClienteDto actualizarCliente(Long id, ClienteToSaveDto cliente);
    ClienteDto buscarClientePorId(Long id) throws ClienteNotFoundException;
    void removerUsuario(Long id);

    List<ClienteDto> encontrarTodosClientes();

    ClienteDto buscarClientePorEmail(String email) throws ClienteNotFoundException;
    List<ClienteDto> buscarClientesPorDireccion(String direccion);
    List<ClienteDto> buscarClientesPorPrimerNombre(String nombre);
}
