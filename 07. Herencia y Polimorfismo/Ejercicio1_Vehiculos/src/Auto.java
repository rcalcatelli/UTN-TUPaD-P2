public class Auto extends Vehiculo {  // Heredamos de Vehiculo
    // Atributo adicional específico de Auto
    private int cantidadDePuertas;

    // Constructor: utiliza super() para inicializar atributos heredados
    public Auto(int cantidadDePuertas, String marca, String modelo) {
        super(marca, modelo);  // Llama al constructor de la clase padre
        this.cantidadDePuertas = cantidadDePuertas;
    }

    // Sobrescribimos el método heredado para mostrar más información
    @Override
    public void mostrarInfo() {
        System.out.println("Modelo: " + this.modelo + " ,Marca: " + this.marca +
                ", Cantidad de puertas: " + cantidadDePuertas);
    }
}
