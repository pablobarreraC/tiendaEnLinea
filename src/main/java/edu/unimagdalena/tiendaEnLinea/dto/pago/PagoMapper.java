package edu.unimagdalena.tiendaEnLinea.dto.pago;

import edu.unimagdalena.tiendaEnLinea.entity.Pago;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PagoMapper {
    PagoMapper instancia= Mappers.getMapper(PagoMapper.class);

    PagoDto pagoEntityToDto(Pago pago);
    Pago pagoDtoToEntity(PagoDto pagoDto);
    PagoToSaveDto pagoEntityToSaveDto(Pago pago);
    Pago pagoToSaveDtoToEntity(PagoToSaveDto pagoToSaveDto);
}
