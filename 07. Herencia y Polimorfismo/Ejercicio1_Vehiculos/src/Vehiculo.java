public class Vehiculo {
    // Atributos protegidos: accesibles desde clases hijas
    protected String marca;
    protected String modelo;

    // Constructor: inicializa los atributos del vehículo
    public Vehiculo(String marca, String modelo) {
        this.marca = marca;
        this.modelo = modelo;
    }

    // Método que muestra la información básica del vehículo
    public void mostrarInfo() {
        System.out.println("Modelo: " + modelo + " ,Marca: " + marca);
    }
}