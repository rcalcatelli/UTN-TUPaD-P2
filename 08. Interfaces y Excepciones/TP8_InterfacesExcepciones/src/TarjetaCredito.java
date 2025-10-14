/**
 * Clase TarjetaCredito
 * Implementa PagoConDescuento (que extiende Pago)
 * Permite procesar pagos y aplicar descuentos con tarjeta
 */
public class TarjetaCredito implements PagoConDescuento {
    private String numeroTarjeta;
    private String titular;
    private double porcentajeDescuento; // Ej: 0.10 para 10% de descuento

    /**
     * Constructor
     * @param numeroTarjeta número de la tarjeta
     * @param titular nombre del titular
     * @param porcentajeDescuento descuento aplicable (ej: 0.15 = 15%)
     */
    public TarjetaCredito(String numeroTarjeta, String titular, double porcentajeDescuento) {
        this.numeroTarjeta = numeroTarjeta;
        this.titular = titular;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    /**
     * Implementación del método procesarPago de la interfaz Pago
     * Simula el procesamiento de un pago con tarjeta
     * @param monto el monto a pagar
     * @return true si el pago fue exitoso
     */
    @Override
    public boolean procesarPago(double monto) {
        System.out.println("\n--- Procesando Pago con Tarjeta de Crédito ---");
        System.out.println("Titular: " + titular);
        System.out.println("Tarjeta: **** **** **** " +
                numeroTarjeta.substring(Math.max(0, numeroTarjeta.length() - 4)));
        System.out.println(String.format("Monto a cobrar: $%.2f", monto));
        System.out.println("Pago procesado exitosamente con Tarjeta de Crédito");
        return true;
    }

    /**
     * Implementación del método aplicarDescuento de PagoConDescuento
     * Aplica el descuento configurado para esta tarjeta
     * @param monto el monto original
     * @return el monto con descuento aplicado
     */
    @Override
    public double aplicarDescuento(double monto) {
        double descuento = monto * porcentajeDescuento;
        double montoFinal = monto - descuento;
        System.out.println(String.format("Descuento aplicado: %.0f%% ($%.2f)",
                porcentajeDescuento * 100, descuento));
        System.out.println(String.format("Monto original: $%.2f → Monto final: $%.2f",
                monto, montoFinal));
        return montoFinal;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public String getTitular() {
        return titular;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }
}