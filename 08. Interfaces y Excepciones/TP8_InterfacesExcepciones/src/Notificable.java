/**
 * Interfaz Notificable
 * Define el contrato para objetos que pueden recibir notificaciones
 * Patrón Observer: permite notificar cambios a objetos interesados
 */
public interface Notificable {
    /**
     * Método que recibe notificaciones
     * @param mensaje el mensaje de notificación a recibir
     */
    void notificar(String mensaje);
}