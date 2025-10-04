public class TarjetaCredito implements Pagable {

    // Implementamos el método de la interfaz
    @Override
    public void pagar() {
        System.out.println("Pago realizado con tarjeta de credito");
    }
}
