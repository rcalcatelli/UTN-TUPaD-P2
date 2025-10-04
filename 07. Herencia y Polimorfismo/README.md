# 🔷 TP7 – Herencia y Polimorfismo

<div align="center">
  
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![POO](https://img.shields.io/badge/POO-Herencia-9B59B6?style=for-the-badge)
![Polimorfismo](https://img.shields.io/badge/Polimorfismo-Abstracto-E74C3C?style=for-the-badge)
![UTN](https://img.shields.io/badge/UTN-TUPaD-0066CC?style=for-the-badge)

</div>

---

## 📖 Descripción

Este repositorio contiene la resolución del **Trabajo Práctico 7** de la materia **Programación II** de la **Tecnicatura Universitaria en Programación a Distancia (UTN)**.

El objetivo principal es aplicar los conceptos de **Herencia** y **Polimorfismo**, dos pilares fundamentales de la Programación Orientada a Objetos, mediante la implementación de 5 ejercicios prácticos (katas) que demuestran:

- Jerarquías de clases con `extends`
- Clases abstractas y métodos abstractos
- Sobrescritura de métodos con `@Override`
- Implementación de interfaces
- Uso de `instanceof` para identificación de tipos
- Polimorfismo en acción

---

## 🎯 Objetivo general

* Comprender y aplicar los conceptos de **herencia** y **polimorfismo** en la Programación Orientada a Objetos.
* Implementar **jerarquías de clases** utilizando herencia simple.
* Desarrollar soluciones flexibles mediante **clases abstractas** y **métodos abstractos**.
* Aplicar **interfaces** para definir contratos de comportamiento.
* Demostrar el **polimorfismo** mediante referencias de tipo padre y enlace dinámico.
* Utilizar correctamente los **modificadores de acceso** (`protected`, `private`, `public`).
* Reconocer la importancia de estos conceptos para la **reutilización de código** y el **diseño flexible** de soluciones.

---

## 📚 Ejercicios incluidos

Cada ejercicio implementa un concepto específico de herencia y polimorfismo, con complejidad creciente:

### 🚗 **Ejercicio 1: Vehículos y Herencia Básica**
- **Conceptos:** Herencia simple, sobrescritura de métodos, uso de `super()`
- **Clases:** `Vehiculo` (padre), `Auto` (hija)
- **Implementa:** Atributos protegidos, constructor con `super()`, método `mostrarInfo()` sobrescrito

### 📐 **Ejercicio 2: Figuras Geométricas y Métodos Abstractos**
- **Conceptos:** Clases abstractas, métodos abstractos, polimorfismo
- **Clases:** `Figura` (abstracta), `Circulo`, `Rectangulo`
- **Implementa:** Método abstracto `calcularArea()`, ArrayList polimórfico

### 💼 **Ejercicio 3: Empleados y Polimorfismo con instanceof**
- **Conceptos:** Identificación de tipos, instanceof, polimorfismo
- **Clases:** `Empleado` (abstracta), `EmpleadoPlanta`, `EmpleadoTemporal`
- **Implementa:** Cálculo de sueldo según tipo, uso de `instanceof`

### 🐾 **Ejercicio 4: Animales y Comportamiento Sobrescrito**
- **Conceptos:** Sobrescritura con `@Override`, polimorfismo clásico
- **Clases:** `Animal`, `Perro`, `Gato`, `Vaca`
- **Implementa:** Método `hacerSonido()` sobrescrito, comportamiento polimórfico

### 💳 **Ejercicio 5: Sistema de Pagos con Interfaces**
- **Conceptos:** Interfaces, implementación múltiple, métodos genéricos
- **Interfaz:** `Pagable`
- **Clases:** `TarjetaCredito`, `Transferencia`, `Efectivo`
- **Implementa:** Método genérico `procesarPago(Pagable medio)`

---

## 📋 Archivos principales

```
📄 TP7 - Herencia y Polimorfismo.pdf (Informe completo)
📂 Ejercicio1_Vehiculos/
   ├── Vehiculo.java
   ├── Auto.java
   └── Main.java
📂 Ejercicio2_Figuras/
   ├── Figura.java
   ├── Circulo.java
   ├── Rectangulo.java
   └── Main.java
📂 Ejercicio3_Empleados/
   ├── Empleado.java
   ├── EmpleadoPlanta.java
   ├── EmpleadoTemporal.java
   └── Main.java
📂 Ejercicio4_Animales/
   ├── Animal.java
   ├── Perro.java
   ├── Gato.java
   ├── Vaca.java
   └── Main.java
📂 Ejercicio5_Pagos/
   ├── Pagable.java
   ├── TarjetaCredito.java
   ├── Transferencia.java
   ├── Efectivo.java
   └── Main.java
📄 README.md
```

---

## 🔍 Conceptos clave aplicados

| Concepto | Ejercicios | Descripción |
|----------|-----------|-------------|
| **Herencia simple** | 1, 2, 3, 4 | Uso de `extends` para crear jerarquías |
| **Clases abstractas** | 2, 3 | Clases base que no pueden instanciarse |
| **Métodos abstractos** | 2 | Métodos sin implementación en la clase padre |
| **Sobrescritura** | 1, 2, 4, 5 | Redefinición de métodos con `@Override` |
| **Polimorfismo** | 2, 3, 4, 5 | Tratamiento uniforme de objetos diferentes |
| **Interfaces** | 5 | Contratos de comportamiento |
| **instanceof** | 3 | Identificación de tipos en runtime |
| **Modificadores** | 1-5 | protected, private, public |

---

## 💡 Análisis comparativo

### Herencia vs. Interfaces

| Aspecto | Herencia (extends) | Interfaces (implements) |
|---------|-------------------|------------------------|
| Cantidad | Solo una clase padre | Múltiples interfaces |
| Implementación | Métodos implementados | Solo declaración |
| Relación | "es un/una" (is-a) | "puede hacer" (can-do) |
| Ejemplo | Auto extends Vehiculo | TarjetaCredito implements Pagable |

### Cuándo usar cada uno

- **Herencia:** Cuando existe una relación "es un" clara y hay código para reutilizar
- **Clases abstractas:** Cuando hay comportamiento común pero algunos métodos deben implementarse en subclases
- **Interfaces:** Cuando se definen capacidades o comportamientos sin implementación compartida

---

## ✅ Conclusiones

Este trabajo práctico permitió consolidar los conceptos fundamentales de la Programación Orientada a Objetos:

### Herencia
* **Reutilización de código:** No repetimos código, lo heredamos de la clase padre
* **Jerarquías lógicas:** Organizamos clases según relaciones "es un/una"
* **Modificadores de acceso:** `protected` permite acceso a subclases manteniendo encapsulamiento

### Polimorfismo
* **Flexibilidad:** Tratamos objetos diferentes de manera uniforme
* **Extensibilidad:** Agregamos nuevas clases sin modificar código existente
* **Enlace dinámico:** La decisión de qué método ejecutar se toma en tiempo de ejecución

### Clases abstractas e interfaces
* **Abstracción:** Definimos estructuras base que completan las subclases
* **Contratos:** Las interfaces establecen qué debe hacer una clase sin especificar cómo
* **Diseño flexible:** Separamos "qué hace" de "cómo lo hace"

### Aplicación práctica
Los 5 ejercicios demuestran escenarios reales donde estos conceptos son esenciales:
- Sistemas de clasificación (vehículos, animales)
- Cálculos especializados (figuras geométricas)
- Procesamiento genérico (sistema de pagos)
- Diferenciación de tipos (empleados)

El trabajo integra conceptos clave de la **programación orientada a objetos**, reforzando la capacidad de crear **código reutilizable, mantenible y escalable**.


---

## 📚 Bibliografía

- Oracle Java Documentation - [Inheritance](https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html)
- Oracle Java Documentation - [Polymorphism](https://docs.oracle.com/javase/tutorial/java/IandI/polymorphism.html)
- Material de Programación II - UTN TUPaD
- Deitel, P. & Deitel, H. (2018). *Java How to Program*

---

<div align="center">

**Programación II - 2025**  
*Universidad Tecnológica Nacional - TUPaD*

</div>
