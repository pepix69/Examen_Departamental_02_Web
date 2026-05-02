package com.gym.system.repositories;

import com.gym.system.entities.Venta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VentaRepository extends CrudRepository<Venta, Long> {
    List<Venta> findAllByOrderByFechaVentaDesc();
    long count();
}
