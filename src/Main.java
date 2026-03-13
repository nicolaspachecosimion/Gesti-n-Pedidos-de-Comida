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
        // Instanciamos nuestra clase gestora y arrancamos el programa
        GestionPedidos app = new GestionPedidos();
        app.iniciar();
    }
}