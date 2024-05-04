package edu.unimagdalena.tiendaEnLinea.controller;

import edu.unimagdalena.tiendaEnLinea.dto.pago.PagoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pago.PagoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.excepción.PagoNotFoundException;
import edu.unimagdalena.tiendaEnLinea.service.PagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
public class pagoController {
    private final PagoService pagoService;

    public pagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public ResponseEntity<List<PagoDto>> obtenerPagos(){
        List<PagoDto> pagoDtos=pagoService.buscarTodosPagos();
        return ResponseEntity.ok().body(pagoDtos);
    }

    @PostMapping
    public ResponseEntity<PagoDto> guardarPago(@RequestBody PagoToSaveDto pago){
        PagoDto pagoDto=pagoService.guardarPago(pago);
        return ResponseEntity.ok().body(pagoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarPagoPorId(@PathVariable Long id){
        pagoService.removerPago(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoDto> actualizarPagoPorId(@PathVariable Long id, @RequestBody PagoToSaveDto pago){
        PagoDto pagoDto=pagoService.actualizarPagoPorId(id,pago);
        return ResponseEntity.ok().body(pagoDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDto> obtenerPagoPorId(@PathVariable Long id){
        try{
            PagoDto pagoDto=pagoService.buscarPagoPorId(id);
            return ResponseEntity.ok().body(pagoDto);
        }catch (PagoNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{fechaInicial}/{fechaFinal}")
    public ResponseEntity<List<PagoDto>> obtenerPagosEntreFechas(@PathVariable LocalDateTime fechaInicial,
                                                                 @PathVariable LocalDateTime fechaFinal){
        List<PagoDto> pagoDtos=pagoService.recuperarPagosDentroDeUnRangoDeFecha(fechaInicial,fechaFinal);
        return ResponseEntity.ok().body(pagoDtos);
    }

    @PostMapping("/{metodoPago}")
    public ResponseEntity<List<PagoDto>> obtenerPagosPorPedidoYMetodoDePago(@RequestBody PedidoDto pedidoDto,@PathVariable String metodoPago){
        List<PagoDto> pagoDtos=pagoService.recuperarPagosPorUnIdentificadorDeUnaOrdenYMétodoDePago(pedidoDto,metodoPago);
        return ResponseEntity.ok().body(pagoDtos);
    }
}
