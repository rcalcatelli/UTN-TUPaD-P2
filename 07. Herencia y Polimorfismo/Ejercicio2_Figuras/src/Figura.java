public abstract class Figura {
    // Atributo protegido para el nombre de la figura
    protected String nombre;

    // Constructor de la clase abstracta
    public Figura(String nombre) {
        this.nombre = nombre;
    }

    // Método abstracto: debe ser implementado por las subclases
    public abstract void calcularArea();
}