package edu.unimagdalena.tiendaEnLinea.repository;

import edu.unimagdalena.tiendaEnLinea.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
    List<Producto> findByNombreContainingIgnoreCase(String terminoBusqueda);

    @Query(value = "SELECT p FROM Producto p WHERE p.stock > 0")
    List<Producto> findByStockIsGreaterThanZero();

    @Query("SELECT p FROM Producto p WHERE p.price=:price AND p.stock=:stock")
    List<Producto> findByPriceLessOrEqualsThanAndStockIs(Double price, Integer stock);
}
