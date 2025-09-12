// Clase Contribuyente
public class Contribuyente {
    private String nombre;
    private String cuil;

    public Contribuyente(String nombre, String cuil) {
        this.nombre = nombre;
        this.cuil = cuil;
    }

    public String getNombre() { return nombre; }
    public String getCuil() { return cuil; }
}

// Clase Impuesto
public class Impuesto {
    private double monto;
    private Contribuyente contribuyente; // Asociación unidireccional

    public Impuesto(double monto, Contribuyente contribuyente) {
        this.monto = monto;
        this.contribuyente = contribuyente;
    }

    public double getMonto() { return monto; }
    public Contribuyente getContribuyente() { return contribuyente; }
}

// Clase Calculadora
public class Calculadora {
    // Dependencia de uso: usa Impuesto como parámetro pero no la almacena como atributo
    public void calcular(Impuesto impuesto) {
        double impuestoCalculado = impuesto.getMonto() * 1.21; // Ejemplo: IVA 21%
        System.out.println("Impuesto calculado para " +
                impuesto.getContribuyente().getNombre() +
                ": $" + impuestoCalculado);
    }
}

