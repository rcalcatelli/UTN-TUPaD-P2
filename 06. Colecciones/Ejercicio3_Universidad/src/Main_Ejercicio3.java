/**
 * Clase principal para ejecutar el Ejercicio 3: Universidad, Profesor y Curso
 * Implementa las 8 tareas requeridas en el trabajo práctico.
 * Demuestra el uso de relación bidireccional 1 a N entre Profesor y Curso.
 *
 * @author Renzo Calcatelli - UTN - Programación II
 * @version 1.0
 */
public class Main_Ejercicio3 {
    public static void main(String[] args) {
        // Encabezado del programa
        System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║    TRABAJO PRÁCTICO 6 - EJERCICIO 3: UNIVERSIDAD (Bidireccional)     ║");
        System.out.println("║                 Universidad Tecnológica Nacional                     ║");
        System.out.println("║                      Programación II                                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝\n");

        // Crear la universidad
        Universidad universidad = new Universidad("Universidad Tecnológica Nacional");
        System.out.println("🏛️  Universidad creada: " + universidad.getNombre() + "\n");

        // ===================================================================
        // TAREA 1: Crear al menos 3 profesores y 5 cursos
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 1: Creación de profesores y cursos");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // Crear profesores
        Profesor prof1 = new Profesor("PR001", "Dr. Carlos Martínez", "Programación");
        Profesor prof2 = new Profesor("PR002", "Dra. Ana López", "Matemática");
        Profesor prof3 = new Profesor("PR003", "Ing. Roberto Fernández", "Sistemas");

        System.out.println("👨‍🏫 Profesores creados:");
        prof1.mostrarInfo();
        prof2.mostrarInfo();
        prof3.mostrarInfo();

        System.out.println("\n📚 Cursos creados:");
        // Crear cursos (inicialmente sin profesor)
        Curso curso1 = new Curso("C001", "Programación I");
        Curso curso2 = new Curso("C002", "Programación II");
        Curso curso3 = new Curso("C003", "Álgebra");
        Curso curso4 = new Curso("C004", "Análisis Matemático");
        Curso curso5 = new Curso("C005", "Sistemas Operativos");

        System.out.println("  • " + curso1.getCodigo() + ": " + curso1.getNombre());
        System.out.println("  • " + curso2.getCodigo() + ": " + curso2.getNombre());
        System.out.println("  • " + curso3.getCodigo() + ": " + curso3.getNombre());
        System.out.println("  • " + curso4.getCodigo() + ": " + curso4.getNombre());
        System.out.println("  • " + curso5.getCodigo() + ": " + curso5.getNombre());

        // ===================================================================
        // TAREA 2: Agregar profesores y cursos a la universidad
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 2: Registrando en la universidad");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        universidad.agregarProfesor(prof1);
        universidad.agregarProfesor(prof2);
        universidad.agregarProfesor(prof3);

        System.out.println();

        universidad.agregarCurso(curso1);
        universidad.agregarCurso(curso2);
        universidad.agregarCurso(curso3);
        universidad.agregarCurso(curso4);
        universidad.agregarCurso(curso5);

        // ===================================================================
        // TAREA 3: Asignar profesores a cursos
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 3: Asignando profesores a cursos");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        universidad.asignarProfesorACurso("C001", "PR001");
        universidad.asignarProfesorACurso("C002", "PR001");
        universidad.asignarProfesorACurso("C003", "PR002");
        universidad.asignarProfesorACurso("C004", "PR002");
        universidad.asignarProfesorACurso("C005", "PR003");

        // ===================================================================
        // TAREA 4: Listar cursos con su profesor y profesores con sus cursos
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 4: Listados completos");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        universidad.listarCursos();
        universidad.listarProfesores();

        System.out.println("\n--- Detalle de cursos por profesor ---");
        prof1.listarCursos();
        prof2.listarCursos();
        prof3.listarCursos();

        // ===================================================================
        // TAREA 5: Cambiar el profesor de un curso (sincronización)
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 5: Cambio de profesor (sincronización bidireccional)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        System.out.println("📋 Estado ANTES del cambio:");
        System.out.println("Curso 'Programación II' - Profesor: " + curso2.getProfesor().getNombre());
        prof1.listarCursos();
        prof3.listarCursos();

        System.out.println("\n🔄 Cambiando profesor de 'Programación II' de PR001 a PR003...");
        universidad.asignarProfesorACurso("C002", "PR003");

        System.out.println("\n📋 Estado DESPUÉS del cambio:");
        System.out.println("Curso 'Programación II' - Profesor: " + curso2.getProfesor().getNombre());
        prof1.listarCursos();  // Debe tener solo 1 curso ahora
        prof3.listarCursos();  // Debe tener 2 cursos ahora

        System.out.println("\n✅ VERIFICACIÓN DE SINCRONIZACIÓN:");
        System.out.println("   • Curso C002 apunta a: " + curso2.getProfesor().getNombre());
        System.out.println("   • Prof. Martínez tiene " + prof1.getCursos().size() + " curso(s)");
        System.out.println("   • Prof. Fernández tiene " + prof3.getCursos().size() + " curso(s)");

        // ===================================================================
        // TAREA 6: Remover un curso y verificar sincronización
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 6: Eliminación de curso");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        System.out.println("📋 Cursos de Dra. López ANTES de eliminar:");
        prof2.listarCursos();

        System.out.println("\n🗑️  Eliminando curso 'Álgebra'...");
        universidad.eliminarCurso("C003");

        System.out.println("\n📋 Cursos de Dra. López DESPUÉS de eliminar:");
        prof2.listarCursos();

        System.out.println("\n✅ VERIFICACIÓN: El curso se eliminó de la lista del profesor.");

        // ===================================================================
        // TAREA 7: Remover un profesor y dejar cursos sin profesor
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 7: Eliminación de profesor");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        System.out.println("📋 Estado ANTES de eliminar al Dr. Martínez:");
        prof1.listarCursos();
        System.out.println("\nCurso C001 - Profesor: " + curso1.getProfesor().getNombre());

        System.out.println("\n🗑️  Eliminando al Dr. Martínez...");
        universidad.eliminarProfesor("PR001");

        System.out.println("\n📋 Estado DESPUÉS de eliminar:");
        System.out.println("Curso C001 - Profesor: " +
                (curso1.getProfesor() != null ? curso1.getProfesor().getNombre() : "Sin asignar"));

        universidad.listarCursos();

        // ===================================================================
        // TAREA 8: Mostrar reporte de cursos por profesor
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  TAREA 8: Reporte final - Cursos por profesor");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        universidad.mostrarReporteCursosPorProfesor();

        // ===================================================================
        // DEMOSTRACIÓN ADICIONAL: Invariante de asociación
        // ===================================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  DEMOSTRACIÓN: Invariante de asociación bidireccional");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        System.out.println("💡 RELACIÓN BIDIRECCIONAL 1 a N:");
        System.out.println("   • Un Profesor dicta muchos Cursos (1 → N)");
        System.out.println("   • Cada Curso tiene un Profesor (N → 1)");
        System.out.println("\n📌 INVARIANTE DE ASOCIACIÓN:");
        System.out.println("   • Si Curso.profesor apunta a un Profesor P");
        System.out.println("   • Entonces P.cursos debe contener ese Curso");
        System.out.println("   • Y viceversa: si Profesor P tiene un Curso C");
        System.out.println("   • Entonces C.profesor debe apuntar a P");
        System.out.println("\n🔧 SINCRONIZACIÓN AUTOMÁTICA:");
        System.out.println("   • setProfesor() actualiza ambos lados");
        System.out.println("   • agregarCurso() actualiza ambos lados");
        System.out.println("   • eliminarCurso() actualiza ambos lados");
        System.out.println("\n⚠️  IMPORTANTE:");
        System.out.println("   • Siempre usar los métodos públicos");
        System.out.println("   • Nunca modificar las listas directamente");
        System.out.println("   • La consistencia se mantiene automáticamente");

        // Mensaje de finalización
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║         FIN DEL EJERCICIO 3 - UNIVERSIDAD (Bidireccional)           ║");
        System.out.println("║                    ✓ Todas las tareas completadas                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝\n");
    }
}