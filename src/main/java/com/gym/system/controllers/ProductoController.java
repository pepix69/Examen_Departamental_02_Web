package com.gym.system.controllers;

import com.gym.system.entities.Producto;
import com.gym.system.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "productos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto, RedirectAttributes attrs) {
        try {
            productoService.guardar(producto);
            attrs.addFlashAttribute("success", "Producto/Servicio guardado correctamente");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
        }
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        productoService.buscarPorId(id).ifPresent(p -> model.addAttribute("producto", p));
        return "productos/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes attrs) {
        productoService.eliminar(id);
        attrs.addFlashAttribute("success", "Producto/Servicio eliminado");
        return "redirect:/productos";
    }
}
