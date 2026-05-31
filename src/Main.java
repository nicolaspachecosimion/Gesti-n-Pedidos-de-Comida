/**
 * Clase principal que arranca la aplicación de Gestión de Pedidos de Comida.
 * * @author TuNombre
 * @version 1.0
 */
public class Main {
    /**
     * Instancia el gestor y arranca el bucle principal.
     *
     * @param args
     */
    public static void main(String[] args) {
        GestionPedidos app = new GestionPedidos();
        // Arrancamos la ventana gráfica
        MenuGrafico.iniciarVentana(app);
    }
}