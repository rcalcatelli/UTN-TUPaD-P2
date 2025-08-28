public class Mascota {
    private String nombre;
    private String especie;
    private int edad;

    // Constructor
    public Mascota(String nombre, String especie, int edad) {
        this.nombre = nombre;
        this.especie = especie;
        setEdad(edad);
    }

    // Mostrar información de la mascota
    public void mostrarInfo() {
        System.out.println("=== INFORMACIÓN DE LA MASCOTA ===");
        System.out.println("Nombre: " + nombre);
        System.out.println("Especie: " + especie);
        System.out.println("Edad: " + edad + " años");
        System.out.println("Etapa de vida: " + determinarEtapaVida());
        System.out.println("==============================");
    }

    // Método para simular el cumpleaños
    public void cumplirAnios() {
        edad++;
        System.out.println("¡Feliz cumpleaños " + nombre + "! Ahora tiene " + edad + " años.");

        // Verificar cambio de etapa de vida
        String nuevaEtapa = determinarEtapaVida();
        System.out.println("Etapa de vida: " + nuevaEtapa);
    }

    // Método privado para determinar la etapa de vida
    private String determinarEtapaVida() {
        if (especie.equalsIgnoreCase("Perro")) {
            if (edad < 1) return "Cachorro";
            else if (edad < 7) return "Adulto";
            else return "Senior";
        } else if (especie.equalsIgnoreCase("Gato")) {
            if (edad < 1) return "Gatito";
            else if (edad < 8) return "Adulto";
            else return "Senior";
        } else {
            if (edad < 2) return "Joven";
            else if (edad < 10) return "Adulto";
            else return "Senior";
        }
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public String getEspecie() { return especie; }
    public int getEdad() { return edad; }

    public void setEdad(int edad) {
        if (edad >= 0) {
            this.edad = edad;
        } else {
            System.out.println("La edad no puede ser negativa");
            this.edad = 0;
        }
    }
}