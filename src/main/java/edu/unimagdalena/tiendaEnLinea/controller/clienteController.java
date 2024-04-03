package edu.unimagdalena.tiendaEnLinea.controller;

import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteDto;
import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteToSaveDto;
import edu.unimagdalena.tiendaEnLinea.excepción.ClienteNotFoundException;
import edu.unimagdalena.tiendaEnLinea.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class clienteController {
    private final ClienteService clienteService;

    public clienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDto>> obtenerClientes(){
        List<ClienteDto> clienteDtos=clienteService.encontrarTodosClientes();
        return ResponseEntity.ok().body(clienteDtos);
    }

    @PostMapping
    public ResponseEntity<ClienteDto> guardarCliente(@RequestBody ClienteToSaveDto cliente){
        ClienteDto guardado=clienteService.guardarCliente(cliente);
        return ResponseEntity.ok().body(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> actualizarClientePorId(@PathVariable Long id,@RequestBody ClienteToSaveDto cliente){
        ClienteDto actualizado=clienteService.actualizarCliente(id,cliente);
        return ResponseEntity.ok().body(actualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> obtenerClientePorId(@PathVariable Long id){
        try{
            ClienteDto clienteDto=clienteService.buscarClientePorId(id);
            return ResponseEntity.ok().body(clienteDto);
        }catch (ClienteNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarClientePorId(@PathVariable Long id){
        clienteService.removerUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{email}")
    public ResponseEntity<ClienteDto> obtenerClientePorEmail(@PathVariable String email){
        try{
            ClienteDto clienteDto=clienteService.buscarClientePorEmail(email);
            return ResponseEntity.ok().body(clienteDto);
        }catch (ClienteNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{direccion}")
    public ResponseEntity<List<ClienteDto>> obtenerClientesPorDireccion(@PathVariable String direccion){
        List<ClienteDto> clienteDtos=clienteService.buscarClientesPorDireccion(direccion);
        return ResponseEntity.ok().body(clienteDtos);
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<List<ClienteDto>> obtenerClientesPorPrimerNombre(@PathVariable String nombre){
        List<ClienteDto> clienteDtos=clienteService.buscarClientesPorPrimerNombre(nombre);
        return ResponseEntity.ok().body(clienteDtos);
    }
}
