package edu.unimagdalena.tiendaEnLinea.dto.producto;
import java.util.Collections;
import java.util.List;

public record ProductoDto(
    Long id,
    String nombre,
    Double price,
    Integer stock
) {}
