package com.gym.system.controllers;

import com.gym.system.entities.Usuario;
import com.gym.system.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                        @RequestParam(required = false) String logout,
                        Model model) {
        if (error != null) model.addAttribute("error", "Credenciales incorrectas");
        if (logout != null) model.addAttribute("logout", "Sesión cerrada exitosamente");
        return "auth/login";
    }

    @GetMapping("/registro")
    public String registroForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String registrar(@ModelAttribute Usuario usuario,
                            @RequestParam String confirmarPassword,
                            RedirectAttributes attrs) {
        if (!usuario.getPassword().equals(confirmarPassword)) {
            attrs.addFlashAttribute("error", "Las contraseñas no coinciden");
            return "redirect:/auth/registro";
        }
        try {
            usuarioService.registrar(usuario);
            attrs.addFlashAttribute("success", "Cuenta creada exitosamente. ¡Inicia sesión!");
            return "redirect:/auth/login";
        } catch (RuntimeException e) {
            attrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/auth/registro";
        }
    }

    @GetMapping("/recuperar")
    public String recuperarForm() {
        return "auth/recuperar";
    }

    @PostMapping("/recuperar")
    public String recuperar(@RequestParam String email, RedirectAttributes attrs) {
        try {
            usuarioService.recuperarPassword(email);
            attrs.addFlashAttribute("success", "Se envió una nueva contraseña a tu correo.");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", "No se pudo enviar el correo. Verifica el email.");
        }
        return "redirect:/auth/recuperar";
    }
}
