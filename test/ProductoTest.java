import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoTest {

    @Test
    public void comprobarMayusculas() {
        Producto p = new Producto(" coca-cola ", 1.50);
        // Esperamos que le quite los espacios extra y lo ponga en mayúsculas
        assertEquals("COCA-COLA", p.getNombre());
    }

    @Test
    public void comprobarPrecioNegativo() {
        Producto p = new Producto("Bocadillo", -3.50);
        // Esperamos que nuestra seguridad actúe y le ponga precio 0.0
        assertEquals(0.0, p.getPrecio());
    }
}