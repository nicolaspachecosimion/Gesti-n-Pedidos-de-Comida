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

    // --- CREAR PEDIDO Y SELECCIONAR PRODUCTOS ---
    private void crearPedido(Cliente cliente) {
        // Iniciamos un pedido asignado a este cliente
        Pedido nuevoPedido = cliente.realizarPedido();
        boolean construyendoPedido = true;

        while (construyendoPedido == true) {
            System.out.println("\nRealizando pedido... Su pedido:");
            System.out.print(nuevoPedido.toString()); // Imprime el ticket actual

            System.out.println("\nAgregue los productos que desee a su pedido (0 para finalizar el pedido):");
            // Recorremos la carta de productos
            for (int i = 0; i < this.cartaProductos.size(); i++) {
                Producto p = this.cartaProductos.get(i);
                System.out.println((i + 1) + ".- " + p.getNombre() + "\t\t" + p.getPrecio() + "€");
            }

            System.out.print("Elige un producto: ");
            String opcionStr = this.sc.nextLine();

            // Comprobamos si nos han escrito un número válido
            boolean esNumero = true;
            if (opcionStr.length() == 0) {
                esNumero = false;
            }
            for (int i = 0; i < opcionStr.length(); i++) {
                if (opcionStr.charAt(i) < '0' || opcionStr.charAt(i) > '9') {
                    esNumero = false;
                }
            }

            if (esNumero == true) {
                int seleccion = Integer.parseInt(opcionStr); // Convertimos el texto a número

                if (seleccion == 0) {
                    construyendoPedido = false;
                } else if (seleccion > 0 && seleccion <= this.cartaProductos.size()) {
                    Producto productoElegido = this.cartaProductos.get(seleccion - 1);
                    nuevoPedido.agregarProducto(productoElegido);
                } else {
                    System.out.println("ERROR: Opción fuera de rango.");
                }
            } else {
                System.out.println("ERROR: Debe introducir un número válido.");
            }
        }

        // Resumen final
        System.out.println("\nResumen de su pedido:");
        System.out.print(nuevoPedido.toString());

        System.out.print("¿Continuar al pago? (S/N): ");
        String continuar = this.sc.nextLine().trim().toUpperCase();

        if (continuar.equals("S")) {
            this.procesarPago(nuevoPedido, cliente);
        } else {
            System.out.println("Pedido cancelado.");
        }
    }

    // --- EL PAGO DEL PEDIDO ---
    private void procesarPago(Pedido pedido, Cliente cliente) {
        if (pedido.getImporteTotal() == 0.0) {
            System.out.println("El pedido está vacío. No hay nada que cobrar.");
            return;
        }

        System.out.println("\nIMPORTE " + pedido.getImporteTotal() + " €.");
        System.out.println("1.- EFECTIVO.");
        System.out.println("2.- TARJETA.");
        System.out.println("3.- CUENTA.");
        System.out.print("Seleccione un método de pago: ");

        String metodoStr = this.sc.nextLine();
        boolean pagoCompletado = false;

        if (metodoStr.equals("1")) {
            System.out.print("Introduce la cantidad a entregar (ej: 123.45): ");
            // Cambiamos comas por puntos por si el usuario lo escribe con coma
            String cantStr = this.sc.nextLine().replace(",", ".");

            // Validacion de decimales
            boolean esDecimal = true;
            if (cantStr.length() == 0) esDecimal = false;
            for (int i = 0; i < cantStr.length(); i++) {
                char c = cantStr.charAt(i);
                if ((c < '0' || c > '9') && c != '.') {
                    esDecimal = false;
                }
            }

            if (esDecimal == true) {
                float cantidad = Float.parseFloat(cantStr);
                pagoCompletado = pedido.pagar(Pedido.PAGO_EFECTIVO, "", cantidad);
            } else {
                System.out.println("ERROR: Cantidad numérica no válida.");
            }

        } else if (metodoStr.equals("2")) {
            System.out.print("Introduce el número de tarjeta: ");
            String tarjeta = this.sc.nextLine();
            pagoCompletado = pedido.pagar(Pedido.PAGO_TARJETA, tarjeta, 0);

        } else if (metodoStr.equals("3")) {
            System.out.print("Introduce el número de cuenta: ");
            String cuenta = this.sc.nextLine();
            pagoCompletado = pedido.pagar(Pedido.PAGO_CUENTA, cuenta, 0);

        } else {
            System.out.println("ERROR: Método de pago no reconocido.");
        }

        // Si el pago ha ido bien, guardamos el pedido en el historial del cliente
        if (pagoCompletado == true) {
            System.out.println("OPERACIÓN REALIZADA CON ÉXITO.");
            System.out.println("PEDIDO: " + pedido.getPago().getCodigoPago() + " ESTADO: PAGADO");
            cliente.agregarPedido(pedido); // ¡Lo añadimos a su historial!
        } else {
            System.out.println("La operación de pago no se pudo completar.");
        }
    }
}
