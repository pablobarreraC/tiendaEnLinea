package edu.unimagdalena.tiendaEnLinea.repository;

import edu.unimagdalena.tiendaEnLinea.entity.Pago;
import edu.unimagdalena.tiendaEnLinea.entity.Pedido;
import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago,Long> {
    /*
    List<Pago> findByFechaPagoBetween(LocalDate fechaInicial,LocalDate fechaFinal);
    List<Pago> findByPedidoIdAndMetodoPagoIs(Pedido pedidoId, MetodoPago metodoPago);

     */
}
