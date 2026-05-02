package com.gym.system.repositories;

import com.gym.system.entities.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {
    List<Producto> findByActivoTrue();
    List<Producto> findByTipoAndActivoTrue(String tipo);
}
