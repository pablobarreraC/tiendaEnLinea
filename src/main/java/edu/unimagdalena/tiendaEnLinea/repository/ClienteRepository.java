package edu.unimagdalena.tiendaEnLinea.repository;

import edu.unimagdalena.tiendaEnLinea.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    /*
    List<Cliente> findByEmail(String email);
    List<Cliente> findByDireccion(String direccion);
    List<Cliente> findByNameStartingWith(String nombre);

     */
}
