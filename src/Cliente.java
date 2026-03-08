import java.util.Date;
import java.util.ArrayList;

public class Cliente {

    // 1. ATRIBUTOS
    private String nombre;
    private String apellidos;
    private Date fechaDeAlta;
    private String telefono;
    private String direccion;
    private ArrayList<Pedido> historial;

    // 2. CONSTRUCTOR
    public Cliente(String nombre, String apellidos, String telefono, String direccion) {
        this.setNombre(nombre);
        this.setApellidos(apellidos);
        this.setTelefono(telefono);
        this.setDireccion(direccion);
        this.fechaDeAlta = new Date();
        this.historial = new ArrayList<Pedido>();
    }

    // 3. MÉTODOS GET Y SET

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {
        if (nombre != null) {
            this.nombre = nombre.trim().toLowerCase();
        } else {
            this.nombre = "";
        }
    }

    public String getApellidos() { return apellidos; }

    public void setApellidos(String apellidos) {
        if (apellidos != null) {
            this.apellidos = apellidos.trim().toUpperCase();
        } else {
            this.apellidos = "";
        }
    }

    public String getTelefono() { return telefono; }

    public void setTelefono(String telefono) {
        if (telefono == null) {
            this.telefono = "000000000";
            return;
        }

        String telLimpio = telefono.replace(" ", "");

        boolean esValido = true;

        // Comprobación A: ¿Tiene exactamente 9 caracteres?
        if (telLimpio.length() != 9) {
            esValido = false;
        }
        else {
            // Comprobación B: ¿El primer número es 6, 7, 8 o 9?
            char primerDigito = telLimpio.charAt(0);
            if (primerDigito != '6' && primerDigito != '7' && primerDigito != '8' && primerDigito != '9') {
                esValido = false;
            }
            else {
                // Comprobación C: ¿Todos los caracteres son números?
                for (int i = 0; i < telLimpio.length(); i++) {
                    char letra = telLimpio.charAt(i);
                    // Comprobamos si el carácter no está entre el 0 y el 9
                    if (letra < '0' || letra > '9') {
                        esValido = false;
                    }
                }
            }
        }

        // 3. Asignamos el valor dependiendo de si ha pasado todas las pruebas
        if (esValido == true) {
            this.telefono = telLimpio;
        } else {
            System.out.println("ERROR: Teléfono no válido (" + telefono + "). Debe tener 9 números y empezar por 6, 7, 8 o 9.");
            this.telefono = "000000000"; // Le asignamos uno por defecto
        }
    }

    public String getDireccion() { return direccion; }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaDeAlta() { return fechaDeAlta; }

    public void setFechaDeAlta(Date fechaDeAlta) {
        if (fechaDeAlta != null) {
            this.fechaDeAlta = fechaDeAlta;
        } else {
            this.fechaDeAlta = new Date();
        }
    }

    // --- GESTIÓN DEL HISTORIAL ---

    public ArrayList<Pedido> getHistorial() {
        return historial;
    }

    public void agregarPedido(Pedido pedido) {
        if (pedido == null) {
            System.out.println("ERROR: El pedido a añadir es nulo.");
            return;
        }

        // Comprobar que esté pagado
        if (pedido.getPago() == null || pedido.getPago().esPagado() == false) {
            System.out.println("ERROR: No se puede agregar al historial un pedido que no esté pagado.");
            return;
        }

        // Comprobar si el pedido es mío
        if (pedido.getCliente() != this) {
            System.out.println("ERROR: Este pedido pertenece a otro cliente.");
            return;
        }

        // Comprobar que no esté repetido como misma fecha y hora
        for (int i = 0; i < this.historial.size(); i++) {
            Pedido pGuardado = this.historial.get(i);

            if (pGuardado.getFechaHora().getTime() == pedido.getFechaHora().getTime()) {
                System.out.println("ERROR: Este pedido ya existe en el historial.");
                return;
            }
        }

        // Si pasa las barreras de seguridad, lo añadimos
        this.historial.add(pedido);
    }

    // Mét odo que permite crear un pedido e iniciarlo asignándole este cliente automáticamente
    public Pedido realizarPedido() {
        return new Pedido(this);
    }
}
