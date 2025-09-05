# 🚀 TP4 – Programación Orientada a Objetos II

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![POO](https://img.shields.io/badge/Paradigma-POO-blueviolet?style=for-the-badge)
![UTN](https://img.shields.io/badge/UTN-TUPaD-0066CC?style=for-the-badge)

</div>

---

## 📖 Descripción

Este repositorio contiene la resolución del **Trabajo Práctico 4** de la materia **Programación II** de la **Tecnicatura Universitaria en Programación a Distancia (UTN)**. El objetivo principal es aplicar conceptos avanzados de **Programación Orientada a Objetos (POO)** en Java para modelar la clase `Empleado`.

---

## 🎯 Objetivo general

Comprender y aplicar conceptos clave de POO en Java, como el uso de **`this`**, la **sobrecarga de constructores y métodos**, el **encapsulamiento** y los **miembros estáticos**. El código busca ser claro, modular y fácil de mantener.

---

## 📚 Marco teórico aplicado

* **Uso de `this`**: Se utiliza para referenciar el objeto actual (la instancia) y evitar la ambigüedad entre atributos de la clase y parámetros de los métodos. También se usa para llamar a otro constructor de la misma clase, un patrón conocido como delegación.
* **Constructores Sobrecargados**: Permite tener múltiples constructores en la misma clase con diferentes parámetros, lo que ofrece flexibilidad al crear objetos. Deben tener el mismo nombre que la clase, pero diferir en el número, tipo u orden de los parámetros.
* **Sobrecarga de Métodos (Overloading)**: Define múltiples métodos con el mismo nombre pero distintos parámetros para realizar operaciones relacionadas. Esto hace que la API sea más intuitiva.
* **Método `toString()`**: Un método heredado de la clase `Object` que se sobrescribe (`@Override`) para devolver una representación útil y legible del objeto en forma de cadena de texto.
* **Atributos y Métodos Estáticos**: Miembros que pertenecen a la clase en lugar de a una instancia específica. Los atributos estáticos (como `totalEmpleados`) se comparten por todas las instancias de la clase. Los métodos estáticos pueden ser llamados sin crear un objeto.
* **Encapsulamiento**: Principio que oculta los detalles internos de una clase y controla el acceso a sus datos a través de métodos públicos (`getters` y `setters`). Esto protege los datos, permite la validación de la información y facilita el mantenimiento del código.

---

## 📋 Casos Prácticos

### 👨‍💼 Clase `Empleado`
Se ha modelado una clase `Empleado` que aplica los conceptos teóricos para gestionar la información de los empleados.

* **Atributos**: `id`, `nombre`, `puesto`, `salario`.
* **Constructores**:
    * Constructor completo que inicializa todos los atributos del empleado.
    * Constructor simplificado que solo recibe el nombre y el puesto, y llama al constructor completo para asignar un salario por defecto.
* **Métodos**:
    * `actualizarSalario()`: Método sobrecargado para aumentar el salario, ya sea por un porcentaje (`double`) o por una cantidad fija (`int`).
    * `toString()`: Sobrescrito para mostrar de forma formateada y legible la información del empleado.
    * `mostrarTotalEmpleados()`: Método estático que devuelve el número total de empleados creados.

---

## ✅ Conclusiones

La aplicación de estos conceptos no es solo teórica, sino la base para crear código profesional y de alta calidad.

* **Mantenibilidad**: El código es mucho más fácil de modificar y extender al estar organizado dentro de la clase.
* **Reutilización**: La clase `Empleado` puede ser utilizada en cualquier otro proyecto que requiera gestionar personal.
* **Robustez**: El encapsulamiento protege los datos y minimiza errores, ya que las modificaciones solo se pueden realizar a través de métodos controlados.
* **Legibilidad**: El código es "autodocumentado" y fácil de entender, gracias a la sobrecarga que permite usar nombres de métodos claros y descriptivos.
* **Escalabilidad**: El diseño modular permite que el sistema crezca sin colapsar, funcionando igual de bien con 10 o 10,000 empleados.

---
<div align="center">
  
<img src="https://img.shields.io/badge/Programación%20Orientada%20a%20Objetos-💻-00C6FF?style=for-the-badge">

</div>
