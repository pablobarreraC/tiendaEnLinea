package edu.unimagdalena.tiendaEnLinea.service;
import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteDto;
import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteToSaveDto;
import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteMapper;
import edu.unimagdalena.tiendaEnLinea.entity.Cliente;
import edu.unimagdalena.tiendaEnLinea.excepción.ClienteNotFoundException;
import edu.unimagdalena.tiendaEnLinea.excepción.NotAbleToDeleteException;
import edu.unimagdalena.tiendaEnLinea.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
@Service
public class ClienteServiceImpl implements ClienteService{

    private final ClienteMapper clienteMapper;
    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteMapper clienteMapper, ClienteRepository clienteRepository) {
        this.clienteMapper = clienteMapper;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteDto guardarCliente(ClienteToSaveDto clienteDto) {
        Cliente cliente = clienteMapper.clienteToSaveDtoToEntity(clienteDto);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        return clienteMapper.clienteEntityToDto(clienteGuardado);
    }

   
    @Override
    public ClienteDto actualizarCliente(Long id, ClienteToSaveDto clienteDto){
        Cliente clienteInDb = clienteRepository.findById(id)
        .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));
        clienteInDb.setNombre(clienteDto.nombre());
        clienteInDb.setEmail(clienteDto.email());
        clienteInDb.setDireccion(clienteDto.direccion());
        Cliente clienteGuardado = clienteRepository.save(clienteInDb);
        
        return clienteMapper.clienteEntityToDto(clienteGuardado);
    }


    @Override
    public ClienteDto buscarClientePorId(Long id) throws ClienteNotFoundException{
       Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));
        return clienteMapper.clienteEntityToDto(cliente);
    }

    @Override
    public void removerUsuario(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NotAbleToDeleteException("Cliente no encontrado"));
        clienteRepository.delete(cliente);
    }

    @Override
    public List<ClienteDto> encontrarTodosClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(cliente -> clienteMapper.clienteEntityToDto(cliente))
                .toList();
    }

    //Otros metodos
    @Override
    public ClienteDto buscarClientePorEmail(String email) throws ClienteNotFoundException {
        List<Cliente> clientes = clienteRepository.findByEmail(email);
        if (Objects.isNull(clientes.get(0)))
            throw new ClienteNotFoundException("Cliente no encontrado");
        return clienteMapper.clienteEntityToDto(clientes.get(0));
    }

    @Override
    public List<ClienteDto> buscarClientesPorDireccion(String direccion) {
        List<Cliente> clientes = clienteRepository.findByDireccion(direccion);
        if(clientes.isEmpty())
            throw new ClienteNotFoundException("Cliente no encontrado");
        return clientes.stream()
                .map(cliente -> clienteMapper.clienteEntityToDto(cliente))
                .toList();
    }

    @Override
    public List<ClienteDto> buscarClientesPorPrimerNombre(String nombre) {
        List<Cliente> clientes = clienteRepository.findByNombreStartingWith(nombre);
        if(clientes.isEmpty())
            throw new ClienteNotFoundException("Cliente no encontrado");
        return clientes.stream()
                .map(cliente -> clienteMapper.clienteEntityToDto(cliente))
                .toList();
    }
}
