import java.util.Date;
import java.util.ArrayList;

public class Pedido {

    // --- CONSTANTES DE ESTADO ---
    public static final int ESTADO_PENDIENTE = 0; // Estado inicial antes de pagar
    public static final int ESTADO_PAGADO = 1;
    public static final int ESTADO_PREPARANDO = 2;
    public static final int ESTADO_LISTO = 3;
    public static final int ESTADO_SERVIDO = 4;

    // --- CONSTANTES DE TIPO DE PAGO ---
    public static final int PAGO_EFECTIVO = 1;
    public static final int PAGO_TARJETA = 2;
    public static final int PAGO_CUENTA = 3;

    // --- ATRIBUTOS ---
    private Cliente cliente;
    private Date fechaHora;
    private ArrayList<Producto> productos;
    private double importeTotal;
    private PasarelaDePago pago;
    private int estado;

    // --- CONSTRUCTOR ---
    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.productos = new ArrayList<Producto>();
        this.importeTotal = 0.0;
        this.estado = ESTADO_PENDIENTE;
    }

    // --- MÉTODOS GET Y SET ---

    public Cliente getCliente() { return cliente; }

    public Date getFechaHora() { return fechaHora; }

    public ArrayList<Producto> getProductos() { return productos; }

    public double getImporteTotal() { return importeTotal; }

    public PasarelaDePago getPago() { return pago; }

    public int getEstado() { return estado; }

    // No se puede empezar a preparar un pedido si no está pagado
    public void setEstado(int nuevoEstado) {
        if (this.estado == ESTADO_PENDIENTE && nuevoEstado != ESTADO_PAGADO) {
            System.out.println("ERROR: No se puede avanzar el estado. El pedido aún no está pagado.");
        } else {
            this.estado = nuevoEstado;
        }
    }
    // --- MÉTODOS ---

    public void agregarProducto(Producto producto) {
        // Solo podemos agregar si el pedido no está pagado osea está en PENDIENTE
        if (this.estado != ESTADO_PENDIENTE) {
            System.out.println("ERROR: No se pueden añadir productos. El pedido ya está pagado o en proceso.");
            return;
        }

        if (producto != null) {
            this.productos.add(producto);
            this.actualizarImporteTotal();
        } else {
            System.out.println("ERROR: El producto a añadir es nulo.");
        }
    }

    public void eliminarProducto(int posicion) {
        // Solo podemos eliminar si el pedido no está pagado
        if (this.estado != ESTADO_PENDIENTE) {
            System.out.println("ERROR: No se pueden eliminar productos de un pedido que ya está pagado.");
            return;
        }

        // Comprobamos que la posición exista dentro del ArrayList para que el programa no pete
        if (posicion >= 0 && posicion < this.productos.size()) {
            this.productos.remove(posicion);
            this.actualizarImporteTotal();
        } else {
            System.out.println("ERROR: Posición no válida. No hay producto en la posición " + posicion);
        }
    }

    // --- MÉT ODO PRIVADO ---
    // Recalcula el precio total recorriendo el array de productos
    private void actualizarImporteTotal() {
        double suma = 0.0;

        for (int i = 0; i < this.productos.size(); i++) {
            Producto p = this.productos.get(i);
            suma = suma + p.getPrecio();
        }
        this.importeTotal = Math.round(suma * 100.0) / 100.0;
    }

    // --- MÉTODOS: PAGO Y TICKET ---

    // Le pasamos el tipo de pago, nuestras constantes, un texto para tarjeta o cuenta y una cantidad para efectivo
    public boolean pagar(int tipoPago, String datoExtra, float cantidad) {
        // 1. Comprobamos que el pedido esté PENDIENTE
        if (this.estado != ESTADO_PENDIENTE) {
            System.out.println("ERROR: Este pedido ya ha sido pagado o está en proceso.");
            return false;
        }

        // 2. Comprobamos que no esté vacío
        if (this.productos.size() == 0) {
            System.out.println("ERROR: El pedido está vacío, no hay nada que cobrar.");
            return false;
        }

        // 3. Creamos la pasarela de pago con el importe total del pedido
        this.pago = new PasarelaDePago(this.importeTotal);
        boolean pagoCorrecto = false;

        // 4. Según el tipo de pago elegido, llamamos a un mét odo u otro
        if (tipoPago == PAGO_EFECTIVO) {
            pagoCorrecto = this.pago.Efectivo(cantidad);
        } else if (tipoPago == PAGO_TARJETA) {
            pagoCorrecto = this.pago.Tarjeta(datoExtra);
        } else if (tipoPago == PAGO_CUENTA) {
            pagoCorrecto = this.pago.Cuenta(datoExtra);
        } else {
            System.out.println("ERROR: Tipo de pago seleccionado no existe.");
            return false;
        }

        // Si la pasarela nos devuelve true, el pago se ha realizado con éxito
        if (pagoCorrecto == true) {
            this.estado = ESTADO_PAGADO;
            this.fechaHora = new Date();
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        String ticket = "CANT.\t\tPRODUCTO\t\tPRECIO UD.\tTOTAL\n";
        ticket = ticket + "=====\t\t=========\t\t=========\t=====\n";

        // Creamos dos listas auxiliares para agrupar
        ArrayList<Producto> unicos = new ArrayList<Producto>();
        ArrayList<Integer> cantidades = new ArrayList<Integer>();

        // Recorremos todos los productos del pedido
        for (int i = 0; i < this.productos.size(); i++) {
            Producto pActual = this.productos.get(i);

            int posicion = unicos.indexOf(pActual);

            if (posicion == -1) {
                unicos.add(pActual);
                cantidades.add(1);
            } else {
                int cantActual = cantidades.get(posicion);
                cantidades.set(posicion, cantActual + 1);
            }
        }

        // Ahora recorremos nuestra lista de productos agrupados para montar las líneas del ticket
        for (int i = 0; i < unicos.size(); i++) {
            Producto p = unicos.get(i);
            int cant = cantidades.get(i);

            // Calculamos el total de esa línea
            double totalLinea = Math.round((p.getPrecio() * cant) * 100.0) / 100.0;

            ticket = ticket + cant + "\t\t" + p.getNombre() + "\t\t" + p.getPrecio() + " €\t\t" + totalLinea + " €\n";
        }

        ticket = ticket + "TOTAL --------------------------------------------> " + this.importeTotal + " €\n";

        return ticket;
    }
}
