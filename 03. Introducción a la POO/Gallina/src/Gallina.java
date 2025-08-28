public class Gallina {
    private static int contadorId = 1; // Variable estática para generar IDs únicos
    private int idGallina;
    private int edad;
    private int huevosPuestos;

    // Constructor
    public Gallina(int edad) {
        this.idGallina = contadorId++;
        setEdad(edad);
        this.huevosPuestos = 0;
    }

    // Método para poner huevos
    public void ponerHuevo() {
        if (edad < 1) {
            System.out.println("La gallina #" + idGallina + " es muy joven para poner huevos");
            return;
        }

        if (edad > 8) {
            System.out.println("La gallina #" + idGallina + " es muy vieja, pone pocos huevos");
            // Gallinas viejas ponen menos huevos
            if (Math.random() > 0.3) {
                return;
            }
        }

        huevosPuestos++;
        System.out.println("¡La gallina #" + idGallina + " puso un huevo! Total: " + huevosPuestos);
    }

    // Método para envejecer
    public void envejecer() {
        edad++;
        System.out.println("La gallina #" + idGallina + " cumplió años. Ahora tiene " + edad + " años");

        if (edad == 1) {
            System.out.println("¡Ya puede empezar a poner huevos!");
        } else if (edad > 8) {
            System.out.println("La gallina está entrando en edad avanzada");
        }
    }

    // Método para mostrar estado actual
    public void mostrarEstado() {
        System.out.println("=== ESTADO DE LA GALLINA #" + idGallina + " ===");
        System.out.println("Edad: " + edad + " años");
        System.out.println("Huevos puestos: " + huevosPuestos);
        System.out.println("Productividad: " + evaluarProductividad());
        System.out.println("Estado: " + determinarEstado());
        System.out.println("=====================================");
    }

    // Método privado para evaluar productividad
    private String evaluarProductividad() {
        if (huevosPuestos == 0) return "Sin producción";
        else if (huevosPuestos < 10) return "Baja";
        else if (huevosPuestos < 50) return "Media";
        else return "Alta";
    }

    // Método privado para determinar estado
    private String determinarEstado() {
        if (edad < 1) return "Pollita";
        else if (edad <= 3) return "Joven y productiva";
        else if (edad <= 6) return "Adulta";
        else return "Senior";
    }

    // Getters
    public int getIdGallina() { return idGallina; }
    public int getEdad() { return edad; }
    public int getHuevosPuestos() { return huevosPuestos; }

    // Setter con validación
    public void setEdad(int edad) {
        if (edad >= 0) {
            this.edad = edad;
        } else {
            System.out.println("La edad no puede ser negativa");
            this.edad = 0;
        }
    }
}