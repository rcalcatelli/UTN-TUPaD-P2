/**
 * Clase Cliente
 * Representa un cliente del e-commerce
 * Implementa Notificable para recibir notificaciones sobre sus pedidos
 */
public class Cliente implements Notificable {
    private String nombre;
    private String email;
    private String telefono;

    /**
     * Constructor
     * @param nombre nombre del cliente
     * @param email correo electrónico del cliente
     * @param telefono teléfono de contacto
     */
    public Cliente(String nombre, String email, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    /**
     * Implementación del método notificar de la interfaz Notificable
     * Este método se ejecuta cuando hay cambios en los pedidos del cliente
     * @param mensaje el mensaje de notificación
     */
    @Override
    public void notificar(String mensaje) {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║           NOTIFICACIÓN PARA CLIENTE                ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println("Cliente: " + nombre);
        System.out.println("Email: " + email);
        System.out.println("Mensaje: " + mensaje);
        System.out.println("──────────────────────────────────────────────────────");

        // En un sistema real, aquí se enviaría un email o SMS
        // System.out.println("✉ Email enviado a: " + email);
        // System.out.println("📱 SMS enviado a: " + telefono);
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return String.format("Cliente: %s (%s)", nombre, email);
    }
}