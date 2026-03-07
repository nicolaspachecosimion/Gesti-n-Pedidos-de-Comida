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
}
