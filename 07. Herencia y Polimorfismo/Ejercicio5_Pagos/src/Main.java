import java.util.ArrayList;

public static void main(String[] args) {
    // Inicializamos un ArrayList del tipo Pagable
    ArrayList<Pagable> formasDePago = new ArrayList<>();

    // Agregamos 3 formas de pago al array
    formasDePago.add(new TarjetaCredito());
    formasDePago.add(new Transferencia());
    formasDePago.add(new Efectivo());

    // Recorremos el array y llamamos al método procesarPago
    for (Pagable p : formasDePago) {
        procesarPago(p);
    }
}

/**
 * Procesa un pago utilizando cualquier objeto que implemente la interfaz
 * Pagable.
 *
 * @param medio el medio de pago a procesar. Puede ser una instancia de
 *              TarjetaCredito, Transferencia, Efectivo, u otra clase que implemente
 *              Pagable.
 */
public static void procesarPago(Pagable medio) {
    medio.pagar();
}