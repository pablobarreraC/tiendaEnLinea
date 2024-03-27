package edu.unimagdalena.tiendaEnLinea.entity;

import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.MetodoPago;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pagos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne @JoinColumn(name = "idPedido",nullable = false)
    private Pedido pedidoId;

    private Double totalPago;
    @Temporal(TemporalType.DATE)
    private LocalDate fechaPago;
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;
}
