package com.gym.system.controllers;

import com.gym.system.services.ProductoService;
import com.gym.system.services.UsuarioService;
import com.gym.system.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/inicio")
    public String inicio(Model model) {
        model.addAttribute("totalProductos", productoService.contar());
        model.addAttribute("totalVentas", ventaService.contar());
        model.addAttribute("totalUsuarios", usuarioService.contarUsuarios());
        model.addAttribute("montoVentas", ventaService.totalVentas());
        model.addAttribute("ultimasVentas", ventaService.listarTodas().stream().limit(5).toList());
        return "inicio";
    }
}
