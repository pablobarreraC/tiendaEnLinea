package edu.unimagdalena.tiendaEnLinea.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "itemsPedido")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "idPedido",nullable = false)
    private Pedido pedidoId;

    @ManyToOne @JoinColumn(name = "idProducto",nullable = false)
    private Producto productoId;

    private Integer cantidad;
    private Double precioUnitario;
}
