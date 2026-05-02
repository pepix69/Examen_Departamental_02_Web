package com.gym.system.repositories;

import com.gym.system.entities.VentaItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VentaItemRepository extends CrudRepository<VentaItem, Long> {
    List<VentaItem> findByVentaId(Long ventaId);
    void deleteByVentaId(Long ventaId);
}
