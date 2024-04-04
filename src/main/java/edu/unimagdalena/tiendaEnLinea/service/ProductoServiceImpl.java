package edu.unimagdalena.tiendaEnLinea.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoDto;
import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoMapper;
import edu.unimagdalena.tiendaEnLinea.dto.producto.ProductoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.entity.Producto;
import edu.unimagdalena.tiendaEnLinea.excepción.NotAbleToDeleteException;
import edu.unimagdalena.tiendaEnLinea.excepción.ProductoNotFoundException;
import edu.unimagdalena.tiendaEnLinea.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {
    private final ProductoMapper productoMapper;
    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoMapper productoMapper, ProductoRepository productoRepository) {
        this.productoMapper = productoMapper;
        this.productoRepository = productoRepository;
    }

    @Override
    public ProductoDto guardarProducto(ProductoToSaveDto productoDto) {
        Producto producto = productoMapper.productoToSaveDtoToEntity(productoDto);
        Producto productoGuardado = productoRepository.save(producto);
        return productoMapper.productoEntityToDto(productoGuardado);
    }

    @Override
    public ProductoDto buscarProductoPorId(Long id) throws ProductoNotFoundException {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));
        return productoMapper.productoEntityToDto(producto);
    }

    @Override
    public List<ProductoDto> buscarTodosProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(producto -> productoMapper.productoEntityToDto(producto))
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

        return productoMapper.productoEntityToDto(productoGuardado);
    }

    @Override
    public void removerProduct(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NotAbleToDeleteException("Producto no encontrado"));
        productoRepository.delete(producto);
    }

    @Override
    public List<ProductoDto> buscarProductoPorNombre(String nombre) {
        List<Producto> productos = productoRepository.findByNombreContainingIgnoreCase(nombre);
        if (productos.isEmpty()) {
            throw new ProductoNotFoundException("Producto no encontrado");
        }
        return productos.stream()
                .map(producto -> productoMapper.productoEntityToDto(producto))
                .toList();
    }

    @Override
    public List<ProductoDto> buscarLosProductosQueEstanEnStock() {
        List<Producto> productos = productoRepository.findByStockIsGreaterThanZero();
        if (productos.isEmpty()) {
            throw new ProductoNotFoundException("Producto no encontrado");
        }
        return productos.stream()
                .map(producto -> productoMapper.productoEntityToDto(producto))
                .toList();
    }

    @Override
    public List<ProductoDto> buscarLosProductosQueNoSuperenUnPrecioYUnStockDeterminado(Double price, Integer stock) {
        List<Producto> productos = productoRepository.findByPriceLessOrEqualsThanAndStockIs(price, stock);
        if (productos.isEmpty()) {
            throw new ProductoNotFoundException("Producto no encontrado");
        }
        return productos.stream()
                .map(producto -> productoMapper.productoEntityToDto(producto))
                .toList();
    }

}
