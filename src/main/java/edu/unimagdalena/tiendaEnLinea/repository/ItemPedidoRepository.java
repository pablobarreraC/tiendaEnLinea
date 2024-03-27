package edu.unimagdalena.tiendaEnLinea.repository;

import edu.unimagdalena.tiendaEnLinea.entity.ItemPedido;
import edu.unimagdalena.tiendaEnLinea.entity.Pedido;
import edu.unimagdalena.tiendaEnLinea.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido,Long> {
    List<ItemPedido> findByPedidoId(Pedido pedidoId);
    List<ItemPedido> findByProductoId(Product productoId);

}
