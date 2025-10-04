import java.util.ArrayList;

public static void main(String[] args) {
    // Inicializamos un array de animales
    ArrayList<Animal> animales = new ArrayList<>();

    // Creamos y agregamos animales al array
    Perro p1 = new Perro();
    Gato g1 = new Gato();
    Vaca v1 = new Vaca();

    animales.add(p1);
    animales.add(g1);
    animales.add(v1);

    // Recorremos el array y llamamos al método hacerSonido
    // Cada animal emite su propio sonido gracias al polimorfismo
    for (Animal a : animales) {
        a.hacerSonido();
    }
}