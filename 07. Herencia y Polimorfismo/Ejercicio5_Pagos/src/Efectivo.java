public class Efectivo implements Pagable {

    // Implementamos el método de la interfaz
    @Override
    public void pagar() {
        System.out.println("Pago realizado con efectivo");
    }
}
