/**
 * Interfaz Pago
 * Define el contrato para procesar pagos
 * Cualquier medio de pago debe implementar este método
 */
interface Pago {
    /**
     * Procesa un pago por el monto especificado
     * @param monto el monto a pagar
     * @return true si el pago fue exitoso, false en caso contrario
     */
    boolean procesarPago(double monto);
}