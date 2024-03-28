package edu.unimagdalena.tiendaEnLinea.repository;

import edu.unimagdalena.tiendaEnLinea.entity.Cliente;
import edu.unimagdalena.tiendaEnLinea.entity.Pedido;
import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {

    List<Pedido> findByFechaPedidoBetween(LocalDateTime fechaInicial, LocalDateTime fechaFinal);
    List<Pedido> findByClienteIdAndStatusIs(Cliente cliente, StatusPedido status);
    @Query(value="SELECT p FROM Pedido p JOIN FETCH p.itemsPedido WHERE p.clienteId=:clienteId")
    List<Pedido> findPedidoAndItemsPedidoByClienteId(Cliente clienteId);
}
