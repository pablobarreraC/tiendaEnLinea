package edu.unimagdalena.tiendaEnLinea.repository;

import edu.unimagdalena.tiendaEnLinea.entity.DetalleEnvio;
import edu.unimagdalena.tiendaEnLinea.entity.Pedido;
import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DetalleEnvioRepository extends JpaRepository<DetalleEnvio,Long> {
    /*
    List<DetalleEnvio> findByPedidoId(Pedido pedidoId);
    List<DetalleEnvio> findByTransportadora(String transportadora);
    @Query("SELECT d FROM DetalleEnvio d WHERE (SELECT p FROM Pedido p " +
                                                "WHERE p.detalleEnvio=d AND " +
                                                "p.status=:status")
    List<DetalleEnvio> findByStatus(StatusPedido status);

     */
}
