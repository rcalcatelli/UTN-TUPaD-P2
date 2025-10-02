# Ejercicio 1: Sistema de Stock

## 📋 Descripción General

Sistema de gestión de inventario que permite administrar productos de una tienda, controlando su disponibilidad, precios y categorías mediante el uso de colecciones dinámicas (ArrayList) y enumeraciones (enum) en Java.

---

## 🎯 Objetivos de Aprendizaje

- Implementar colecciones dinámicas con `ArrayList`
- Utilizar enumeraciones (`enum`) con atributos y métodos
- Aplicar encapsulamiento con modificadores de acceso
- Implementar relación 1 a N (Inventario → Productos)
- Practicar operaciones CRUD (Create, Read, Update, Delete)
- Desarrollar métodos de búsqueda y filtrado
- Usar ciclo `for-each` para recorrer colecciones

---

## 📁 Estructura de Archivos

```
Ejercicio1_Stock/
│
├── CategoriaProducto.java    → Enumeración de categorías
├── Producto.java              → Clase modelo del producto
├── Inventario.java            → Gestor del inventario
├── Main_Ejercicio1.java       → Programa principal
└── README_Ejercicio1.md       → Este archivo
```

---

## 🔧 Componentes del Sistema

### 1. **CategoriaProducto.java** (Enum)

**Propósito:** Define las categorías de productos con descripciones.

**Valores:**
- `ALIMENTOS` - Productos comestibles
- `ELECTRONICA` - Dispositivos electrónicos
- `ROPA` - Prendas de vestir
- `HOGAR` - Artículos para el hogar

**Características:**
- Atributo privado `descripcion` (final)
- Constructor privado del enum
- Método `getDescripcion()` para obtener la descripción

**Conceptos aplicados:**
- Uso de enums con atributos
- Métodos en enumeraciones
- Modificador `final` para inmutabilidad

---

### 2. **Producto.java** (Clase)

**Atributos:**
- `id` (String) - Identificador único
- `nombre` (String) - Nombre del producto
- `precio` (double) - Precio en pesos
- `cantidad` (int) - Stock disponible
- `categoria` (CategoriaProducto) - Categoría del producto

**Métodos:**
- Constructor completo
- Getters para todos los atributos
- `setCantidad(int)` - Actualiza el stock
- `mostrarInfo()` - Imprime información formateada

**Conceptos aplicados:**
- Encapsulamiento (atributos privados)
- Uso de `this` para referenciar atributos de instancia
- Métodos de acceso (getters/setters)

---

### 3. **Inventario.java** (Clase Gestora)

**Atributo:**
- `ArrayList<Producto> productos` - Colección dinámica de productos

**Métodos implementados:**

| Método | Descripción | Complejidad |
|--------|-------------|-------------|
| `agregarProducto(Producto p)` | Añade un producto al inventario | O(1) |
| `listarProductos()` | Muestra todos los productos | O(n) |
| `buscarProductoPorId(String id)` | Busca por identificador | O(n) |
| `eliminarProducto(String id)` | Elimina un producto | O(n) |
| `actualizarStock(String id, int cantidad)` | Modifica el stock | O(n) |
| `filtrarPorCategoria(CategoriaProducto cat)` | Filtra por categoría | O(n) |
| `obtenerTotalStock()` | Suma total de unidades | O(n) |
| `obtenerProductoConMayorStock()` | Encuentra el mayor stock | O(n) |
| `filtrarProductosPorPrecio(double min, double max)` | Filtra por rango de precio | O(n) |
| `mostrarCategoriasDisponibles()` | Lista todas las categorías | O(1) |

**Conceptos aplicados:**
- Colecciones dinámicas (`ArrayList`)
- Ciclo `for-each` para recorrer listas
- Búsqueda lineal
- Operaciones de agregación (suma)
- Algoritmos de comparación (máximo)

---

### 4. **Main_Ejercicio1.java** (Programa Principal)

Implementa las **10 tareas requeridas** del TP:

1. ✅ Crear 5 productos con diferentes categorías
2. ✅ Listar todos los productos
3. ✅ Buscar producto por ID
4. ✅ Filtrar por categoría
5. ✅ Eliminar producto y listar restantes
6. ✅ Actualizar stock
7. ✅ Mostrar total de stock
8. ✅ Obtener producto con mayor stock
9. ✅ Filtrar por rango de precio ($1000 - $3000)
10. ✅ Mostrar categorías disponibles

---

## 🚀 Cómo Ejecutar

### Opción 1: Compilación manual

```bash
# Compilar todos los archivos
javac CategoriaProducto.java
javac Producto.java
javac Inventario.java
javac Main_Ejercicio1.java

# Ejecutar el programa principal
java Main_Ejercicio1
```

### Opción 2: Usando IDE (Eclipse, IntelliJ, NetBeans)

1. Crear un nuevo proyecto Java
2. Copiar los 4 archivos `.java` al proyecto
3. Ejecutar `Main_Ejercicio1.java`

---

## 📊 Relaciones entre Clases

```
┌─────────────────┐
│  Inventario     │
│  (1)            │
├─────────────────┤
│ - productos     │ ◆━━━━━━━━━━━━━━┓
│ + agregar()     │                ┃
│ + listar()      │                ┃ 1:N
│ + buscar()      │                ┃
│ + eliminar()    │                ┃
└─────────────────┘                ┃
                                   ┃
                                   ┃
                    ┌──────────────▼──────┐
                    │     Producto        │
                    │     (N)             │
                    ├─────────────────────┤
                    │ - id                │
                    │ - nombre            │
                    │ - precio            │──── usa ────┐
                    │ - cantidad          │             │
                    │ - categoria         │◄────────────┘
                    │ + mostrarInfo()     │
                    └─────────────────────┘
                              │
                              │ usa
                              │
                    ┌─────────▼──────────┐
                    │ CategoriaProducto  │
                    │      (enum)        │
                    ├────────────────────┤
                    │ ALIMENTOS          │
                    │ ELECTRONICA        │
                    │ ROPA               │
                    │ HOGAR              │
                    │ + getDescripcion() │
                    └────────────────────┘
```

---

## 💡 Conceptos Clave Aplicados

### 1. **Encapsulamiento**
```java
private String id;  // Atributo privado
public String getId() { return id; }  // Acceso controlado
```

### 2. **ArrayList (Colección Dinámica)**
```java
private ArrayList<Producto> productos = new ArrayList<>();
productos.add(p);  // Tamaño dinámico
```

### 3. **Enum con Métodos**
```java
public enum CategoriaProducto {
    ALIMENTOS("Productos comestibles");
    
    private final String descripcion;
    
    public String getDescripcion() {
        return descripcion;
    }
}
```

### 4. **For-Each Loop**
```java
for (Producto p : productos) {
    p.mostrarInfo();
}
```

### 5. **Búsqueda Lineal**
```java
for (Producto p : productos) {
    if (p.getId().equals(id)) {
        return p;
    }
}
```

---

## 📈 Ejemplo de Salida Esperada

```
╔══════════════════════════════════════════════════════════════════════╗
║         TRABAJO PRÁCTICO 6 - EJERCICIO 1: SISTEMA DE STOCK          ║
╚══════════════════════════════════════════════════════════════════════╝

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
  TAREA 1: Creación de productos
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

✓ Producto agregado: Arroz Integral 1kg
✓ Producto agregado: Notebook HP Pavilion
...
```

---

## ✅ Conclusiones del Ejercicio

Al completar este ejercicio, se logra:

- ✓ Comprender el uso de `this` para acceder a atributos de instancia
- ✓ Implementar enumeraciones con métodos personalizados
- ✓ Manejar colecciones dinámicas con `ArrayList`
- ✓ Aplicar encapsulamiento y modificadores de acceso
- ✓ Desarrollar métodos de búsqueda y filtrado
- ✓ Practicar operaciones CRUD básicas
- ✓ Implementar relaciones 1 a N entre clases
- ✓ Usar ciclos `for-each` para recorrer colecciones

---

## 📚 Referencias

- Documentación proporcionada por la Universidad Tecnológica Nacional.
- [Java ArrayList Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)
- [Java Enum Types](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html)
- [Encapsulation in Java](https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html)

---

**Autor:** Renzo Calcatelli UTN - Programación II  
**Versión:** 1.0  
**Fecha:** 2025