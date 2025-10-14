/**
 * Interfaz PagoConDescuento
 * Extiende la interfaz Pago añadiendo funcionalidad de descuentos
 * Herencia múltiple: una clase puede implementar ambas interfaces
 */
interface PagoConDescuento extends Pago {
    /**
     * Aplica un descuento al monto total
     * @param monto el monto original
     * @return el monto con descuento aplicado
     */
    double aplicarDescuento(double monto);
}