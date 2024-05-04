package edu.unimagdalena.tiendaEnLinea.controller;

import edu.unimagdalena.tiendaEnLinea.dto.detalleEnvio.DetalleEnvioDto;
import edu.unimagdalena.tiendaEnLinea.dto.detalleEnvio.DetalleEnvioToSaveDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.excepción.DetalleEnvioNotFoundException;
import edu.unimagdalena.tiendaEnLinea.service.DetalleEnvioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/detallesEnvio")
public class detalleEnvioController {
    private final DetalleEnvioService detalleEnvioService;

    public detalleEnvioController(DetalleEnvioService detalleEnvioService) {
        this.detalleEnvioService = detalleEnvioService;
    }

    @GetMapping
    public ResponseEntity<List<DetalleEnvioDto>> obtenerDetallesEnvio(){
        List<DetalleEnvioDto> detalleEnvioDtos=detalleEnvioService.buscarTodosDetallesEnvio();
        return ResponseEntity.ok().body(detalleEnvioDtos);
    }

    @PostMapping
    public ResponseEntity<DetalleEnvioDto> guardarDetalleEnvio(@RequestBody DetalleEnvioToSaveDto detalleEnvio){
        DetalleEnvioDto detalle=detalleEnvioService.guardarDetalleEnvio(detalleEnvio);
        return ResponseEntity.ok().body(detalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleEnvioDto> actualizarDetalleEnvioPorId(@PathVariable Long id,@RequestBody DetalleEnvioToSaveDto detalleEnvioToSaveDto){
        DetalleEnvioDto actualizado=detalleEnvioService.actualizarDetalleEnvioPorId(id,detalleEnvioToSaveDto);
        return ResponseEntity.ok().body(actualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleEnvioDto> obtenerDetalleEnvioPorId(@PathVariable Long id) {
        try {
            DetalleEnvioDto detalleEnvioDto = detalleEnvioService.buscarDetalleEnvioPorId(id);
            return ResponseEntity.ok().body(detalleEnvioDto);
        } catch (DetalleEnvioNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<List<DetalleEnvioDto>> obtenerDetallesEnvioPorPedido(@RequestBody PedidoDto pedidoDto){
        List<DetalleEnvioDto> detalleEnvioDtos=detalleEnvioService.buscarLosDetallesDelEnvíoPorPedidoId(pedidoDto);
        return ResponseEntity.ok().body(detalleEnvioDtos);
    }

    @GetMapping("/{transportadora}")
    public ResponseEntity<List<DetalleEnvioDto>> obtenerDetallesEnvioPorTransportadora(@PathVariable String transportadora){
        List<DetalleEnvioDto> detalleEnvioDtos=detalleEnvioService.buscarLosDetallesDelEnvíoParaUnaTrasportadora(transportadora);
        return ResponseEntity.ok().body(detalleEnvioDtos);
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<DetalleEnvioDto>> obtenerDetallesEnvioPorStatus(@PathVariable String status){
        List<DetalleEnvioDto> detalleEnvioDtos=detalleEnvioService.buscarLosDetallesDelEnvioPorStatus(status);
        return ResponseEntity.ok().body(detalleEnvioDtos);
    }
}
