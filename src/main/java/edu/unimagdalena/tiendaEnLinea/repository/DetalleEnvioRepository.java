package edu.unimagdalena.tiendaEnLinea.repository;

import edu.unimagdalena.tiendaEnLinea.entity.DetalleEnvio;
import edu.unimagdalena.tiendaEnLinea.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleEnvioRepository extends JpaRepository<DetalleEnvio,Long> {
    List<DetalleEnvio> findByPedidoId(Pedido pedidoId);
    List<DetalleEnvio> findByTransportadora(String transportadora);
}
