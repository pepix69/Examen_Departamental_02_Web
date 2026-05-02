package com.gym.system.controllers;

import com.gym.system.entities.Venta;
import com.gym.system.services.ProductoService;
import com.gym.system.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.listarTodas());
        return "ventas/lista";
    }

    @GetMapping("/nueva")
    public String nuevaForm(Model model) {
        model.addAttribute("venta", new Venta());
        return "ventas/nueva";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Venta venta, RedirectAttributes attrs) {
        Venta saved = ventaService.guardar(venta);
        attrs.addFlashAttribute("success", "Venta creada. Ahora agrega productos/servicios.");
        return "redirect:/ventas/detalle/" + saved.getId();
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        ventaService.buscarPorId(id).ifPresent(v -> model.addAttribute("venta", v));
        model.addAttribute("productos", productoService.listarActivos());
        return "ventas/detalle";
    }

    @PostMapping("/agregar-item")
    public String agregarItem(@RequestParam Long ventaId,
                              @RequestParam Long productoId,
                              @RequestParam Integer cantidad,
                              RedirectAttributes attrs) {
        ventaService.agregarItem(ventaId, productoId, cantidad);
        attrs.addFlashAttribute("success", "Producto/Servicio agregado a la venta");
        return "redirect:/ventas/detalle/" + ventaId;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes attrs) {
        ventaService.eliminar(id);
        attrs.addFlashAttribute("success", "Venta eliminada");
        return "redirect:/ventas";
    }
}
