package edu.unimagdalena.tiendaEnLinea.dto.producto;

import edu.unimagdalena.tiendaEnLinea.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductoMapper {
    ProductoMapper instancia= Mappers.getMapper(ProductoMapper.class);

    ProductoDto productoEntityToDto(Producto producto);
    Producto productoDtoToEntity(ProductoDto productoDto);
    ProductoToSaveDto productoEntityToSaveDto(Producto producto);
    Producto productoToSaveDtoToEntity(ProductoToSaveDto productoToSaveDto);
}
