import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class GestionPedidos {

    // --- ATRIBUTOS ---
    private HashMap<String, Cliente> clientes;
    private ArrayList<Producto> cartaProductos;
    private Scanner sc;

    /**
     * Constructor que inicializa las estructuras de datos y carga los clientes
     * y productos de prueba.
     */
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

        // Creamos un cliente
        Cliente c1 = new Cliente("Juan", "Perez", "776245688", "Calle 123");

        // Lo metemos en el HashMap. La clave es su teléfono
        this.clientes.put(c1.getTelefono(), c1);
    }

    // --- BUCLE PRINCIPAL ---
    /**
     * Inicia el bucle principal de la aplicación por consola,
     * pidiendo el teléfono del cliente para buscarlo en el sistema.
     */
    public void iniciar() {

        System.out.println("=========================================");
        System.out.println("   SISTEMA DE GESTIÓN DE PEDIDOS");
        System.out.println("=========================================");
        System.out.print("¿Deseas cargar los datos de una sesión anterior? (S/N): ");
        String respCarga = this.sc.nextLine();

        if (respCarga.equalsIgnoreCase("S")) {
            System.out.print("Introduce el nombre del fichero: ");
            String ficheroCargar = this.sc.nextLine();
            this.recuperarDatos(ficheroCargar);
        }
        System.out.println("Iniciando el sistema...\n");

        boolean salir = false;

        // Bucle que se repite hasta que el usuario escriba 0
        while (salir == false) {
            System.out.print("\nINTRODUZCA TELÉFONO (0 SALIR) :");
            String tlfInput = this.sc.nextLine();

            // Limpiamos los espacios
            String tlfLimpio = tlfInput.replace(" ", "");

            if (tlfLimpio.equals("0")) {
                // Preguntar si desea guardar antes de salir
                System.out.println("\n--- CERRANDO SESIÓN ---");
                System.out.print("¿Deseas guardar todos los datos actuales en un fichero? (S/N): ");
                String respGuardar = this.sc.nextLine();

                if (respGuardar.equalsIgnoreCase("S")) {
                    System.out.print("Introduce el nombre del fichero para guardar (ejemplo: datos.dat): ");
                    String ficheroGuardar = this.sc.nextLine();
                    this.guardarDatos(ficheroGuardar);
                }

                System.out.println("GRACIAS POR USAR NUESTRO SOFTWARE!.");
                salir = true; // Aquí ya cambia a true y el bucle while terminará
            }
        }
    }

    /**
     * Añadir productos al carrito mediante teclado.
     *
     * @param cliente El cliente que está realizando la compra.
     */
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
    public void procesarPago(Pedido pedido, Cliente cliente) {
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
            // Cambiamos comas por puntos para que valgan las dos opciones
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
    /**
     * Guarda el HashMap de clientes y la lista de productos en un archivo binario.
     * Usa la serialización de objetos de Java para almacenar los datos en el disco duro.
     *
     * @param nombreFichero El nombre o ruta del archivo donde se guardará la partida.
     */
    public void guardarDatos(String nombreFichero) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreFichero))) {
            // Guardamos primero el mapa de clientes (que arrastra su historial de pedidos)
            oos.writeObject(this.clientes);
            // Guardamos después la carta de productos de la aplicación
            oos.writeObject(this.cartaProductos);

            System.out.println("Datos guardados correctamente en el fichero: " + nombreFichero);
        } catch (IOException e) {
            System.out.println("ERROR al guardar los datos en el archivo: " + e.getMessage());
        }
    }
    /**
     * Recupera el HashMap de clientes y la lista de productos desde un archivo binario.
     * Reconstruye los objetos en la memoria RAM mediante deserialización.
     *
     * @param nombreFichero El nombre del archivo desde donde se cargarán los datos.
     * @return true si los datos se cargaron con éxito, false si el archivo no existía o falló.
     */
    public boolean recuperarDatos(String nombreFichero) {
        try (ObjectInputStream ois = new ObjectInputStream(new java.io.FileInputStream(nombreFichero))) {

            // Leemos los datos del archivo y los guardamos en variables temporales
            HashMap<String, Cliente> clientesCargados = (HashMap<String, Cliente>) ois.readObject();
            ArrayList<Producto> productosCargados = (ArrayList<Producto>) ois.readObject();

            // Usamos putAll para sumarlos a los actuales
            this.clientes.putAll(clientesCargados);

            System.out.println("Datos recuperados y fusionados con éxito desde el fichero: " + nombreFichero);
            return true;
        } catch (Exception e) {
            System.out.println("AVISO: No se pudieron cargar los datos previos (" + e.getMessage() + ").");
            return false;
        }
    }

    /**
     * Busca un cliente en el sistema por su número de teléfono.
     * @param telefono El teléfono introducido en el formulario.
     * @return El objeto Cliente si existe, o null si no se encuentra.
     */
    public Cliente buscarCliente(String telefono) {
        String tlfLimpio = telefono.replace(" ", "");
        return this.clientes.get(tlfLimpio);
    }

    /**
     * Permite a la interfaz gráfica lanzar el menú de pedido en consola
     * para un cliente concreto que ya ha sido localizado.
     * @param cliente El cliente al que se va a atender.
     */
    public void lanzarPedidoConsola(Cliente cliente) {
        this.crearPedido(cliente);
    }

    public ArrayList<Producto> getCartaProductos() {
        return this.cartaProductos;
    }


}
