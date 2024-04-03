package edu.unimagdalena.tiendaEnLinea.controller;

import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoDto;
import edu.unimagdalena.tiendaEnLinea.excepción.ItemPedidoNotFoundException;
import edu.unimagdalena.tiendaEnLinea.service.ItemPedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/itemsPedido")
public class itemPedidoController {
    private final ItemPedidoService itemPedidoService;

    public itemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @GetMapping
    public ResponseEntity<List<ItemPedidoDto>> obtenerItemsPedido(){
        List<ItemPedidoDto> itemPedidoDtos=itemPedidoService.buscarTodosItemPedidos();
        return ResponseEntity.ok().body(itemPedidoDtos);
    }

    @PostMapping
    public ResponseEntity<ItemPedidoDto> guardarItemPedido(@RequestBody ItemPedidoToSaveDto itemPedido){
        ItemPedidoDto itemPedidoDto=itemPedidoService.guardarItemPedido(itemPedido);
        return ResponseEntity.ok().body(itemPedidoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedidoDto> actualizarItemPedidoPorId(@PathVariable Long id,@RequestBody ItemPedidoToSaveDto itemPedidoToSaveDto){
        ItemPedidoDto actualizado=itemPedidoService.actualizarItemPedidoPorId(id,itemPedidoToSaveDto);
        return ResponseEntity.ok().body(actualizado);
    }

    @GetMapping("/{id")
    public ResponseEntity<ItemPedidoDto> obtenerItemPedidoPorId(@PathVariable Long id){
        try{
            ItemPedidoDto itemPedidoDto=itemPedidoService.buscarItemPedidoPorId(id);
            return ResponseEntity.ok().body(itemPedidoDto);
        }catch (ItemPedidoNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eleminarPorId(@PathVariable Long id){
        itemPedidoService.removerItemPedido(id);
        return  ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<List<ItemPedidoDto>> buscarItemsPorPedido(@RequestBody PedidoDto pedidoDto){
        List<ItemPedidoDto> itemPedidoDtos=itemPedidoService.buscarItemsDelPedidoPorPedidoId(pedidoDto);
        return ResponseEntity.ok().body(itemPedidoDtos);
    }

    @PostMapping
    public ResponseEntity<List<ItemPedidoDto>> buscarItemsPorProducto(@RequestBody ProductoDto productoDto){
        List<ItemPedidoDto> itemPedidoDtos=itemPedidoService.buscarItemsDelPedidoParaUnProductoEspecífico(productoDto);
        return ResponseEntity.ok().body(itemPedidoDtos);
    }

    @PostMapping
    public ResponseEntity<Integer> obtenerVentasPorProducto(@RequestBody ProductoDto productoDto){
        Integer total=itemPedidoService.calcularLaSumaDelTotalDeVentasParaUnProducto(productoDto);
        return ResponseEntity.ok().body(total);
    }
}
