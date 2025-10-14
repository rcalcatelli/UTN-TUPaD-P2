# 🔷 TP8 – Interfaces y Excepciones en Java

<div align="center">
  
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![POO](https://img.shields.io/badge/POO-Interfaces-3498DB?style=for-the-badge)
![Excepciones](https://img.shields.io/badge/Excepciones-Try--Catch-E74C3C?style=for-the-badge)
![UTN](https://img.shields.io/badge/UTN-TUPaD-0066CC?style=for-the-badge)

</div>

---

## 📖 Descripción

Este repositorio contiene la resolución del **Trabajo Práctico 8** de la materia **Programación II** de la **Tecnicatura Universitaria en Programación a Distancia (UTN)**.

El objetivo principal es aplicar los conceptos de **Interfaces** y **Manejo de Excepciones**, componentes esenciales del desarrollo profesional en Java, mediante la implementación de:

- Sistema de E-commerce con interfaces múltiples
- Herencia múltiple de interfaces
- Patrón Observer para notificaciones
- Manejo robusto de errores con excepciones
- Excepciones personalizadas
- Try-with-resources para gestión de recursos

---

## 🎯 Objetivo general

* Comprender la utilidad de las **interfaces** para lograr diseños desacoplados y reutilizables.
* Aplicar **herencia múltiple** a través de interfaces para combinar comportamientos.
* Utilizar correctamente estructuras de control de **excepciones** para evitar caídas del programa.
* Crear **excepciones personalizadas** para validar reglas de negocio.
* Aplicar buenas prácticas como **try-with-resources** y uso del bloque **finally** para manejar recursos y errores.
* Reforzar el diseño robusto y mantenible mediante la integración de interfaces y manejo de errores en Java.

---

## 📚 Contenido del trabajo práctico

El TP se divide en dos partes principales que integran interfaces y excepciones:

### 🛒 **Parte 1: Sistema de E-commerce con Interfaces**

#### **Interfaz Pagable**
- **Conceptos:** Contratos de comportamiento, polimorfismo
- **Implementan:** `Producto`, `Pedido`
- **Demuestra:** Cálculo polimórfico de totales

#### **Interfaces de Pago (Herencia Múltiple)**
- **Interfaces:** `Pago`, `PagoConDescuento` (extends Pago)
- **Implementan:** `TarjetaCredito`, `PayPal`
- **Demuestra:** Herencia de interfaces, procesamiento de pagos con descuentos

#### **Sistema de Notificaciones (Patrón Observer)**
- **Interfaz:** `Notificable`
- **Implementa:** `Cliente`
- **Demuestra:** Comunicación automática cuando cambia el estado del pedido

### ⚠️ **Parte 2: Ejercicios sobre Excepciones**

#### **1. División Segura**
- **Excepción:** `ArithmeticException`
- **Demuestra:** Try-catch-finally básico, manejo de división por cero

#### **2. Conversión de Cadena a Número**
- **Excepción:** `NumberFormatException`
- **Demuestra:** Validación de entrada del usuario

#### **3. Lectura de Archivo**
- **Excepciones:** `FileNotFoundException`, `IOException`
- **Demuestra:** Múltiples catch, cierre de recursos en finally

#### **4. Excepción Personalizada**
- **Clase:** `EdadInvalidaException`
- **Demuestra:** Creación y uso de excepciones custom para reglas de negocio

#### **5. Try-with-Resources**
- **Recurso:** `BufferedReader`
- **Demuestra:** Cierre automático de recursos (buenas prácticas desde Java 7+)

---

## 📋 Archivos principales

```
📄 README.md
📂 src/
   ├── Pagable.java
   ├── Producto.java
   ├── Pedido.java
   ├── Pago.java
   ├── PagoConDescuento.java
   ├── TarjetaCredito.java
   ├── PayPal.java
   ├── Notificable.java
   ├── Cliente.java
   ├── EdadInvalidaException.java
   ├── EjerciciosExcepciones.java
   └── Main.java
```

---

## 🔍 Conceptos clave aplicados

| Concepto | Archivos | Descripción |
|----------|----------|-------------|
| **Interfaces** | Pagable, Notificable, Pago | Contratos de comportamiento común |
| **Herencia múltiple** | PagoConDescuento extends Pago | Una interfaz puede extender otra |
| **Implementación** | Producto, Pedido, Cliente | Uso de `implements` para cumplir contratos |
| **Polimorfismo** | Main.java (arrays Pagable) | Tratamiento uniforme de diferentes tipos |
| **Try-catch-finally** | EjerciciosExcepciones | Manejo estructurado de errores |
| **Excepciones checked** | EdadInvalidaException | Deben ser declaradas o capturadas |
| **Excepciones unchecked** | ArithmeticException | No requieren declaración explícita |
| **Try-with-resources** | tryWithResources() | Cierre automático de recursos |

---

## 💡 Análisis comparativo

### Interfaces vs. Clases Abstractas

| Aspecto | Interfaces (implements) | Clases Abstractas (extends) |
|---------|------------------------|----------------------------|
| Cantidad | Múltiples interfaces | Solo una clase padre |
| Implementación | Solo declaración | Pueden tener métodos implementados |
| Relación | "puede hacer" (can-do) | "es un/una" (is-a) |
| Ejemplo | TarjetaCredito implements PagoConDescuento | Auto extends Vehiculo |

### Excepciones Checked vs. Unchecked

| Tipo | Checked | Unchecked |
|------|---------|-----------|
| Padre | Exception | RuntimeException |
| Compilador | Obliga a manejarlas | No obliga |
| Cuándo usar | Errores recuperables | Errores de programación |
| Ejemplos | IOException, EdadInvalidaException | ArithmeticException, NullPointerException |

### Cuándo usar cada uno

- **Interfaces:** Para definir capacidades o comportamientos sin implementación compartida
- **Excepciones checked:** Para errores que el código cliente puede y debe manejar
- **Excepciones unchecked:** Para errores de programación que no deberían ocurrir
- **Try-with-resources:** Siempre que se trabaje con recursos que implementen AutoCloseable

---

## ✅ Conclusiones

Este trabajo práctico permitió consolidar conceptos avanzados de la Programación Orientada a Objetos:

### Interfaces
* **Desacoplamiento:** El código depende de abstracciones, no de implementaciones concretas
* **Herencia múltiple de comportamiento:** Una clase puede implementar múltiples interfaces
* **Polimorfismo efectivo:** Tratamiento uniforme de objetos diferentes a través de contratos

### Excepciones
* **Robustez:** El programa maneja errores sin terminar abruptamente
* **Separación de responsabilidades:** Código de error separado del código normal
* **Try-with-resources:** Previene memory leaks con cierre automático de recursos
* **Excepciones personalizadas:** Expresan reglas de negocio de forma clara

### Integración de conceptos
El sistema de E-commerce integra exitosamente:
- Interfaces para definir contratos (Pagable, Pago, Notificable)
- Excepciones para validación y manejo de errores
- Patrones de diseño (Observer para notificaciones)
- Polimorfismo para tratamiento uniforme de diferentes tipos

El trabajo demuestra la capacidad de crear **sistemas robustos, mantenibles y escalables**, fundamentales para el desarrollo profesional de software.

---

## 📚 Bibliografía

- Oracle Java Documentation - [Interfaces](https://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html)
- Oracle Java Documentation - [Exceptions](https://docs.oracle.com/javase/tutorial/essential/exceptions/)
- Effective Java - Joshua Bloch (3rd Edition)
- Material de Programación II - UTN TUPaD

---

<div align="center">

**Programación II - 2025**  
*Universidad Tecnológica Nacional - TUPaD*

</div>
