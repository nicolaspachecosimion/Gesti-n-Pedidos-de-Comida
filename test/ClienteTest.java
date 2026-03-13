import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    @Test
    public void comprobarTelefonoValidoConEspacios() {
        Cliente c = new Cliente("juan", "perez", "776 24 56 88", "Calle 1");
        // Esperamos que le quite los espacios correctamente
        assertEquals("776245688", c.getTelefono());
    }

    @Test
    public void comprobarTelefonoInvalidoEmpiezaMal() {
        Cliente c = new Cliente("juan", "perez", "123456789", "Calle 1");
        // Como empieza por 1 (y no por 6, 7, 8 o 9), debe poner el de por defecto
        assertEquals("000000000", c.getTelefono());
    }

    @Test
    public void comprobarTelefonoConLetras() {
        Cliente c = new Cliente("juan", "perez", "77624568A", "Calle 1");
        // Como tiene una letra, la validación debe fallar
        assertEquals("000000000", c.getTelefono());
    }

    @Test
    public void comprobarFormateoNombres() {
        Cliente c = new Cliente(" aLbeRto ", " rUiZ ", "776245688", "Calle 1");
        assertEquals("alberto", c.getNombre()); // Siempre minúsculas
        assertEquals("RUIZ", c.getApellidos()); // Siempre mayúsculas
    }
}