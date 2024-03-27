package edu.unimagdalena.tiendaEnLinea.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "clientes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(unique = true)
    private String email;
    private String direccion;

    @OneToMany(mappedBy = "clienteId",orphanRemoval = true)
    private List<Pedido> pedidos;
}
