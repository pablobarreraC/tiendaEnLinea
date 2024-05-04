package edu.unimagdalena.tiendaEnLinea.dto.cliente;

public record ClienteToSaveDto( 
    long id,
    String nombre,
    String email,
    String direccion
) {}
