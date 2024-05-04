package edu.unimagdalena.tiendaEnLinea.dto.pedido;

import edu.unimagdalena.tiendaEnLinea.entity.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PedidoMapper {
    PedidoMapper instancia= Mappers.getMapper(PedidoMapper.class);

    PedidoDto pedidoEntityToDto(Pedido pedido);
    Pedido pedidoDtoToEntity(PedidoDto pedidoDto);
    PedidoToSaveDto pedidoEntityToSaveDto(Pedido pedido);
    Pedido pedidoToSaveDtoToEntity(PedidoToSaveDto pedidoToSaveDto);
}
