package edu.unimagdalena.tiendaEnLinea.controller;

import edu.unimagdalena.tiendaEnLinea.dto.cliente.AuthRequest;
import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteDto;
import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteToSaveDto;
import edu.unimagdalena.tiendaEnLinea.excepción.ClienteNotFoundException;
import edu.unimagdalena.tiendaEnLinea.service.ClienteService;
import edu.unimagdalena.tiendaEnLinea.serviceSecurity.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class clienteController {
    private final ClienteService clienteService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public clienteController(ClienteService clienteService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.clienteService = clienteService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @GetMapping("/getAlls")
    public ResponseEntity<List<ClienteDto>> obtenerClientes(){
        List<ClienteDto> clienteDtos=clienteService.encontrarTodosClientes();
        return ResponseEntity.ok().body(clienteDtos);
    }

    @PostMapping("/register") //se hace el registro del cliente
    public ResponseEntity<ClienteDto> guardarCliente(@RequestBody ClienteToSaveDto cliente){
        ClienteDto guardado=clienteService.guardarCliente(cliente);
        return ResponseEntity.ok().body(guardado);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ClienteDto> actualizarClientePorId(@PathVariable Long id,@RequestBody ClienteToSaveDto cliente){
        ClienteDto actualizado=clienteService.actualizarCliente(id,cliente);
        return ResponseEntity.ok().body(actualizado);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ClienteDto> obtenerClientePorId(@PathVariable Long id){
        try{
            ClienteDto clienteDto=clienteService.buscarClientePorId(id);
            return ResponseEntity.ok().body(clienteDto);
        }catch (ClienteNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity eliminarClientePorId(@PathVariable Long id){
        clienteService.removerUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<ClienteDto> obtenerClientePorEmail(@PathVariable String email){
        try{
            ClienteDto clienteDto=clienteService.buscarClientePorEmail(email);
            return ResponseEntity.ok().body(clienteDto);
        }catch (ClienteNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getByDirec/{direccion}")
    public ResponseEntity<List<ClienteDto>> obtenerClientesPorDireccion(@PathVariable String direccion){
        List<ClienteDto> clienteDtos=clienteService.buscarClientesPorDireccion(direccion);
        return ResponseEntity.ok().body(clienteDtos);
    }

    @GetMapping("/getByName/{nombre}")
    public ResponseEntity<List<ClienteDto>> obtenerClientesPorPrimerNombre(@PathVariable String nombre){
        List<ClienteDto> clienteDtos=clienteService.buscarClientesPorPrimerNombre(nombre);
        return ResponseEntity.ok().body(clienteDtos);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.email());
            return ResponseEntity.ok(token);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
