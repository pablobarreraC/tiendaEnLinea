package edu.unimagdalena.tiendaEnLinea.dto.producto;

public record ProductoToSaveDto(
    Long id,
    String nombre,
    Double price,
    Integer stock
) 
{}
