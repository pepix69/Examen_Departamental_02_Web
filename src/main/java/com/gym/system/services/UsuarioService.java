package com.gym.system.services;

import com.gym.system.entities.Usuario;
import com.gym.system.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JavaMailSender mailSender;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        return User.builder()
            .username(usuario.getEmail())
            .password(usuario.getPassword())
            .roles(usuario.getRol())
            .build();
    }

    public void registrar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }

    public void recuperarPassword(String email) {
        Optional<Usuario> opt = usuarioRepository.findByEmail(email);
        if (opt.isPresent()) {
            Usuario usuario = opt.get();
            String nuevaPassword = UUID.randomUUID().toString().substring(0, 8);
            usuario.setPassword(encoder.encode(nuevaPassword));
            usuarioRepository.save(usuario);

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);
            msg.setSubject("Recuperación de contraseña - GymSystem");
            msg.setText("Hola " + usuario.getNombreCompleto() + ",\n\n"
                + "Tu nueva contraseña temporal es: " + nuevaPassword + "\n\n"
                + "Por favor, cámbiala después de iniciar sesión.\n\n"
                + "Saludos,\nGymSystem");
            mailSender.send(msg);
        }
    }

    public Iterable<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public long contarUsuarios() {
        return usuarioRepository.count();
    }
}
