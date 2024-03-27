package edu.unimagdalena.tiendaEnLinea.repository;

import edu.unimagdalena.tiendaEnLinea.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Product,Long> {
    List<Product> findByNombreContainingIgnoreCase(String terminoBusqueda);
    @Query(value = "SELECT p FROM Product p WHERE p.stock > 0")
    List<Product> findByStockIsGreaterThanZero();
    List<Product> findByPriceLessOrEqualsThanAndStockIs(Double price,Integer stock);
}
