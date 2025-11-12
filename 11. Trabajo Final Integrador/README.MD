# 🔐 Sistema de Gestión de Usuarios y Credenciales de Acceso

<div align="center">
  
![Java](https://img.shields.io/badge/Java-21_LTS-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-Connectivity-007396?style=for-the-badge)
![Architecture](https://img.shields.io/badge/Architecture-Layered-2ECC71?style=for-the-badge)
![Security](https://img.shields.io/badge/Security-SHA--256-E74C3C?style=for-the-badge)
![UTN](https://img.shields.io/badge/UTN-TUPaD-0066CC?style=for-the-badge)

</div>

---

## 📖 Descripción

Este repositorio contiene el **Trabajo Final Integrador** de la materia **Programación II** de la **Tecnicatura Universitaria en Programación a Distancia (UTN)**.

El proyecto implementa un **sistema completo de gestión de usuarios y credenciales** con arquitectura en capas, demostrando el dominio de:

- Relación 1:1 unidireccional en Java y SQL
- Arquitectura en capas (Config, Entities, DAO, Service, Main)
- Gestión de transacciones ACID con JDBC
- Patrón DAO para persistencia
- Seguridad con hash SHA-256 + salt
- Validaciones de negocio robustas
- Manejo profesional de excepciones

---

## 🎯 Objetivo general

* Implementar una **relación 1:1 unidireccional** entre Usuario y CredencialAcceso tanto en el modelo de objetos como en base de datos.
* Aplicar **arquitectura en capas** para separar responsabilidades y facilitar el mantenimiento.
* Gestionar **transacciones ACID** con control de commit/rollback para garantizar integridad de datos.
* Implementar el **patrón DAO** para abstraer la lógica de acceso a datos.
* Aplicar **principios SOLID** y buenas prácticas de diseño orientado a objetos.
* Desarrollar un sistema **seguro y robusto** con validaciones exhaustivas y manejo de errores.
* Demostrar el uso profesional de **JDBC** sin frameworks ORM.

---

## 📚 Contenido del trabajo

El TFI se estructura en componentes claramente definidos que trabajan en conjunto:

### 🏗️ **Arquitectura del Sistema**

#### **Capa de Configuración (config)**
- **Clase:** `DatabaseConfig`
- **Conceptos:** Singleton, gestión de conexiones
- **Demuestra:** Centralización de recursos compartidos, propiedades de conexión

#### **Capa de Entidades (entities)**
- **Clases:** `Usuario`, `CredencialAcceso`
- **Conceptos:** POJOs, encapsulación, relación 1:1 unidireccional
- **Demuestra:** Modelo de dominio limpio con baja lógica

#### **Capa de Acceso a Datos (dao)**
- **Interfaces:** `GenericDao<T>`
- **Clases:** `UsuarioDao`, `CredencialAccesoDao`
- **Conceptos:** Patrón DAO, operaciones CRUD, mapeo objeto-relacional
- **Demuestra:** Abstracción de persistencia, PreparedStatement, manejo de ResultSet

#### **Capa de Lógica de Negocio (service)**
- **Interfaces:** `GenericService<T>`
- **Clases:** `UsuarioService`, `CredencialAccesoService`
- **Conceptos:** Transacciones, validaciones, reglas de negocio
- **Demuestra:** Orquestación de DAOs, control transaccional, hash de contraseñas

#### **Capa de Presentación (main)**
- **Clases:** `Main`, `AppMenu`
- **Conceptos:** Interfaz de usuario, manejo de entrada/salida
- **Demuestra:** Menús interactivos, validación de entrada del usuario

### 🗄️ **Modelo de Base de Datos**

#### **Tabla usuario**
```sql
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    eliminado BOOLEAN DEFAULT FALSE,
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

#### **Tabla credencial_acceso**
```sql
CREATE TABLE credencial_acceso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(64) NOT NULL,
    salt VARCHAR(32) NOT NULL,
    intentos_fallidos INT DEFAULT 0,
    ultimo_intento DATETIME,
    eliminado BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### 🔐 **Sistema de Seguridad**

#### **Hash de Contraseñas**
- **Algoritmo:** SHA-256
- **Salt:** 16 bytes aleatorios por usuario (SecureRandom)
- **Demuestra:** Almacenamiento seguro, prevención de rainbow tables

#### **Control de Acceso**
- **Máximo:** 5 intentos fallidos
- **Bloqueo:** Temporal de 15 minutos
- **Registro:** Timestamp del último intento

#### **Validación de Contraseñas**
- Mínimo 8 caracteres
- Al menos 1 mayúscula
- Al menos 1 minúscula  
- Al menos 1 número

---

## 📋 Archivos principales
```
📁 proyecto/
│
├── 📂 src/
│   └── 📂 main/
│       └── 📂 java/
│           └── 📂 com/
│               └── 📂 utn/
│                   └── 📂 programacion2/
│                       │
│                       ├── 📂 config/
│                       │   └── 📄 DatabaseConfig.java
│                       │
│                       ├── 📂 entities/
│                       │   ├── 📄 Usuario.java
│                       │   └── 📄 CredencialAcceso.java
│                       │
│                       ├── 📂 dao/
│                       │   ├── 📄 UsuarioDao.java
│                       │   └── 📄 CredencialAccesoDao.java
│                       │
│                       ├── 📂 service/
│                       │   ├── 📄 UsuarioService.java
│                       │   └── 📄 CredencialAccesoService.java
│                       │
│                       └── 📂 main/
│                           └── 📄 Main.java
│
├── 📂 database/
│   ├── 📄 01_crear_base_datos.sql
│   └── 📄 02_datos_prueba.sql
│
├── 📂 docs/
│   ├── 📄 TFI_Documentacion.pdf
│   └── 🖼️ Diagrama_UML.png
│
└── 📄 README.md
```

---

## 🔍 Conceptos clave aplicados

| Concepto | Archivos | Descripción |
|----------|----------|-------------|
| **Relación 1:1 Unidireccional** | Usuario, CredencialAcceso | Usuario conoce sus credenciales, pero no al revés |
| **Patrón DAO** | GenericDao, UsuarioDao, CredencialAccesoDao | Abstracción de operaciones de persistencia |
| **Arquitectura en Capas** | config, entities, dao, service, main | Separación clara de responsabilidades |
| **Transacciones ACID** | UsuarioService, CredencialAccesoService | Control de commit/rollback manual |
| **PreparedStatement** | Todos los DAOs | Prevención de SQL Injection |
| **Foreign Key Única** | credencial_acceso.usuario_id UNIQUE | Garantía de relación 1:1 a nivel BD |
| **Hash + Salt** | UsuarioService.hashPassword() | Seguridad de contraseñas |
| **Baja Lógica** | Campo 'eliminado' | Preservación de datos para auditoría |
| **Principios SOLID** | Todo el sistema | SRP, OCP, DIP, ISP |
| **Validaciones** | Capa Service | Reglas de negocio centralizadas |

---

## 💡 Análisis de decisiones técnicas

### Relación 1:1 - FK Única vs. PK Compartida

| Aspecto | FK Única (Implementada) | PK Compartida (Alternativa) |
|---------|------------------------|----------------------------|
| **Estructura** | usuario_id UNIQUE + FK | credencial_acceso.id = usuario.id |
| **IDs** | Independientes | Acoplados |
| **Flexibilidad** | ✅ Alta | ⚠️ Baja |
| **Integridad 1:1** | ✅ UNIQUE garantiza | ✅ Absoluta por diseño |
| **Migraciones** | ✅ Fáciles | ⚠️ Complejas |
| **Cuándo usar** | Mayoría de casos | Cuando IDs compartidos son ventaja |

### Arquitectura en Capas

| Capa | Responsabilidad | Acoplamiento | Testeable |
|------|----------------|--------------|-----------|
| **Config** | Conexiones, propiedades | Bajo | Sí |
| **Entities** | Modelo de dominio | Ninguno | Sí |
| **DAO** | Persistencia SQL | Config | Sí (con mock DB) |
| **Service** | Lógica de negocio | DAO | Sí |
| **Main** | Interfaz usuario | Service | Parcial |

### Transacciones - Orden de Operaciones

| Operación | Orden Correcto | Razón |
|-----------|----------------|-------|
| **Creación** | 1. Usuario → 2. Credencial | FK requiere que usuario exista |
| **Actualización** | 1. Validar → 2. Usuario → 3. Credencial | Verificar existencia antes de modificar |
| **Eliminación Lógica** | 1. Usuario → 2. Credencial | Marcar ambas como eliminadas |
| **Eliminación Física** | CASCADE automático | Usuario se elimina, credencial también |

---

## 🚀 Instalación y uso

### Prerrequisitos

- **Java JDK 21 LTS** o superior
- **MySQL 8.0** o superior
- **Maven** (opcional)
- **MySQL Connector/J 8.0.33**

### Configuración

#### 1️⃣ Clonar repositorio
```bash
git clone https://github.com/usuario/sistema-usuarios-credenciales.git
cd sistema-usuarios-credenciales
```

#### 2️⃣ Crear base de datos
```bash
mysql -u root -p
```
```sql
CREATE DATABASE gestion_usuarios;
USE gestion_usuarios;
SOURCE database/schema.sql;
```

#### 3️⃣ Configurar conexión
Editar `DatabaseConfig.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/gestion_usuarios";
private static final String USER = "tu_usuario";
private static final String PASSWORD = "tu_contraseña";
```

#### 4️⃣ Compilar y ejecutar
```bash
# Compilar
javac -d bin -cp "lib/*" src/main/java/com/utn/programacion2/**/*.java

# Ejecutar
java -cp "bin:lib/*" com.utn.programacion2.main.Main
```

### Menú Principal
```
═══════════════════════════════════════════════════
              MENÚ PRINCIPAL
═══════════════════════════════════════════════════

 1. Gestión de Usuarios
 2. Gestión de Credenciales
 3. Autenticación / Login
 0. Salir

═══════════════════════════════════════════════════
```

---

## ✅ Pruebas realizadas

### Resultados de Testing

| Funcionalidad | Casos Probados | Estado |
|--------------|----------------|--------|
| **Crear Usuario** | Válido, email duplicado, campos vacíos, email inválido | ✅ Exitoso |
| **Crear Usuario + Credencial** | Transacción completa, rollback, duplicados, contraseña débil | ✅ Exitoso |
| **Actualizar Usuario** | Normal, ID inexistente, email a uno existente, campos inválidos | ✅ Exitoso |
| **Eliminar Usuario** | Lógica, física, CASCADE, recuperar eliminado | ✅ Exitoso |
| **Validar Login** | Correctas, incorrectas, usuario inexistente, 5+ intentos | ✅ Exitoso |
| **Búsquedas** | Por ID, email, username, listar todos | ✅ Exitoso |
| **Relación 1:1** | Verificación de unicidad, intentos de duplicación | ✅ Exitoso |
| **Transacciones** | Commit exitoso, rollback por error | ✅ Exitoso |

### Consultas de Verificación

#### Verificar relación 1:1
```sql
SELECT u.id, COUNT(c.id) as cant_credenciales
FROM usuario u
LEFT JOIN credencial_acceso c ON u.id = c.usuario_id
GROUP BY u.id
HAVING cant_credenciales > 1;
-- Debe retornar 0 filas
```

#### Usuarios bloqueados
```sql
SELECT u.nombre, u.email, c.intentos_fallidos, c.ultimo_intento
FROM usuario u
JOIN credencial_acceso c ON u.id = c.usuario_id
WHERE c.intentos_fallidos >= 5;
```

#### Auditoría de eliminados
```sql
SELECT 'Usuario' as tipo, id, nombre, 'Eliminado' as estado
FROM usuario WHERE eliminado = TRUE
UNION ALL
SELECT 'Credencial', id, nombre_usuario, 'Eliminado'
FROM credencial_acceso WHERE eliminado = TRUE;
```

---

## 📊 Patrones y principios de diseño

### Principios SOLID Aplicados

| Principio | Aplicación | Ejemplo |
|-----------|-----------|----------|
| **SRP** | Una responsabilidad por clase | DAO solo persiste, Service solo valida |
| **OCP** | Extensible sin modificar | GenericDao extensible para nuevas entidades |
| **LSP** | Implementaciones intercambiables | Cualquier DAO cumple el contrato GenericDao |
| **ISP** | Interfaces específicas | GenericDao solo métodos CRUD esenciales |
| **DIP** | Dependencia de abstracciones | Service depende de interfaces, no clases |

### Patrones Implementados

- ✅ **DAO Pattern:** Separación de lógica de acceso a datos
- ✅ **Service Layer:** Centralización de lógica de negocio  
- ✅ **Singleton:** DatabaseConfig para gestión de conexiones
- ✅ **Template Method:** GenericDao y GenericService con métodos comunes
- ✅ **Layered Architecture:** Separación en 5 capas distintas

### Flujo de una Operación Típica
```
Usuario (Main) 
    ↓
AppMenu.crearUsuario()
    ↓
UsuarioService.crear()
    ├── Validaciones
    ├── setAutoCommit(false)
    ├── UsuarioDao.crear(conn)
    ├── Commit / Rollback
    └── Return Usuario
```

---

## 🔐 Características de seguridad

### Implementaciones de Seguridad

#### ✅ Hash de Contraseñas
```java
String salt = generarSalt();           // 16 bytes aleatorios
String hash = hashPassword(pwd, salt); // SHA-256
credencial.setSalt(salt);
credencial.setPasswordHash(hash);
```

#### ✅ Prevención SQL Injection
```java
String sql = "SELECT * FROM usuario WHERE email = ?";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setString(1, email);  // Parametrizado, seguro
```

#### ✅ Control de Intentos Fallidos
```java
if (intentosFallidos >= 5) {
    long minutos = Duration.between(ultimoIntento, now).toMinutes();
    if (minutos < 15) {
        throw new SecurityException("Cuenta bloqueada temporalmente");
    }
}
```

#### ✅ Validación de Contraseñas
- Mínimo 8 caracteres
- Al menos 1 mayúscula
- Al menos 1 minúscula
- Al menos 1 número

---

## 💻 Stack tecnológico

| Tecnología | Versión | Uso |
|-----------|---------|-----|
| ![Java](https://img.shields.io/badge/Java-21_LTS-ED8B00?logo=openjdk) | 21 LTS | Lenguaje principal |
| ![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql) | 8.0 | Base de datos |
| ![JDBC](https://img.shields.io/badge/JDBC-8.0.33-007396) | 8.0.33 | Conectividad |
| ![Git](https://img.shields.io/badge/Git-2.x-F05032?logo=git) | 2.x | Control de versiones |
| ![IntelliJ](https://img.shields.io/badge/IntelliJ-Latest-000000?logo=intellij-idea) | Latest | IDE desarrollo |

---

## 🚀 Mejoras futuras

- [ ] **Interfaz gráfica:** Migrar a JavaFX/Swing
- [ ] **Pool de conexiones:** Implementar HikariCP
- [ ] **Sistema de roles:** Admin, usuario, guest con permisos granulares
- [ ] **Autenticación MFA:** Segundo factor vía email/TOTP
- [ ] **API RESTful:** Exponer con Spring Boot
- [ ] **Testing automatizado:** JUnit 5 + Mockito
- [ ] **Logging profesional:** SLF4J + Logback
- [ ] **Recuperación de contraseña:** Sistema de tokens vía email
- [ ] **Migración a ORM:** Evaluar JPA/Hibernate
- [ ] **Tokens de sesión:** JWT con expiración automática

---

## ✅ Conclusiones

Este Trabajo Final Integrador permitió consolidar los conocimientos fundamentales de Programación II:

### Logros Técnicos
* **Relación 1:1 unidireccional:** Implementación completa en Java y SQL con comprensión profunda de las alternativas de diseño (FK única vs PK compartida)
* **Arquitectura en capas:** Separación exitosa de responsabilidades siguiendo principios SOLID, facilitando mantenimiento y testing
* **Transacciones ACID:** Gestión manual con JDBC demostrando control total sobre commit/rollback y manejo de errores
* **Patrón DAO:** Abstracción efectiva de la lógica de persistencia con interfaces genéricas reutilizables
* **Seguridad robusta:** Hash SHA-256 + salt, PreparedStatement, validaciones exhaustivas y control de intentos fallidos

### Competencias Desarrolladas
* **Diseño orientado a objetos:** Aplicación práctica de herencia, polimorfismo, encapsulación e interfaces
* **Gestión de persistencia:** Dominio de JDBC sin frameworks, comprendiendo el funcionamiento interno del mapeo objeto-relacional
* **Manejo de errores:** Try-catch-finally estratégico en todas las capas con mensajes descriptivos
* **Validaciones de negocio:** Reglas centralizadas en la capa Service con excepciones personalizadas
* **Buenas prácticas:** Código limpio, documentado y siguiendo convenciones profesionales

### Aplicación Profesional
El sistema desarrollado demuestra capacidad para crear **aplicaciones empresariales robustas y escalables**:
- Arquitectura mantenible y extensible
- Código desacoplado y testeable
- Seguridad a nivel producción
- Gestión profesional de errores
- Documentación completa

Estos conocimientos constituyen la base fundamental para el desarrollo de software profesional y son directamente aplicables en entornos de producción reales.

---

## 👥 Equipo de desarrollo

| Integrante | Legajo | Email | Rol |
|------------|--------|-------|-----|
| Pablo Basualdo Arcati | 4822 | pbasualdoarcati@gmail.com | Desarrollador Full-Stack / Tester |
| Renzo Calcatelli | 5721 | rcalcatelli@gmail.com | Desarrollador Full-Stack / Investigación |
| Dante Kaddarian | 7589 | dantek91@yahoo.com | Desarrollador Full-Stack / Tester |

**Comisión:** M2025-1  
**Materia:** Programación II  
**Profesor:** Ariel Enferrel

---

## 📚 Bibliografía

- Oracle. (2024). *Java SE 21 Documentation*. https://docs.oracle.com/en/java/javase/21/
- Oracle. (2024). *JDBC API Documentation*. https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/
- MySQL. (2024). *MySQL 8.0 Reference Manual*. https://dev.mysql.com/doc/refman/8.0/en/
- Fowler, M. (2002). *Patterns of Enterprise Application Architecture*. Addison-Wesley Professional
- Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994). *Design Patterns: Elements of Reusable Object-Oriented Software*. Addison-Wesley Professional
- Universidad Tecnológica Nacional. (2024). *Programación II - Material de Cátedra*

---

## 🤝 Uso de IA

Durante el desarrollo se utilizó asistencia de **Claude (Anthropic)** para:
- Consultas sobre mejores prácticas de Java y SQL
- Revisión de sintaxis y patrones de diseño
- Sugerencias de estructura arquitectónica
- Generación de diagramas UML
- Asistencia en documentación técnica

**Nota importante:** La IA se utilizó como herramienta de consulta y aprendizaje. Todo el código fue escrito, comprendido, probado y validado personalmente por el equipo de desarrollo.

---

<div align="center">

**Universidad Tecnológica Nacional**  
*Tecnicatura Universitaria en Programación a Distancia*  
**Programación II - Comisión M2025-1 - 2025**

---

[![GitHub](https://img.shields.io/badge/GitHub-Repository-181717?style=for-the-badge&logo=github)](https://github.com/usuario/sistema-usuarios-credenciales)

</div>
