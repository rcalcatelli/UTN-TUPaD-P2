import java.util.ArrayList;
import java.util.List;

/**
 * Clase Pedido
 * Representa un pedido que contiene múltiples productos
 * Implementa Pagable para calcular el total del pedido
 * Puede notificar a clientes sobre cambios de estado
 */
public class Pedido implements Pagable {
    // Atributos privados
    private int numeroPedido;
    private List<Producto> productos;
    private String estado; // "Pendiente", "Procesando", "Enviado", "Entregado"
    private Notificable cliente; // Referencia al cliente para notificar

    /**
     * Constructor
     * @param numeroPedido identificador único del pedido
     */
    public Pedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
        this.productos = new ArrayList<>();
        this.estado = "Pendiente";
    }

    /**
     * Asigna un cliente notificable al pedido
     * @param cliente objeto que implementa Notificable
     */
    public void setCliente(Notificable cliente) {
        this.cliente = cliente;
    }

    /**
     * Agrega un producto al pedido
     * @param producto el producto a agregar
     */
    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    /**
     * Elimina un producto del pedido
     * @param producto el producto a eliminar
     * @return true si se eliminó, false si no existía
     */
    public boolean eliminarProducto(Producto producto) {
        return productos.remove(producto);
    }

    /**
     * Obtiene la lista de productos
     * @return lista de productos del pedido
     */
    public List<Producto> getProductos() {
        return productos;
    }

    /**
     * Cambia el estado del pedido y notifica al cliente
     * @param nuevoEstado el nuevo estado del pedido
     */
    public void cambiarEstado(String nuevoEstado) {
        String estadoAnterior = this.estado;
        this.estado = nuevoEstado;

        // Si hay un cliente asignado, lo notificamos del cambio
        if (cliente != null) {
            String mensaje = String.format(
                    "Pedido #%d cambió de estado: %s → %s",
                    numeroPedido, estadoAnterior, nuevoEstado
            );
            cliente.notificar(mensaje);
        }
    }

    /**
     * Obtiene el estado actual del pedido
     * @return el estado del pedido
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Obtiene el número de pedido
     * @return el número de pedido
     */
    public int getNumeroPedido() {
        return numeroPedido;
    }

    /**
     * Implementación del método calcularTotal() de Pagable
     * Suma los precios de todos los productos del pedido
     * @return el total del pedido
     */
    @Override
    public double calcularTotal() {
        double total = 0;
        for (Producto producto : productos) {
            // Usamos el método calcularTotal() de cada producto
            total += producto.calcularTotal();
        }
        return total;
    }

    /**
     * Método toString para mostrar información del pedido
     * @return cadena con detalles del pedido
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Pedido #%d - Estado: %s\n", numeroPedido, estado));
        sb.append("Productos:\n");
        for (Producto p : productos) {
            sb.append("  - ").append(p.toString()).append("\n");
        }
        sb.append(String.format("Total: $%.2f", calcularTotal()));
        return sb.toString();
    }
}