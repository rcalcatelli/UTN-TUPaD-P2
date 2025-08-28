public class Libro {
    // Atributos privados - principio de encapsulamiento
    private String titulo;
    private String autor;
    private int anioPublicacion;

    // Constructor
    public Libro(String titulo, String autor, int anioPublicacion) {
        this.titulo = titulo;
        this.autor = autor;
        setAnioPublicacion(anioPublicacion); // Usar setter para validación
    }

    // Getters - acceso controlado de lectura
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    // Setter con validación para año de publicación
    public void setAnioPublicacion(int anioPublicacion) {
        int anioActual = java.time.Year.now().getValue();

        if (anioPublicacion > 0 && anioPublicacion <= anioActual) {
            this.anioPublicacion = anioPublicacion;
            System.out.println("Año de publicación actualizado correctamente: " + anioPublicacion);
        } else {
            System.out.println("Error: Año inválido. Debe estar entre 1 y " + anioActual);
        }
    }

    // Método para mostrar información del libro
    public void mostrarInfo() {
        System.out.println("=== INFORMACIÓN DEL LIBRO ===");
        System.out.println("Título: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Año de Publicación: " + anioPublicacion);
        System.out.println("Antigüedad: " + calcularAntiguedad() + " años");
        System.out.println("============================");
    }

    // Método auxiliar para calcular antigüedad
    private int calcularAntiguedad() {
        return java.time.Year.now().getValue() - anioPublicacion;
    }
}