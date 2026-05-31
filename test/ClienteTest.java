import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    @Test
    public void comprobarTelefonoValidoConEspacios() {
        Cliente c = new Cliente("juan", "perez", "776 24 56 88", "Calle 1");
        assertEquals("776245688", c.getTelefono());
    }

    @Test
    public void comprobarTelefonoInvalidoEmpiezaMal() {
        Cliente c = new Cliente("juan", "perez", "123456789", "Calle 1");
        assertEquals("000000000", c.getTelefono());
    }

    @Test
    public void comprobarTelefonoConLetras() {
        Cliente c = new Cliente("juan", "perez", "77624568A", "Calle 1");
        assertEquals("000000000", c.getTelefono());
    }

    @Test
    public void comprobarFormateoNombres() {
        Cliente c = new Cliente(" aLbeRto ", " rUiZ ", "776245688", "Calle 1");
        assertEquals("alberto", c.getNombre());
        assertEquals("RUIZ", c.getApellidos());
    }

    @Test
    public void comprobarRechazoPedidoNoPagadoEnHistorial() {
        Cliente c = new Cliente("Ana", "Gomez", "666777888", "Dir");
        Pedido pedidoPendiente = c.realizarPedido(); // Se crea en estado pendiente por defecto

        c.agregarPedido(pedidoPendiente);

        // El historial debería seguir vacío porque el pedido no está pagado
        assertEquals(0, c.getHistorial().size());
    }
}