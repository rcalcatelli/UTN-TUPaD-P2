# Ejercicio 3: Universidad, Profesor y Curso (Bidireccional 1 a N)

## 📋 Descripción General

Sistema académico universitario que modela la relación entre Profesores y Cursos mediante una **relación bidireccional 1 a N**: un Profesor dicta muchos Cursos, y cada Curso tiene exactamente un Profesor responsable. La característica principal es que **la navegación es posible desde ambos extremos** y debe mantenerse sincronizada.

---

## 🎯 Objetivos de Aprendizaje

- Diferenciar **bidireccionalidad** de relaciones unidireccionales
- Mantener **invariantes de asociación** (coherencia de referencias)
- Implementar **sincronización automática** entre ambos lados
- Practicar colecciones (ArrayList), búsquedas y operaciones de alta/baja
- Diseñar métodos "seguros" que sincronicen los dos lados siempre
- Comprender el concepto de **navegación bidireccional**

---

## 📁 Estructura de Archivos

```
Ejercicio3_Universidad/
│
├── Profesor.java              → Lado "1" de la relación (tiene lista de cursos)
├── Curso.java                 → Lado "N" de la relación (tiene referencia a profesor)
├── Universidad.java           → Gestor de profesores y cursos
├── Main_Ejercicio3.java       → Programa principal con 8 tareas
└── README_Ejercicio3.md       → Este archivo
```

---

## 🔗 Concepto Clave: Relación Bidireccional

### ¿Qué es la bidireccionalidad?

Una relación bidireccional permite **navegar en ambas direcciones**:

```
    Profesor ←──────────→ Curso
      (1)                  (N)
```

- **Desde Profesor**: puedo acceder a todos sus Cursos (`profesor.getCursos()`)
- **Desde Curso**: puedo acceder a su Profesor (`curso.getProfesor()`)

### Invariante de Asociación

**REGLA DE ORO:** Las referencias deben ser **coherentes en ambos lados**:

```
Si curso.profesor == profesorA
Entonces profesorA.cursos debe contener curso

Y viceversa:
Si profesorA.cursos contiene curso
Entonces curso.profesor == profesorA
```

### ⚠️ Problema sin sincronización

```java
// ❌ INCORRECTO: Modificación manual sin sincronización
Profesor p = new Profesor(...);
Curso c = new Curso(...);

c.profesor = p;  // Curso apunta a profesor
// Pero p.cursos NO contiene a c → INCONSISTENCIA
```

### ✅ Solución: Sincronización automática

```java
// ✅ CORRECTO: Usar método que sincroniza ambos lados
c.setProfesor(p);
// Automáticamente:
// 1. c.profesor = p
// 2. p.cursos.add(c)
// → CONSISTENCIA GARANTIZADA
```

---

## 🔧 Componentes del Sistema

### 1. **Profesor.java** (Lado "1")

**Atributos:**
- `id` (String) - Identificador único
- `nombre` (String) - Nombre completo
- `especialidad` (String) - Área principal
- `List<Curso> cursos` - **Lista de cursos que dicta**

**Métodos críticos para bidireccionalidad:**

| Método | Responsabilidad | Sincronización |
|--------|-----------------|----------------|
| `agregarCurso(Curso c)` | Añade curso a su lista | Llama a `c.setProfesor(this)` |
| `eliminarCurso(Curso c)` | Quita curso de su lista | Llama a `c.setProfesor(null)` |

**Pseudocódigo de agregarCurso:**
```java
public void agregarCurso(Curso c) {
    if (!cursos.contains(c)) {
        cursos.add(c);                    // Lado Profesor
        if (c.getProfesor() != this) {
            c.setProfesor(this);          // Sincronizar lado Curso
        }
    }
}
```

---

### 2. **Curso.java** (Lado "N")

**Atributos:**
- `codigo` (String) - Código único
- `nombre` (String) - Nombre del curso
- `Profesor profesor` - **Referencia al profesor responsable**

**Método crítico: setProfesor()**

Este es el **método más importante** del ejercicio. Realiza 3 pasos:

```java
public void setProfesor(Profesor nuevoProfesor) {
    // PASO 1: Remover del profesor anterior
    if (this.profesor != null && this.profesor != nuevoProfesor) {
        this.profesor.eliminarCurso(this);
    }
    
    // PASO 2: Asignar nuevo profesor
    this.profesor = nuevoProfesor;
    
    // PASO 3: Agregar al nuevo profesor
    if (nuevoProfesor != null && !nuevoProfesor.getCursos().contains(this)) {
        nuevoProfesor.agregarCurso(this);
    }
}
```

**Explicación visual:**

```
Estado inicial:
    ProfesorA ←→ CursoX
    ProfesorB

Llamar: cursoX.setProfesor(profesorB)

1. Remover de ProfesorA:
    ProfesorA     CursoX
    ProfesorB

2. Asignar nuevo:
    ProfesorA     CursoX → ProfesorB
    
3. Agregar a ProfesorB:
    ProfesorA     CursoX ←→ ProfesorB
```

---

### 3. **Universidad.java** (Gestora)

**Responsabilidades:**
- Administrar alta/baja de profesores y cursos
- Facilitar asignaciones mediante IDs
- Proporcionar búsquedas y reportes

**Métodos importantes:**

| Método | Descripción | Sincronización |
|--------|-------------|----------------|
| `asignarProfesorACurso(...)` | Asigna profesor a curso | Usa `curso.setProfesor()` |
| `eliminarCurso(String)` | Elimina curso | Rompe relación antes |
| `eliminarProfesor(String)` | Elimina profesor | Desasigna todos sus cursos |

**Eliminación segura de profesor:**
```java
public void eliminarProfesor(String id) {
    Profesor profesor = buscarProfesorPorId(id);
    if (profesor != null) {
        // Copiar lista para evitar ConcurrentModificationException
        List<Curso> cursosDelProfesor = new ArrayList<>(profesor.getCursos());
        
        // Desasignar cada curso
        for (Curso c : cursosDelProfesor) {
            c.setProfesor(null);  // Sincronización automática
        }
        
        profesores.remove(profesor);
    }
}
```

---

## 📊 Diagrama de Clases (UML)

```
┌─────────────────────┐
│    Universidad      │
├─────────────────────┤
│ - nombre: String    │
│ - profesores: List  │
│ - cursos: List      │
├─────────────────────┤
│ + agregarProfesor() │
│ + agregarCurso()    │
│ + asignarProfesor() │
│ + eliminarProfesor()│
│ + eliminarCurso()   │
└─────────────────────┘
         │ gestiona
         │
    ┌────┴────┐
    │         │
    ▼         ▼
┌─────────────────────┐         ┌──────────────────────┐
│      Profesor       │ 1    N  │       Curso          │
├─────────────────────┤◆────────◆├──────────────────────┤
│ - id: String        │  dicta  │ - codigo: String     │
│ - nombre: String    │←────────│ - nombre: String     │
│ - especialidad      │ profesor│ - profesor: Profesor │
│ - cursos: List<C>   │         │                      │
├─────────────────────┤         ├──────────────────────┤
│ + agregarCurso(C)   │         │ + setProfesor(P)     │
│ + eliminarCurso(C)  │         │ + getProfesor(): P   │
│ + listarCursos()    │         │ + mostrarInfo()      │
│ + mostrarInfo()     │         │                      │
└─────────────────────┘         └──────────────────────┘
```

**Notación:**
- `◆──────◆` indica bidireccionalidad
- `1` y `N` indican la cardinalidad (1 a muchos)

---

## 🔄 Flujo de Sincronización

### Ejemplo: Asignar profesor a curso

```
Situación inicial:
- Prof. Martínez NO tiene cursos
- Curso "Programación I" NO tiene profesor

Llamada: curso.setProfesor(martinez)

Pasos ejecutados:
1. curso.profesor = martinez
2. martinez.agregarCurso(curso)
   2.1. martinez.cursos.add(curso)
   2.2. curso.setProfesor(martinez) → Ya está, no hace nada

Resultado:
- martinez.cursos contiene "Programación I" ✓
- curso.profesor apunta a martinez ✓
→ SINCRONIZACIÓN COMPLETA
```

### Ejemplo: Cambiar profesor de un curso

```
Situación inicial:
- Prof. Martínez dicta "Programación I"
- curso.profesor = martinez
- martinez.cursos = ["Programación I"]

Llamada: curso.setProfesor(lopez)

Pasos ejecutados:
1. martinez.eliminarCurso(curso)
   1.1. martinez.cursos.remove(curso)
   1.2. curso.profesor = null (pero seguimos en setProfesor)
2. curso.profesor = lopez
3. lopez.agregarCurso(curso)
   3.1. lopez.cursos.add(curso)
   3.2. curso.setProfesor(lopez) → Ya está, no hace nada

Resultado:
- martinez.cursos = [] ✓
- lopez.cursos = ["Programación I"] ✓
- curso.profesor = lopez ✓
→ SINCRONIZACIÓN COMPLETA
```

---

## 🚀 Cómo Ejecutar

```bash
# Compilar
javac Profesor.java
javac Curso.java
javac Universidad.java
javac Main_Ejercicio3.java

# Ejecutar
java Main_Ejercicio3
```

---

## 📊 Tareas Implementadas

| # | Tarea | Concepto demostrado | Estado |
|---|-------|---------------------|--------|
| 1 | Crear 3 profesores y 5 cursos | Instanciación | ✅ |
| 2 | Agregar a universidad | Gestión de colecciones | ✅ |
| 3 | Asignar profesores a cursos | Sincronización inicial | ✅ |
| 4 | Listar cursos y profesores | Navegación bidireccional | ✅ |
| 5 | Cambiar profesor de un curso | **Sincronización compleja** | ✅ |
| 6 | Eliminar curso | Romper relación bidireccional | ✅ |
| 7 | Eliminar profesor | Desasignar múltiples cursos | ✅ |
| 8 | Reporte de cursos por profesor | Análisis de datos | ✅ |

---

## 💡 Comparación con Ejercicios Anteriores

| Aspecto | Ejercicio 1 (Stock) | Ejercicio 2 (Biblioteca) | Ejercicio 3 (Universidad) |
|---------|---------------------|--------------------------|---------------------------|
| **Tipo de relación** | Asociación simple | Composición | **Bidireccional** |
| **Navegación** | Solo Inventario → Producto | Solo Biblioteca → Libro | **Ambas direcciones** |
| **Sincronización** | No requerida | No requerida | **Crítica** |
| **Complejidad** | Baja | Media | **Alta** |
| **Responsabilidad** | Inventario gestiona | Biblioteca posee | **Ambos participan** |

---

## ⚠️ Errores Comunes y Soluciones

### Error 1: ConcurrentModificationException

**Problema:**
```java
for (Curso c : profesor.getCursos()) {
    c.setProfesor(null);  // Modifica la lista mientras la recorre
}
```

**Solución:**
```java
List<Curso> copia = new ArrayList<>(profesor.getCursos());
for (Curso c : copia) {
    c.setProfesor(null);  // Ahora es seguro
}
```

### Error 2: Recursión infinita

**Problema:**
```java
public void agregarCurso(Curso c) {
    cursos.add(c);
    c.setProfesor(this);  // Esto llama a agregarCurso otra vez → LOOP
}
```

**Solución:**
```java
public void agregarCurso(Curso c) {
    if (!cursos.contains(c)) {  // Evita duplicados
        cursos.add(c);
        if (c.getProfesor() != this) {  // Solo si es necesario
            c.setProfesor(this);
        }
    }
}
```

### Error 3: Referencias huérfanas

**Problema:**
```java
// Eliminar profesor sin desasignar cursos
profesores.remove(profesor);
// Los cursos siguen apuntando al profesor eliminado
```

**Solución:**
```java
// Primero desasignar todos los cursos
for (Curso c : new ArrayList<>(profesor.getCursos())) {
    c.setProfesor(null);
}
// Luego eliminar
profesores.remove(profesor);
```

---

## ✅ Conclusiones del Ejercicio

Al completar este ejercicio, se logra:

- ✓ Diferenciar **bidireccionalidad** de relaciones unidireccionales
- ✓ Mantener **invariantes de asociación** al agregar, quitar o reasignar
- ✓ Implementar **sincronización automática** entre ambos extremos
- ✓ Practicar colecciones (ArrayList), búsquedas y operaciones CRUD
- ✓ Diseñar métodos "seguros" que sincronicen los dos lados siempre
- ✓ Evitar errores comunes (recursión infinita, referencias huérfanas)
- ✓ Comprender la importancia de la **coherencia de datos**

---

## 🎓 Conceptos Avanzados

### 1. Navegación bidireccional
- Acceso desde ambos extremos de la relación
- Facilita consultas complejas
- Requiere mantenimiento cuidadoso

### 2. Invariante de asociación
- Regla que debe cumplirse siempre
- Garantiza consistencia de datos
- Se mantiene mediante sincronización

### 3. Métodos de sincronización
- Encapsulan la lógica de actualización
- Previenen estados inconsistentes
- Deben ser utilizados exclusivamente

---

## 📚 Referencias

- [Association vs Aggregation vs Composition](https://www.baeldung.com/java-composition-aggregation-association)
- [Bidirectional Relationships in JPA](https://www.baeldung.com/jpa-one-to-many)
- [Design Patterns: Managing Object Relationships](https://refactoring.guru/design-patterns/relationships)

---

**Autor:** Renzo Calcatelli - UTN - Programación II  
**Versión:** 1.0  
**Fecha:** 2025