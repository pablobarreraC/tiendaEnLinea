package edu.unimagdalena.tiendaEnLinea.entity;

import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.StatusPedido;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name="idCliente",nullable = false)
    private Cliente clienteId;

    @OneToMany(mappedBy = "pedidoId",orphanRemoval = true)
    private List<ItemPedido> itemsPedido;

    @OneToOne(mappedBy = "pedidoId",orphanRemoval = true)
    private Pago pago;

    @OneToOne(mappedBy = "pedidoId",orphanRemoval = true)
    private DetalleEnvio detalleEnvio;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaPedido;
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
}
