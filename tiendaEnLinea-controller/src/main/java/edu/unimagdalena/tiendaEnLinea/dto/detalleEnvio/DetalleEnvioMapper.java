package edu.unimagdalena.tiendaEnLinea.dto.detalleEnvio;

import edu.unimagdalena.tiendaEnLinea.entity.DetalleEnvio;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DetalleEnvioMapper {
    DetalleEnvioMapper instancia= Mappers.getMapper(DetalleEnvioMapper.class);

    DetalleEnvioDto detalleEnvioEntityToDto(DetalleEnvio detalleEnvio);
    DetalleEnvio detalleEnvioDtoToEntity(DetalleEnvioDto detalleEnvioDto);
    DetalleEnvioToSaveDto detalleEnvioEntityToSaveDto(DetalleEnvio detalleEnvio);
    DetalleEnvio detalleEnvioToSaveDtoToEntity(DetalleEnvioToSaveDto detalleEnvioToSaveDto);
}
