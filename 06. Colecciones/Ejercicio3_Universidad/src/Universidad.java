import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una universidad y gestiona sus profesores y cursos.
 * Proporciona métodos para administrar el alta/baja de profesores y cursos,
 * así como la asignación de profesores a cursos (relación bidireccional).
 *
 * @author Renzo Calcatelli - UTN - Programación II
 * @version 1.0
 */
public class Universidad {
    // Atributos privados
    private String nombre;
    private List<Profesor> profesores;
    private List<Curso> cursos;

    /**
     * Constructor que crea una universidad con listas vacías.
     *
     * @param nombre Nombre de la universidad
     */
    public Universidad(String nombre) {
        this.nombre = nombre;
        this.profesores = new ArrayList<>();
        this.cursos = new ArrayList<>();
    }

    /**
     * @return El nombre de la universidad
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Agrega un profesor a la universidad.
     *
     * @param p Profesor a agregar
     */
    public void agregarProfesor(Profesor p) {
        profesores.add(p);
        System.out.println("✓ Profesor agregado: " + p.getNombre());
    }

    /**
     * Agrega un curso a la universidad.
     *
     * @param c Curso a agregar
     */
    public void agregarCurso(Curso c) {
        cursos.add(c);
        System.out.println("✓ Curso agregado: " + c.getNombre());
    }

    /**
     * Asigna un profesor a un curso utilizando sus identificadores.
     * Este método usa el setProfesor del curso, que automáticamente
     * sincroniza la relación bidireccional.
     *
     * @param codigoCurso Código del curso
     * @param idProfesor ID del profesor
     */
    public void asignarProfesorACurso(String codigoCurso, String idProfesor) {
        Curso curso = buscarCursoPorCodigo(codigoCurso);
        Profesor profesor = buscarProfesorPorId(idProfesor);

        if (curso != null && profesor != null) {
            curso.setProfesor(profesor);  // Sincronización automática
            System.out.println("✓ Profesor " + profesor.getNombre() +
                    " asignado al curso " + curso.getNombre());
        } else {
            if (curso == null) {
                System.out.println("✗ Error: Curso con código " + codigoCurso + " no encontrado.");
            }
            if (profesor == null) {
                System.out.println("✗ Error: Profesor con ID " + idProfesor + " no encontrado.");
            }
        }
    }

    /**
     * Lista todos los profesores de la universidad.
     */
    public void listarProfesores() {
        if (profesores.isEmpty()) {
            System.out.println("⚠ No hay profesores en la universidad.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║              PROFESORES DE " + nombre.toUpperCase());
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");

        for (Profesor p : profesores) {
            p.mostrarInfo();
        }

        System.out.println("─────────────────────────────────────────────────────────────────────");
        System.out.println("Total de profesores: " + profesores.size());
    }

    /**
     * Lista todos los cursos de la universidad con su profesor asignado.
     */
    public void listarCursos() {
        if (cursos.isEmpty()) {
            System.out.println("⚠ No hay cursos en la universidad.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║               CURSOS DE " + nombre.toUpperCase());
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");

        for (Curso c : cursos) {
            c.mostrarInfo();
        }

        System.out.println("─────────────────────────────────────────────────────────────────────");
        System.out.println("Total de cursos: " + cursos.size());
    }

    /**
     * Busca un profesor por su ID.
     *
     * @param id ID del profesor a buscar
     * @return El profesor encontrado, o null si no existe
     */
    public Profesor buscarProfesorPorId(String id) {
        for (Profesor p : profesores) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Busca un curso por su código.
     *
     * @param codigo Código del curso a buscar
     * @return El curso encontrado, o null si no existe
     */
    public Curso buscarCursoPorCodigo(String codigo) {
        for (Curso c : cursos) {
            if (c.getCodigo().equals(codigo)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Elimina un curso de la universidad por su código.
     * IMPORTANTE: Antes de eliminar, rompe la relación con su profesor
     * para mantener la consistencia bidireccional.
     *
     * @param codigo Código del curso a eliminar
     */
    public void eliminarCurso(String codigo) {
        Curso curso = buscarCursoPorCodigo(codigo);

        if (curso != null) {
            // CRÍTICO: Romper la relación bidireccional
            // Si tiene profesor, quitarse de su lista
            if (curso.getProfesor() != null) {
                curso.getProfesor().eliminarCurso(curso);
            }

            cursos.remove(curso);
            System.out.println("✓ Curso eliminado: " + curso.getNombre());
        } else {
            System.out.println("✗ Curso con código " + codigo + " no encontrado.");
        }
    }

    /**
     * Elimina un profesor de la universidad por su ID.
     * IMPORTANTE: Antes de eliminar, desasigna todos los cursos que dictaba,
     * dejando su profesor en null para mantener la consistencia.
     *
     * @param id ID del profesor a eliminar
     */
    public void eliminarProfesor(String id) {
        Profesor profesor = buscarProfesorPorId(id);

        if (profesor != null) {
            // CRÍTICO: Desasignar todos los cursos que dictaba
            // Usar una copia de la lista para evitar ConcurrentModificationException
            List<Curso> cursosDelProfesor = new ArrayList<>(profesor.getCursos());

            for (Curso c : cursosDelProfesor) {
                c.setProfesor(null);  // Desasignar el profesor (sincronización automática)
            }

            profesores.remove(profesor);
            System.out.println("✓ Profesor eliminado: " + profesor.getNombre());
            System.out.println("  " + cursosDelProfesor.size() + " curso(s) quedaron sin profesor.");
        } else {
            System.out.println("✗ Profesor con ID " + id + " no encontrado.");
        }
    }

    /**
     * Muestra un reporte con la cantidad de cursos por cada profesor.
     * Útil para ver la carga académica de cada docente.
     */
    public void mostrarReporteCursosPorProfesor() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║              REPORTE: CURSOS POR PROFESOR                          ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");

        if (profesores.isEmpty()) {
            System.out.println("⚠ No hay profesores registrados.");
            return;
        }

        for (Profesor p : profesores) {
            System.out.println("👤 " + p.getNombre() + " (" + p.getEspecialidad() + "): " +
                    p.getCursos().size() + " curso(s)");
        }

        System.out.println("─────────────────────────────────────────────────────────────────────");
    }
}