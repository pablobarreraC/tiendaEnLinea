package edu.unimagdalena.tiendaEnLinea.dto.detalleEnvio;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;


public record DetalleEnvioDto(long id,
    String direccion,
    String transportadora,
    String numeroGuia
){}
