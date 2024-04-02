package edu.unimagdalena.tiendaEnLinea.dto.cliente;


import edu.unimagdalena.tiendaEnLinea.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel= MappingConstants.ComponentModel.SPRING)
public interface ClienteMapper {
    ClienteMapper instancia= Mappers.getMapper(ClienteMapper.class);

    ClienteDto clienteEntityToDto(Cliente cliente);
    Cliente clienteDtoToEntity(ClienteDto clienteDto);
    Cliente clienteToSaveDtoToEntity(ClienteToSaveDto clienteToSaveDto);
    ClienteToSaveDto clienteEntityToSaveDto(Cliente cliente);
}
