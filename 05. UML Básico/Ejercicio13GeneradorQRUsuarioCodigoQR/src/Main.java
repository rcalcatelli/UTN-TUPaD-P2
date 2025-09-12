// Clase Usuario
public class Usuario {
    private String nombre;
    private String email;

    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
}

// Clase CodigoQR
public class CodigoQR {
    private String valor;
    private Usuario usuario; // Asociación unidireccional

    public CodigoQR(String valor, Usuario usuario) {
        this.valor = valor;
        this.usuario = usuario;
    }

    public String getValor() { return valor; }
    public Usuario getUsuario() { return usuario; }
}

// Clase GeneradorQR
public class GeneradorQR {
    // Dependencia de creación: crea CodigoQR dentro del método pero no lo conserva como atributo
    public void generar(String valor, Usuario usuario) {
        CodigoQR codigoQR = new CodigoQR(valor, usuario); // Crea el objeto
        System.out.println("Código QR generado: " + codigoQR.getValor() +
                " para usuario: " + codigoQR.getUsuario().getNombre());
        // El objeto CodigoQR se crea y se usa localmente, no se almacena como atributo
    }

