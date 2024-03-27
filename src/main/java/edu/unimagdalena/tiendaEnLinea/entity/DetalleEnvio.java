package edu.unimagdalena.tiendaEnLinea.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detallesEnvio")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetalleEnvio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne @JoinColumn(name = "idPedido",nullable = false)
    private Pedido pedidoId;

    private String direccion;
    private String transportadora;
    @Column(unique = true)
    private String numeroGuia;
}
