# Ejercicio 2: Biblioteca y Libros

## 📋 Descripción General

Sistema de gestión de biblioteca que permite registrar libros y sus autores. Implementa una **relación de composición 1 a N** donde una Biblioteca contiene múltiples Libros, y cada Libro pertenece obligatoriamente a una Biblioteca. Si la Biblioteca se elimina, también se eliminan sus Libros.

---

## 🎯 Objetivos de Aprendizaje

- Comprender la **composición 1 a N** entre clases
- Diferenciar composición de asociación simple
- Reforzar el manejo de colecciones dinámicas (`ArrayList`)
- Practicar métodos de búsqueda, filtrado y eliminación
- Implementar relaciones entre múltiples clases (Biblioteca-Libro-Autor)
- Mejorar la modularidad aplicando POO

---

## 📁 Estructura de Archivos

```
Ejercicio2_Biblioteca/
│
├── Autor.java                 → Clase para autores
├── Libro.java                 → Clase para libros
├── Biblioteca.java            → Gestor de la biblioteca (composición)
├── Main_Ejercicio2.java       → Programa principal
└── README_Ejercicio2.md       → Este archivo
```

---

## 🔧 Componentes del Sistema

### 1. **Autor.java** (Clase)

**Propósito:** Representa un autor de libros con información básica.

**Atributos:**
- `id` (String) - Identificador único del autor
- `nombre` (String) - Nombre completo del autor
- `nacionalidad` (String) - Nacionalidad del autor

**Métodos:**
- Constructor completo
- Getters para todos los atributos
- `mostrarInfo()` - Muestra datos del autor

**Características:**
- Clase independiente que puede ser reutilizada
- Encapsulamiento completo
- Sin dependencias con otras clases del dominio

---

### 2. **Libro.java** (Clase)

**Propósito:** Representa un libro con toda su información bibliográfica.

**Atributos:**
- `isbn` (String) - Identificador único internacional
- `titulo` (String) - Título del libro
- `anioPublicacion` (int) - Año de publicación
- `autor` (Autor) - Referencia al autor del libro

**Métodos:**
- Constructor completo
- Getters para todos los atributos
- `mostrarInfo()` - Muestra datos del libro incluyendo autor

**Relaciones:**
- **Asociación simple** con Autor (un Libro tiene un Autor)
- **Composición** con Biblioteca (el Libro pertenece a la Biblioteca)

---

### 3. **Biblioteca.java** (Clase Gestora)

**Propósito:** Gestiona la colección de libros implementando composición.

**Atributos:**
- `nombre` (String) - Nombre de la biblioteca
- `List<Libro> libros` - Colección de libros (composición)

**Métodos implementados:**

| Método | Descripción | Tipo de relación |
|--------|-------------|------------------|
| `agregarLibro(params...)` | Crea y añade un libro | Composición |
| `listarLibros()` | Muestra todos los libros | - |
| `buscarLibroPorIsbn(String)` | Busca por ISBN | - |
| `eliminarLibro(String)` | Elimina un libro | Composición |
| `obtenerCantidadLibros()` | Cuenta los libros | - |
| `filtrarLibrosPorAnio(int)` | Filtra por año | - |
| `mostrarAutoresDisponibles()` | Lista autores únicos | - |

**Características clave:**
- ✓ Los libros se **crean dentro** de la biblioteca (`agregarLibro` crea el objeto `Libro`)
- ✓ La biblioteca es **responsable del ciclo de vida** de sus libros
- ✓ Al eliminar un libro, este **deja de existir completamente**
- ✓ Uso de `HashSet` para evitar duplicados al mostrar autores

---

## 🔗 Tipos de Relaciones

### Composición 1 a N (Biblioteca → Libro)

```
┌─────────────────┐
│   Biblioteca    │
│      (1)        │  POSEE (composición fuerte)
├─────────────────┤  ◆━━━━━━━━━━━━━━━━━━━━━━━━┓
│ - nombre        │                           ┃
│ - libros        │                           ┃
│                 │                           ┃
│ +agregarLibro() │  Crea el libro aquí       ┃
│ +eliminarLibro()│  Destruye el libro aquí   ┃
└─────────────────┘                           ┃
                                              ┃
                          ┌───────────────────▼────┐
                          │       Libro            │
                          │        (N)             │
                          ├────────────────────────┤
                          │ - isbn                 │
                          │ - titulo               │
                          │ - anioPublicacion      │
                          │ - autor                │────┐
                          │                        │    │
                          │ + mostrarInfo()        │    │
                          └────────────────────────┘    │
                                                        │ Asociación
                                 ┌──────────────────────┘
                                 │
                          ┌──────▼──────────┐
                          │     Autor       │
                          ├─────────────────┤
                          │ - id            │
                          │ - nombre        │
                          │ - nacionalidad  │
                          └─────────────────┘
```

**Diferencias clave:**

| Aspecto | Composición (Biblioteca-Libro) | Asociación (Libro-Autor) |
|---------|--------------------------------|--------------------------|
| **Creación** | El contenedor crea el objeto | Se pasa por parámetro |
| **Ciclo de vida** | Dependiente del contenedor | Independiente |
| **Eliminación** | Se destruye con el contenedor | Sigue existiendo |
| **Responsabilidad** | El contenedor es dueño | Solo referencia |

---

## 🚀 Cómo Ejecutar

### Compilación y ejecución:

```bash
# Compilar todos los archivos
javac Autor.java
javac Libro.java
javac Biblioteca.java
javac Main_Ejercicio2.java

# Ejecutar el programa principal
java Main_Ejercicio2
```

### Usando IDE:
1. Crear un nuevo proyecto Java
2. Copiar los 4 archivos `.java` al proyecto
3. Ejecutar `Main_Ejercicio2.java`

---

## 📊 Tareas Implementadas

| # | Tarea | Estado |
|---|-------|--------|
| 1 | Crear una biblioteca | ✅ |
| 2 | Crear al menos 3 autores | ✅ |
| 3 | Agregar 5 libros a la biblioteca | ✅ |
| 4 | Listar todos los libros con información del autor | ✅ |
| 5 | Buscar un libro por ISBN | ✅ |
| 6 | Filtrar libros por año específico | ✅ |
| 7 | Eliminar un libro y listar restantes | ✅ |
| 8 | Mostrar cantidad total de libros | ✅ |
| 9 | Listar todos los autores disponibles | ✅ |

---

## 💡 Conceptos Clave Aplicados

### 1. **Composición Fuerte**

```java
// La biblioteca CREA el libro internamente
public void agregarLibro(String isbn, String titulo, int anio, Autor autor) {
    Libro libro = new Libro(isbn, titulo, anio, autor);  // Creación interna
    libros.add(libro);
}
```

**Ventajas:**
- Control total sobre el ciclo de vida
- Encapsulamiento mejorado
- Facilita la gestión de memoria

### 2. **Asociación Simple**

```java
// El libro solo REFERENCIA al autor (no lo crea)
public class Libro {
    private Autor autor;  // El autor existe independientemente
    
    public Libro(..., Autor autor) {
        this.autor = autor;  // Solo guarda la referencia
    }
}
```

### 3. **Eliminación de Duplicados con HashSet**

```java
Set<String> autoresUnicos = new HashSet<>();

for (Libro libro : libros) {
    String idAutor = libro.getAutor().getId();
    if (!autoresUnicos.contains(idAutor)) {
        autoresUnicos.add(idAutor);
        libro.getAutor().mostrarInfo();
    }
}
```

### 4. **Uso de List en lugar de ArrayList**

```java
// Mejor práctica: programar contra la interfaz
private List<Libro> libros;  // Interfaz List

// En el constructor:
this.libros = new ArrayList<>();  // Implementación concreta
```

**Ventajas:**
- Mayor flexibilidad
- Facilita cambios futuros
- Sigue el principio de inversión de dependencias

---

## 📈 Ejemplo de Salida

```
╔══════════════════════════════════════════════════════════════════════╗
║       TRABAJO PRÁCTICO 6 - EJERCICIO 2: BIBLIOTECA Y LIBROS         ║
╚══════════════════════════════════════════════════════════════════════╝

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
  TAREA 3: Agregando libros a la biblioteca
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

✓ Libro agregado: Cien Años de Soledad
✓ Libro agregado: El Aleph
...
```

---

## 🎓 Comparación: Composición vs Asociación

### Escenario 1: Composición (Biblioteca-Libro)
```java
Biblioteca biblioteca = new Biblioteca("UTN");
biblioteca.agregarLibro("ISBN123", "Título", 2020, autor);
// El libro se crea DENTRO de la biblioteca

biblioteca.eliminarLibro("ISBN123");
// El libro se destruye completamente
```

### Escenario 2: Asociación (Libro-Autor)
```java
Autor autor = new Autor("A001", "García Márquez", "Colombiana");
Libro libro = new Libro("ISBN123", "Título", 2020, autor);
// El autor existe independientemente

libro = null;  // El libro desaparece
// Pero el autor SIGUE EXISTIENDO
```

---

## ✅ Conclusiones del Ejercicio

Al completar este ejercicio, se logra:

- ✓ Comprender la **composición 1 a N** entre Biblioteca y Libro
- ✓ Diferenciar composición de asociación simple
- ✓ Reforzar el manejo de **colecciones dinámicas** (ArrayList/List)
- ✓ Practicar el uso de **métodos de búsqueda, filtrado y eliminación**
- ✓ Implementar relaciones entre múltiples clases
- ✓ Mejorar la **modularidad** aplicando el paradigma POO
- ✓ Usar estructuras de datos como **HashSet** para eliminar duplicados

---

## 🔍 Preguntas de Reflexión

1. **¿Qué pasaría si eliminamos la biblioteca?**
    - En composición: todos los libros se destruyen
    - Los autores seguirían existiendo (asociación)

2. **¿Por qué `agregarLibro()` crea el objeto Libro internamente?**
    - Porque es composición: la biblioteca es responsable del ciclo de vida

3. **¿Por qué el Autor se pasa por parámetro?**
    - Porque es asociación: el autor existe independientemente del libro

4. **¿Cuándo usar composición vs asociación?**
    - **Composición**: cuando el objeto "parte" no tiene sentido sin el "todo"
    - **Asociación**: cuando los objetos pueden existir independientemente

---

## 📚 Referencias

- Documentación proporcionada por la Universidad Tecnológica Nacional.
- [Java Collections Framework](https://docs.oracle.com/javase/8/docs/technotes/guides/collections/overview.html)
- [Composition vs Aggregation](https://www.baeldung.com/java-composition-aggregation-association)
- [HashSet in Java](https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html)

---

**Autor:** Renzo Calcatelli - UTN - Programación II  
**Versión:** 1.0  
**Fecha:** 2025