import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoTest {

    @Test
    public void comprobarMayusculas() {
        Producto p = new Producto(" coca-cola ", 1.50);
        assertEquals("COCA-COLA", p.getNombre());
    }

    @Test
    public void comprobarPrecioNegativo() {
        Producto p = new Producto("Bocadillo", -3.50);
        assertEquals(0.0, p.getPrecio());
    }

    // --- NUEVO TEST ---
    @Test
    public void comprobarIgualdadProductos() {
        Producto p1 = new Producto("Agua", 1.0);
        Producto p2 = new Producto(" aGua ", 2.5);

        // Como nuestro equals compara por nombre limpio, deberían ser el mismo producto
        assertEquals(p1, p2);
    }
}