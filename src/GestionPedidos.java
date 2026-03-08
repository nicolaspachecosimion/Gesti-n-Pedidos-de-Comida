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
        Cliente c1 = new Cliente("Juan", "Perez", "776245688", "Calle 123");

        // Lo metemos en el HashMap. La clave es su teléfono
        this.clientes.put(c1.getTelefono(), c1);
    }

    // --- BUCLE PRINCIPAL ---
    public void iniciar() {
        boolean salir = false;

        // Bucle que se repite hasta que el usuario escriba 0
        while (salir == false) {
            System.out.print("\nINTRODUZCA TELÉFONO (0 SALIR) :");
            String tlfInput = this.sc.nextLine();

            // Limpiamos los espacios"
            String tlfLimpio = tlfInput.replace(" ", "");

            if (tlfLimpio.equals("0")) {
                System.out.println("GRACIAS POR USAR NUESTRO SOFTWARE!.");
                salir = true;
            } else {
                // Buscamos el cliente en nuestro HashMap usando el teléfono limpio
                // El mét odo .get() devuelve el Cliente si lo encuentra, o null si no existe
                Cliente clienteActual = this.clientes.get(tlfLimpio);

                if (clienteActual == null) {
                    System.out.println("ERROR: El cliente no existe!");
                } else {
                    this.crearPedido(clienteActual);
                }
            }
        }
    }

    private void crearPedido(Cliente cliente) {
        // De momento solo ponemos esto para probar que lo encuentra bien
        System.out.println("Cliente encontrado: " + cliente.getNombre() + " " + cliente.getApellidos());
        System.out.println("Próximamente: Lógica de añadir productos...");
    }

    // --- MAIN ---
    public static void main(String[] args) {
        GestionPedidos app = new GestionPedidos();
        app.iniciar();
    }
}
