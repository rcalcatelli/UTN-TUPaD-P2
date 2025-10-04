public class Rectangulo extends Figura {
    // Atributos específicos del rectángulo
    private double base;
    private double altura;

    // Constructor
    public Rectangulo(double base, double altura, String nombre) {
        super(nombre);
        this.base = base;
        this.altura = altura;
    }

    // Implementación del método abstracto para calcular área del rectángulo
    @Override
    public void calcularArea() {
        System.out.println("El area del rectangulo " + nombre + " es: " +
                (base * altura));
    }
}