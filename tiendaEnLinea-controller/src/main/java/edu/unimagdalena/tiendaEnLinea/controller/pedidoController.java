package edu.unimagdalena.tiendaEnLinea.controller;

import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoDto;
import edu.unimagdalena.tiendaEnLinea.excepción.PedidoNotFoundException;
import edu.unimagdalena.tiendaEnLinea.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
public class pedidoController {
    private final PedidoService pedidoService;

    public pedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoDto>> obtenerPedidos(){
        List<PedidoDto> productoDtos=pedidoService.buscarTodosPedidos();
        return ResponseEntity.ok().body(productoDtos);
    }

    @PostMapping
    public ResponseEntity<PedidoDto> guardarPedido(@RequestBody PedidoToSaveDto pedido){
        PedidoDto pedidoDto=pedidoService.guardarPedido(pedido);
        return ResponseEntity.ok().body(pedidoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity elimininarPedidoPorId(@PathVariable Long id){
        pedidoService.removerPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDto> actualizarPedido(@PathVariable Long id, @RequestBody PedidoToSaveDto pedido){
        PedidoDto actualizado=pedidoService.actualizarPedidoPorId(id,pedido);
        return ResponseEntity.ok().body(actualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> buscarPedidoPorId(@PathVariable Long id){
        try{
            PedidoDto pedidoDto=pedidoService.buscarPedidoPorId(id);
            return ResponseEntity.ok().body(pedidoDto);
        }catch (PedidoNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{fechaInicial}/{fechaFinal}")
    public ResponseEntity<List<PedidoDto>> obtenerPedidosEntreFechas(@PathVariable LocalDateTime fechaInicial, @PathVariable LocalDateTime fechaFinal){
        List<PedidoDto> pedidoDtos=pedidoService.buscarPedidosEntreDosFecha(fechaInicial,fechaFinal);
        return ResponseEntity.ok().body(pedidoDtos);
    }

    @PostMapping("/{status}")
    public ResponseEntity<List<PedidoDto>> obtenerPedidosPorClienteYEstado(@RequestBody ClienteDto cliente,@PathVariable String status){
        List<PedidoDto> productoDtos=pedidoService.buscarPedidosPorClienteYUnEstado(cliente,status);
        return ResponseEntity.ok().body(productoDtos);
    }

    @PostMapping
    public ResponseEntity<List<PedidoDto>> obtenerPedidoPorCliente(@RequestBody ClienteDto cliente){
        List<PedidoDto> pedidoDtos=pedidoService.findPedidoByClienteWithItems(cliente);
        return ResponseEntity.ok().body(pedidoDtos);
    }
}
