package edu.unimagdalena.tiendaEnLinea.service;

import java.util.List;

import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoDto;
import org.springframework.stereotype.Service;

import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoMapper;
import edu.unimagdalena.tiendaEnLinea.dto.itemPedido.ItemPedidoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.entity.ItemPedido;
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
        ItemPedido itemPedido = itemPedidoMapper.itemPedidoSaveDtoToItemPedidoEntity(itemPedidoDto);
        ItemPedido itemPedidoGuardado = itemPedidoRepository.save(itemPedido);
        return itemPedidoMapper.itemPedidoEntityToItemPedidoDto(itemPedidoGuardado);
    }

    @Override
    public ItemPedidoDto actualizarItemPedidoPorId(Long id, ItemPedidoToSaveDto itemPedidoDto) {
        ItemPedido itemPedidoInDb = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNotFoundException("El Item del Pedido no fue encontrado"));

        itemPedidoInDb.setCantidad(itemPedidoDto.cantidad());
        itemPedidoInDb.setPrecioUnitario(itemPedidoDto.precioUnitario());

        ItemPedido itemPedidoGuardado = itemPedidoRepository.save(itemPedidoInDb);
        return itemPedidoMapper.itemPedidoEntityToItemPedidoDto(itemPedidoGuardado);
    }

    @Override
    public ItemPedidoDto buscarItemPedidoPorId(Long id) throws ItemPedidoNotFoundException {
        ItemPedido itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNotFoundException("Item del pedido no encontrado"));
        return itemPedidoMapper.itemPedidoEntityToItemPedidoDto(itemPedido);
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
                .map(itemPedido -> itemPedidoMapper.itemPedidoEntityToItemPedidoDto(itemPedido))
                .toList();
    }

    //Otros metodos

    @Override
    public List<ItemPedidoDto> buscarItemsDelPedidoPorPedidoId(Long idPedido) {
        List<ItemPedido> itemPedidos = itemPedidoRepository.findItemPedidoByPedidoId(idPedido);
        if (itemPedidos.isEmpty())
            throw new ItemPedidoNotFoundException("No se encontraron Item de Pedidos para el Pedido identificado con el id: " + idPedido);
        return itemPedidos.stream()
                .map(itemPedido -> itemPedidoMapper.itemPedidoEntityToItemPedidoDto(itemPedido))
                .toList();
    }

    @Override
    public List<ItemPedidoDto> buscarItemsDelPedidoParaUnProductoEspecífico(Long idProducto) {
        List<ItemPedido> itemPedidos = itemPedidoRepository.findItemPedidoByProductoId(idProducto);
        if (itemPedidos.isEmpty())
            throw new ItemPedidoNotFoundException("No se encontraron Item de Pedidos para el Producto identificado con el id: " + idProducto);
        return itemPedidos.stream()
                .map(itemPedido -> itemPedidoMapper.itemPedidoEntityToItemPedidoDto(itemPedido))
                .toList();
    }

    @Override
    public Double calcularLaSumaDelTotalDeVentasParaUnProducto(ProductoDto idProducto) {
        return itemPedidoRepository.findTotalByProductoId(idProducto);
    }

}
