package edu.unimagdalena.tiendaEnLinea.service;

import org.springframework.stereotype.Service;

import edu.unimagdalena.tiendaEnLinea.excepción.NotAbleToDeleteException;
import edu.unimagdalena.tiendaEnLinea.excepción.ProductoNotFoundException;

@Service
public class ProductoServiceImpl implements ProductoService {
  /*    private final ProductoMapper productoMapper;
    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoMapper productoMapper, ProductoRepository productoRepository) {
        this.productoMapper = productoMapper;
        this.productoRepository = productoRepository;
    }

    @Override
    public ProductoDto guardarProducto(ProductoToSaveDto productoDto) {
        Producto producto = productoMapper.productoSaveDtoToProductoEntity(productoDto);
        Producto productoGuardado = productoRepository.save(producto);
        return productoMapper.productoEntityToProductoDto(productoGuardado);
    }

    @Override
    public ProductoDto buscarProductoPorId(Long id) throws ProductoNotFoundException {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));
        return productoMapper.productoEntityToProductoDto(producto);
    }

    @Override
    public List<ProductoDto> buscarTodosProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(producto -> productoMapper.productoEntityToProductoDto(producto))
                .toList();
    }

    @Override
    public ProductoDto actualizarProductoPorId(Long id, ProductoToSaveDto productoDto) {
        Producto productoInDb = productoRepository.findById(id)
                        .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));

        productoInDb.setNombre(productoDto.nombre());
        productoInDb.setPrice(productoDto.price());
        productoInDb.setStock(productoDto.stock());

        Producto productoGuardado = productoRepository.save(productoInDb);

        return productoMapper.productoEntityToProductoDto(productoGuardado);
    }

    @Override
    public void removerProduct(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NotAbleToDeleteException("Producto no encontrado"));
        productoRepository.delete(producto);
    }

    @Override
    public List<ProductoDto> buscarProductoPorNombre(String nombre) {
        List<Producto> productos = productoRepository.findProductoByNombre(nombre);
        if (productos.isEmpty()) {
            throw new ProductoNotFoundException("Producto no encontrado");
        }
        return productos.stream()
                .map(producto -> productoMapper.productoEntityToProductoDto(producto))
                .toList();
    }

    @Override
    public List<ProductoDto> buscarLosProductosQueEstanEnStock(Integer stock) {
        List<Producto> productos = productoRepository.findProductoByStockGreaterThan(stock);
        if (productos.isEmpty()) {
            throw new ProductoNotFoundException("Producto no encontrado");
        }
        return productos.stream()
                .map(producto -> productoMapper.productoEntityToProductoDto(producto))
                .toList();
    }

    @Override
    public List<ProductoDto> buscarLosProductosQueNoSuperenUnPrecioYUnStockDeterminado(Double price, Integer stock) {
        List<Producto> productos = productoRepository.findProductoByPriceAndStock(price, stock);
        if (productos.isEmpty()) {
            throw new ProductoNotFoundException("Producto no encontrado");
        }
        return productos.stream()
                .map(producto -> productoMapper.productoEntityToProductoDto(producto))
                .toList();
    }
*/

}
