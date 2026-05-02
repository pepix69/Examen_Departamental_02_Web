-- ===== DATOS INICIALES =====
-- Usuario admin (password: admin123)
INSERT IGNORE INTO usuarios (nombre_completo, email, password, rol)
VALUES ('Administrador', 'admin@gym.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTpOSKGe9Uy', 'ADMIN');

-- Productos/Servicios del gimnasio
INSERT IGNORE INTO productos (nombre, descripcion, precio, tipo, stock) VALUES
('Membresía Mensual', 'Acceso ilimitado al gimnasio por 1 mes', 500.00, 'SERVICIO', 999),
('Membresía Trimestral', 'Acceso ilimitado al gimnasio por 3 meses', 1300.00, 'SERVICIO', 999),
('Clase de Yoga', 'Sesión de yoga de 60 minutos', 120.00, 'SERVICIO', 30),
('Clase de Spinning', 'Sesión de spinning de 45 minutos', 100.00, 'SERVICIO', 20),
('Clase de Zumba', 'Sesión de zumba de 60 minutos', 90.00, 'SERVICIO', 25),
('Guantes de Box', 'Guantes profesionales de boxeo', 350.00, 'PRODUCTO', 15),
('Cuerda para saltar', 'Cuerda profesional de velocidad', 150.00, 'PRODUCTO', 30),
('Proteína Whey 1kg', 'Suplemento proteico de alta calidad', 850.00, 'PRODUCTO', 20),
('Asesoría Nutricional', 'Consulta con nutriólogo deportivo', 400.00, 'SERVICIO', 999),
('Entrenamiento Personal', 'Sesión personalizada con trainer certificado', 600.00, 'SERVICIO', 999);
