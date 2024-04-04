package edu.unimagdalena.tiendaEnLinea.service;

import java.util.List;

import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoDto;
import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoMapper;

import org.springframework.stereotype.Service;

import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoMapper;
import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoMapper;
import edu.unimagdalena.tiendaEnLinea.entity.ItemPedido;
import edu.unimagdalena.tiendaEnLinea.entity.Pedido;
import edu.unimagdalena.tiendaEnLinea.entity.Producto;
import edu.unimagdalena.tiendaEnLinea.excepción.ItemPedidoNotFoundException;
import edu.unimagdalena.tiendaEnLinea.excepción.NotAbleToDeleteException;
import edu.unimagdalena.tiendaEnLinea.repository.ItemPedidoRepository;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService{
    private final ItemPedidoMapper itemPedidoMapper;
    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoServiceImpl(ItemPedidoMapper itemPedidoMapper, ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoMapper = itemPedidoMapper;
        this.itemPedidoRepository = itemPedidoRepository;
    }


    @Override
    public ItemPedidoDto guardarItemPedido(ItemPedidoToSaveDto itemPedidoDto) {
        ItemPedido itemPedido = itemPedidoMapper.itemPedidoToSaveDtoToEntity(itemPedidoDto);
        ItemPedido itemPedidoGuardado = itemPedidoRepository.save(itemPedido);
        return itemPedidoMapper.itemPedidoEntityToDto(itemPedidoGuardado);
    }

    @Override
    public ItemPedidoDto actualizarItemPedidoPorId(Long id, ItemPedidoToSaveDto itemPedidoDto) {
        ItemPedido itemPedidoInDb = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNotFoundException("El Item del Pedido no fue encontrado"));

        itemPedidoInDb.setCantidad(itemPedidoDto.cantidad());
        itemPedidoInDb.setPrecioUnitario(itemPedidoDto.precioUnitario());

        ItemPedido itemPedidoGuardado = itemPedidoRepository.save(itemPedidoInDb);
        return itemPedidoMapper.itemPedidoEntityToDto(itemPedidoGuardado);
    }

    @Override
    public ItemPedidoDto buscarItemPedidoPorId(Long id) throws ItemPedidoNotFoundException {
        ItemPedido itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNotFoundException("Item del pedido no encontrado"));
        return itemPedidoMapper.itemPedidoEntityToDto(itemPedido);
    }

    @Override
    public void removerItemPedido(Long id) {
        ItemPedido itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new NotAbleToDeleteException("Item del pedido no se encontro"));
        itemPedidoRepository.delete(itemPedido);
    }

    @Override
    public List<ItemPedidoDto> buscarTodosItemPedidos() {
        List<ItemPedido> itemPedidos = itemPedidoRepository.findAll();
        return itemPedidos.stream()
                .map(itemPedido -> itemPedidoMapper.itemPedidoEntityToDto(itemPedido))
                .toList();
    }

    //Otros metodos

    @Override
    public List<ItemPedidoDto> buscarItemsDelPedidoPorPedidoId(PedidoDto idPedido) {
        Pedido pedido=PedidoMapper.instancia.pedidoDtoToEntity(idPedido);
        List<ItemPedido> itemPedidos = itemPedidoRepository.findByPedidoId(pedido);
        if (itemPedidos.isEmpty())
            throw new ItemPedidoNotFoundException("No se encontraron Item de Pedidos para el Pedido identificado con el id: " + idPedido);
        return itemPedidos.stream()
                .map(itemPedido -> itemPedidoMapper.itemPedidoEntityToDto(itemPedido))
                .toList();
    }

    @Override
    public List<ItemPedidoDto> buscarItemsDelPedidoParaUnProductoEspecífico(ProductoDto idProducto) {
        Producto producto=ProductoMapper.instancia.productoDtoToEntity(idProducto);
        List<ItemPedido> itemPedidos = itemPedidoRepository.findByProductoId(producto);
        if (itemPedidos.isEmpty())
            throw new ItemPedidoNotFoundException("No se encontraron Item de Pedidos para el Producto identificado con el id: " + idProducto);
        return itemPedidos.stream()
                .map(itemPedido -> itemPedidoMapper.itemPedidoEntityToDto(itemPedido))
                .toList();
    }

    @Override
    public Integer calcularLaSumaDelTotalDeVentasParaUnProducto(ProductoDto idProducto) {
        Producto producto=ProductoMapper.instancia.productoDtoToEntity(idProducto);
        return itemPedidoRepository.calcularTotalVentasDeProducto(producto);
    }

}
