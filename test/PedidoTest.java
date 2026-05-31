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

        pedido.setEstado(Pedido.ESTADO_PAGADO); // Forzamos el estado a PAGADO
        pedido.agregarProducto(new Producto("Pizza", 7.0));

        assertEquals(0, pedido.getProductos().size());
    }

    @Test
    public void comprobarEliminarProductoActualizaPrecio() {
        Cliente c = new Cliente("Pepe", "Garcia", "666111222", "Dir");
        Pedido pedido = new Pedido(c);

        pedido.agregarProducto(new Producto("Pizza", 10.0));
        pedido.agregarProducto(new Producto("Agua", 2.0));

        pedido.eliminarProducto(0); // Eliminamos el elemento en la posición 0

        // Debería quedar solo 1 producto y el total debería haber bajado a 2.0
        assertEquals(1, pedido.getProductos().size());
        assertEquals(2.0, pedido.getImporteTotal());
    }

    @Test
    public void comprobarFlujoCompletoDePago() {
        Cliente c = new Cliente("Pepe", "Garcia", "666111222", "Dir");
        Pedido pedido = new Pedido(c);
        pedido.agregarProducto(new Producto("Hamburguesa", 5.0));

        // Simulamos que el cliente paga con tarjeta
        boolean exito = pedido.pagar(Pedido.PAGO_TARJETA, "1111222233334444", 0);

        // Verificamos que el pago da true y que el estado del pedido ha cambiado a pagado
        assertTrue(exito);
        assertEquals(Pedido.ESTADO_PAGADO, pedido.getEstado());
    }
}