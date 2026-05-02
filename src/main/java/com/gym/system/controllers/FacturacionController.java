package com.gym.system.controllers;

import com.gym.system.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/facturacion")
public class FacturacionController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.listarTodas());
        return "facturacion/lista";
    }

    @GetMapping("/generar/{id}")
    public String generarFactura(@PathVariable Long id, Model model) {
        ventaService.buscarPorId(id).ifPresent(v -> model.addAttribute("venta", v));
        return "facturacion/invoice";
    }
}
