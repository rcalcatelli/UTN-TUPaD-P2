import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un profesor universitario.
 * Implementa el lado "1" de la relación bidireccional 1 a N con Curso.
 * Un profesor puede dictar múltiples cursos.
 *
 * IMPORTANTE: La relación es bidireccional, por lo que al agregar/quitar
 * cursos se debe sincronizar automáticamente con el lado del curso.
 *
 * @author Estudiante UTN - Programación II
 * @version 1.0
 */
public class Profesor {
    // Atributos privados (encapsulamiento)
    private String id;
    private String nombre;
    private String especialidad;
    private List<Curso> cursos;  // Lista de cursos que dicta (lado 1:N)

    /**
     * Constructor completo de la clase Profesor.
     * Inicializa la lista de cursos vacía.
     *
     * @param id Identificador único del profesor
     * @param nombre Nombre completo del profesor
     * @param especialidad Área de especialización del profesor
     */
    public Profesor(String id, String nombre, String especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.cursos = new ArrayList<>();
    }

    // Métodos getter para acceder a los atributos privados

    /**
     * @return El identificador único del profesor
     */
    public String getId() {
        return id;
    }

    /**
     * @return El nombre completo del profesor
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return La especialidad del profesor
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * @return La lista de cursos que dicta el profesor
     */
    public List<Curso> getCursos() {
        return cursos;
    }

    /**
     * Agrega un curso a la lista de cursos del profesor.
     * Este método NO llama a setProfesor para evitar recursión infinita.
     * La sincronización se hace directamente.
     *
     * IMPORTANTE: Este método es llamado desde setProfesor del curso.
     *
     * @param c Curso a agregar
     */
    public void agregarCurso(Curso c) {
        // Solo agregar si no está en la lista (evitar duplicados)
        if (!cursos.contains(c)) {
            cursos.add(c);
        }
        // NO llamar a c.setProfesor() aquí para evitar recursión
        // El curso ya está gestionando su propio cambio de profesor
    }

    /**
     * Elimina un curso de la lista de cursos del profesor.
     * Este método NO llama a setProfesor para evitar recursión infinita.
     * La sincronización se hace directamente.
     *
     * IMPORTANTE: Este método es llamado desde setProfesor del curso.
     *
     * @param c Curso a eliminar
     */
    public void eliminarCurso(Curso c) {
        cursos.remove(c);
        // NO llamar a c.setProfesor() aquí para evitar recursión
        // El curso ya está gestionando su propio cambio de profesor
    }

    /**
     * Lista en consola todos los cursos que dicta el profesor.
     * Muestra el código y nombre de cada curso.
     */
    public void listarCursos() {
        System.out.println("\n📚 Cursos de " + nombre + ":");

        if (cursos.isEmpty()) {
            System.out.println("   ⚠ No tiene cursos asignados.");
        } else {
            for (Curso c : cursos) {
                System.out.println("   • " + c.getCodigo() + ": " + c.getNombre());
            }
        }
    }

    /**
     * Muestra en consola la información completa del profesor.
     * Incluye: ID, nombre, especialidad y cantidad de cursos.
     */
    public void mostrarInfo() {
        System.out.println("ID: " + id +
                " | Nombre: " + nombre +
                " | Especialidad: " + especialidad +
                " | Cursos: " + cursos.size());
    }
}