# 🏋️ GymSystem — Sistema de Gestión de Gimnasio

Sistema web desarrollado con **Spring Boot + Thymeleaf + AdminLTE** para gestión de gimnasios.

---

## 📁 Estructura del Proyecto

```
gym-system/
├── src/main/java/com/gym/system/
│   ├── GymSystemApplication.java         ← Clase principal
│   ├── config/
│   │   └── SecurityConfig.java           ← Configuración de seguridad
│   ├── controllers/
│   │   ├── AuthController.java           ← Login, Registro, Recuperar
│   │   ├── InicioController.java         ← Dashboard
│   │   ├── ProductoController.java       ← CRUD Productos/Servicios
│   │   ├── VentaController.java          ← CRUD Ventas
│   │   └── FacturacionController.java    ← Facturación
│   ├── entities/
│   │   ├── Usuario.java
│   │   ├── Producto.java
│   │   ├── Venta.java
│   │   └── VentaItem.java
│   ├── repositories/
│   │   ├── UsuarioRepository.java
│   │   ├── ProductoRepository.java
│   │   ├── VentaRepository.java
│   │   └── VentaItemRepository.java
│   └── services/
│       ├── UsuarioService.java
│       ├── ProductoService.java
│       └── VentaService.java
├── src/main/resources/
│   ├── templates/
│   │   ├── fragments/layout.html         ← Fragmentos reutilizables
│   │   ├── auth/
│   │   │   ├── login.html
│   │   │   ├── registro.html
│   │   │   └── recuperar.html
│   │   ├── productos/
│   │   │   ├── lista.html
│   │   │   └── formulario.html
│   │   ├── ventas/
│   │   │   ├── lista.html
│   │   │   ├── nueva.html
│   │   │   └── detalle.html
│   │   ├── facturacion/
│   │   │   ├── lista.html
│   │   │   └── invoice.html              ← Página de factura imprimible
│   │   └── inicio.html                   ← Dashboard principal
│   ├── application.properties
│   ├── schema.sql                        ← Esquema de base de datos
│   └── data.sql                          ← Datos iniciales
└── pom.xml
```

---

## ⚙️ Configuración

### 1. Base de Datos en Aiven.io

1. Crea una cuenta en [aiven.io](https://aiven.io)
2. Crea un nuevo servicio **MySQL**
3. Copia las credenciales y actualiza `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://TU_HOST.aivencloud.com:3306/TU_DB?useSSL=true&requireSSL=true
spring.datasource.username=avnadmin
spring.datasource.password=TU_PASSWORD
```

### 2. Correo para recuperación de contraseña

En Gmail:
1. Ve a tu cuenta → Seguridad → Contraseñas de aplicaciones
2. Genera una contraseña de app
3. Actualiza `application.properties`:

```properties
spring.mail.username=tucorreo@gmail.com
spring.mail.password=xxxx xxxx xxxx xxxx
```

### 3. Ejecutar localmente

```bash
mvn spring-boot:run
```

Accede en: http://localhost:8080  
Credenciales por defecto: `admin@gym.com` / `admin123`

---

## 🚀 Despliegue en Render.com

1. Sube tu proyecto a GitHub
2. Crea una cuenta en [render.com](https://render.com)
3. Nuevo → **Web Service**
4. Conecta tu repositorio de GitHub
5. Configura:
   - **Build Command:** `mvn clean package -DskipTests`
   - **Start Command:** `java -jar target/system-0.0.1-SNAPSHOT.jar`
6. Agrega las variables de entorno:
   - `SPRING_DATASOURCE_URL`
   - `SPRING_DATASOURCE_USERNAME`
   - `SPRING_DATASOURCE_PASSWORD`
   - `SPRING_MAIL_USERNAME`
   - `SPRING_MAIL_PASSWORD`

---

## 📊 Módulos del Sistema

| Módulo | URL | Descripción |
|--------|-----|-------------|
| Login | `/auth/login` | Inicio de sesión |
| Registro | `/auth/registro` | Crear cuenta |
| Recuperar | `/auth/recuperar` | Recuperar contraseña por email |
| Dashboard | `/inicio` | Estadísticas y gráficas |
| Productos | `/productos` | CRUD productos y servicios |
| Ventas | `/ventas` | Gestión de ventas |
| Facturación | `/facturacion` | Listado de facturas |
| Invoice | `/facturacion/generar/{id}` | Factura imprimible |

---

## 🛠️ Tecnologías

- **Backend:** Spring Boot 3.2, Spring Security, Spring Data JDBC
- **Frontend:** Thymeleaf, AdminLTE 3.2, Bootstrap 4, Chart.js
- **BD:** MySQL (Aiven.io)
- **Deploy:** Render.com
- **Email:** Spring Mail + Gmail SMTP
