/**
 * EdadInvalidaException
 * Excepción personalizada para validar edades
 * Extiende Exception (checked exception) para forzar su manejo
 *
 * Las excepciones personalizadas permiten crear reglas de negocio específicas
 * y manejarlas de manera diferenciada en la aplicación
 */
public class EdadInvalidaException extends Exception {

    /**
     * Constructor sin parámetros
     * Crea la excepción con un mensaje por defecto
     */
    public EdadInvalidaException() {
        super("Edad inválida: debe estar entre 0 y 120 años");
    }

    /**
     * Constructor con mensaje personalizado
     * @param mensaje el mensaje de error específico
     */
    public EdadInvalidaException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor con mensaje y causa
     * Útil para encadenar excepciones
     * @param mensaje el mensaje de error
     * @param causa la excepción que causó este error
     */
    public EdadInvalidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}