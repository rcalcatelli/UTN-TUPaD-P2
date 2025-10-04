public static void main(String[] args) {
    // Instanciamos un auto con 5 puertas, marca Renault, modelo Sandero
    Auto a = new Auto(5, "Renault", "Sandero");

    // Llamamos al método sobrescrito que muestra toda la información
    a.mostrarInfo();
}