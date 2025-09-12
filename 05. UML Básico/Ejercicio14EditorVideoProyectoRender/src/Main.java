// Clase Proyecto
public class Proyecto {
    private String nombre;
    private int duracionMin;

    public Proyecto(String nombre, int duracionMin) {
        this.nombre = nombre;
        this.duracionMin = duracionMin;
    }

    public String getNombre() { return nombre; }
    public int getDuracionMin() { return duracionMin; }
}

// Clase Render
public class Render {
    private String formato;
    private Proyecto proyecto; // Asociación unidireccional

    public Render(String formato, Proyecto proyecto) {
        this.formato = formato;
        this.proyecto = proyecto;
    }

    public String getFormato() { return formato; }
    public Proyecto getProyecto() { return proyecto; }
}

// Clase EditorVideo
public class EditorVideo {
    // Dependencia de creación: crea Render dentro del método pero no lo conserva como atributo
    public void exportar(String formato, Proyecto proyecto) {
        Render render = new Render(formato, proyecto); // Crea el objeto
        System.out.println("Exportando proyecto: " + render.getProyecto().getNombre() +
                " en formato: " + render.getFormato() +
                " - Duración: " + render.getProyecto().getDuracionMin() + " min");
        // El objeto Render se crea y se usa localmente, no se almacena como atributo
    }
}