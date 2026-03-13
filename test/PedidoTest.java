import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PedidoTest {

    @Test
    public void comprobarSumaDeImporteTotal() {
        Cliente c = new Cliente("Pepe", "Garcia", "666111222", "Dir");
        Pedido pedido = new Pedido(c);

        pedido.agregarProducto(new Producto("Agua", 1.0));
        pedido.agregarProducto(new Producto("Pan", 1.5));

        assertEquals(2.5, pedido.getImporteTotal());
    }

    @Test
    public void comprobarNoAñadirSiEstaPagado() {
        Cliente c = new Cliente("Pepe", "Garcia", "666111222", "Dir");
        Pedido pedido = new Pedido(c);

        // Forzamos el estado a PAGADO
        pedido.setEstado(Pedido.ESTADO_PAGADO);

        // Intentamos añadir un producto
        pedido.agregarProducto(new Producto("Pizza", 7.0));

        // El array de productos debe tener tamaño 0 porque nuestra seguridad lo impidió
        assertEquals(0, pedido.getProductos().size());
    }
}