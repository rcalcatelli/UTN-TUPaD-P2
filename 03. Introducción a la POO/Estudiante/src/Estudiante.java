public class Estudiante {
    // Atributos privados (encapsulamiento)
    private String nombre;
    private String apellido;
    private String curso;
    private double calificacion;

    // Constructor para inicializar el objeto
    public Estudiante(String nombre, String apellido, String curso, double calificacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.curso = curso;
        setCalificacion(calificacion); // Usamos el setter para validar
    }

    // Método para mostrar información del estudiante
    public void mostrarInfo() {
        System.out.println("=== INFORMACIÓN DEL ESTUDIANTE ===");
        System.out.println("Nombre: " + nombre + " " + apellido);
        System.out.println("Curso: " + curso);
        System.out.printf("Calificación: %.2f%n", calificacion);
        System.out.println("Estado: " + obtenerEstadoAcademico());
        System.out.println("================================");
    }

    // Método para subir calificación con validación
    public void subirCalificacion(double puntos) {
        if (puntos <= 0) {
            System.out.println("Error: Los puntos deben ser positivos");
            return;
        }

        double nuevaCalificacion = this.calificacion + puntos;
        if (nuevaCalificacion > 10.0) {
            System.out.println("Advertencia: La calificación no puede superar 10.0");
            this.calificacion = 10.0;
        } else {
            this.calificacion = nuevaCalificacion;
        }

        System.out.printf("Calificación aumentada en %.2f puntos. Nueva calificación: %.2f%n",
                puntos, this.calificacion);
    }

    // Método para bajar calificación con validación
    public void bajarCalificacion(double puntos) {
        if (puntos <= 0) {
            System.out.println("Error: Los puntos deben ser positivos");
            return;
        }

        double nuevaCalificacion = this.calificacion - puntos;
        if (nuevaCalificacion < 0.0) {
            System.out.println("Advertencia: La calificación no puede ser negativa");
            this.calificacion = 0.0;
        } else {
            this.calificacion = nuevaCalificacion;
        }

        System.out.printf("Calificación reducida en %.2f puntos. Nueva calificación: %.2f%n",
                puntos, this.calificacion);
    }

    // Método auxiliar para determinar el estado académico
    private String obtenerEstadoAcademico() {
        if (calificacion >= 7.0) return "Aprobado";
        else if (calificacion >= 4.0) return "Regular";
        else return "Libre";
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCurso() { return curso; }
    public double getCalificacion() { return calificacion; }

    public void setCalificacion(double calificacion) {
        if (calificacion >= 0.0 && calificacion <= 10.0) {
            this.calificacion = calificacion;
        } else {
            System.out.println("Calificación inválida. Debe estar entre 0 y 10");
            this.calificacion = 0.0;
        }
    }
}