package edu.unimagdalena.tiendaEnLinea.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "productos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double price;
    private Integer stock;

    @OneToMany(mappedBy = "productoId",orphanRemoval = true)
    private List<ItemPedido> itemsPedido;
}
