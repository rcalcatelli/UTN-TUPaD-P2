/**
 * Interfaz Pagable
 * Define el contrato para todos los elementos que pueden calcular un total
 * Esta interfaz permite polimorfismo: cualquier clase que la implemente
 * podrá ser tratada como un objeto Pagable
 */
public interface Pagable {
    /**
     * Método que debe implementarse para calcular el total
     * @return el monto total calculado
     */
    double calcularTotal();
}