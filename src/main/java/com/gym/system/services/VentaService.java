package com.gym.system.services;

import com.gym.system.entities.Producto;
import com.gym.system.entities.Venta;
import com.gym.system.entities.VentaItem;
import com.gym.system.repositories.ProductoRepository;
import com.gym.system.repositories.VentaItemRepository;
import com.gym.system.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private VentaItemRepository ventaItemRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<Venta> listarTodas() {
        return ventaRepository.findAllByOrderByFechaVentaDesc();
    }

    public Optional<Venta> buscarPorId(Long id) {
        Optional<Venta> venta = ventaRepository.findById(id);
        venta.ifPresent(v -> {
            List<VentaItem> items = ventaItemRepository.findByVentaId(v.getId());
            items.forEach(item -> {
                productoRepository.findById(item.getProductoId())
                    .ifPresent(item::setProducto);
            });
            v.setItems(items);
        });
        return venta;
    }

    public Venta guardar(Venta venta) {
        return ventaRepository.save(venta);
    }

    @Transactional
    public VentaItem agregarItem(Long ventaId, Long productoId, Integer cantidad) {
        Venta venta = ventaRepository.findById(ventaId)
            .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        Producto producto = productoRepository.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        VentaItem item = new VentaItem();
        item.setVentaId(ventaId);
        item.setProductoId(productoId);
        item.setCantidad(cantidad);
        item.setPrecioUnitario(producto.getPrecio());
        item.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(cantidad)));
        ventaItemRepository.save(item);

        // Actualizar total de la venta
        List<VentaItem> items = ventaItemRepository.findByVentaId(ventaId);
        BigDecimal total = items.stream()
            .map(VentaItem::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        venta.setTotal(total);
        ventaRepository.save(venta);

        return item;
    }

    @Transactional
    public void eliminar(Long id) {
        ventaItemRepository.deleteByVentaId(id);
        ventaRepository.deleteById(id);
    }

    public long contar() {
        return ventaRepository.count();
    }

    public BigDecimal totalVentas() {
        return listarTodas().stream()
            .map(Venta::getTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
