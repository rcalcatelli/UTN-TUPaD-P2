/**
 * Clase PayPal
 * Implementa PagoConDescuento (que extiende Pago)
 * Permite procesar pagos y aplicar descuentos mediante PayPal
 */
public class PayPal implements PagoConDescuento {
    private String email;
    private double porcentajeDescuento;

    /**
     * Constructor
     * @param email correo electrónico asociado a la cuenta PayPal
     * @param porcentajeDescuento descuento aplicable (ej: 0.05 = 5%)
     */
    public PayPal(String email, double porcentajeDescuento) {
        this.email = email;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    /**
     * Implementación del método procesarPago de la interfaz Pago
     * Simula el procesamiento de un pago con PayPal
     * @param monto el monto a pagar
     * @return true si el pago fue exitoso
     */
    @Override
    public boolean procesarPago(double monto) {
        System.out.println("\n--- Procesando Pago con PayPal ---");
        System.out.println("Cuenta PayPal: " + email);
        System.out.println(String.format("Monto a cobrar: $%.2f", monto));
        System.out.println("Redirigiendo a PayPal para autenticación...");
        System.out.println("Pago procesado exitosamente con PayPal");
        return true;
    }

    /**
     * Implementación del método aplicarDescuento de PagoConDescuento
     * Aplica el descuento configurado para PayPal
     * @param monto el monto original
     * @return el monto con descuento aplicado
     */
    @Override
    public double aplicarDescuento(double monto) {
        double descuento = monto * porcentajeDescuento;
        double montoFinal = monto - descuento;
        System.out.println(String.format("Descuento PayPal aplicado: %.0f%% ($%.2f)",
                porcentajeDescuento * 100, descuento));
        System.out.println(String.format("Monto original: $%.2f → Monto final: $%.2f",
                monto, montoFinal));
        return montoFinal;
    }

    public String getEmail() {
        return email;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }
}