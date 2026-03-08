import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class GestionPedidos {

    // --- ATRIBUTOS ---
    private HashMap<String, Cliente> clientes;
    private ArrayList<Producto> cartaProductos;
    private Scanner sc;

    // --- CONSTRUCTOR ---
    public GestionPedidos() {
        this.clientes = new HashMap<String, Cliente>();
        this.cartaProductos = new ArrayList<Producto>();
        this.sc = new Scanner(System.in);

        this.cargarDatosDePrueba();
    }

    // --- MÉT ODO PARA CARGAR DATOS ---
    private void cargarDatosDePrueba() {
        this.cartaProductos.add(new Producto("PIZZA", 7.55));
        this.cartaProductos.add(new Producto("AGUA", 0.35));
        this.cartaProductos.add(new Producto("BOCADILLO", 3.55));
        this.cartaProductos.add(new Producto("HAMBURGUESA", 3.75));
        this.cartaProductos.add(new Producto("COCACOLA", 0.59));
        this.cartaProductos.add(new Producto("CERVEZA", 0.75));

        // Ordenamos la lista alfabéticamente
        Collections.sort(this.cartaProductos);

        // Creamos un cliente de prueba
        Cliente c1 = new Cliente("Juan", "Perez", "776245688", "Calle Falsa 123");

        // Lo metemos en el HashMap. La clave es su teléfono
        this.clientes.put(c1.getTelefono(), c1);
    }

    // --- MÉT ODO PRINCIPAL DEL PROGRAMA ---
    public void iniciar() {
        System.out.println("Bienvenido al sistema de pedidos.");
    }

    // --- MAIN ---
    public static void main(String[] args) {
        GestionPedidos app = new GestionPedidos();
        app.iniciar();
    }
}
