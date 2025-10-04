public class Circulo extends Figura {
    // Atributo específico del círculo
    private double radio;

    // Constructor
    public Circulo(double radio, String nombre) {
        super(nombre);  // Inicializa el nombre en la clase padre
        this.radio = radio;
    }

    // Implementación del método abstracto para calcular área del círculo
    @Override
    public void calcularArea() {
        System.out.println("El area del circulo " + nombre + " es: " +
                (radio * 3.14));
    }
}
