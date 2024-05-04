package edu.unimagdalena.tiendaEnLinea.dto.itemPedido;

import edu.unimagdalena.tiendaEnLinea.entity.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemPedidoMapper {
    ItemPedidoMapper instancia= Mappers.getMapper(ItemPedidoMapper.class);

    ItemPedidoDto itemPedidoEntityToDto(ItemPedido itemPedido);
    ItemPedido itemPedidoDtoToEntity(ItemPedidoDto itemPedidoDto);
    ItemPedidoToSaveDto itemPedidoEntityToSaveDto(ItemPedido itemPedido);
    ItemPedido itemPedidoToSaveDtoToEntity(ItemPedidoToSaveDto itemPedidoToSaveDto);
}
