import java.util.Date;

public class PasarelaDePago {

    // 1. Atributos
    private double importe;
    private long codigoPago;

    // 2. Constructor
    public PasarelaDePago(double importe) {
        this.setImporte(importe);
        this.codigoPago = 0;
    }

    // 3. Metodos get y set

    public double getImporte() {
        return importe;
    }

    private void setImporte(double importe) {
        if (importe < 0) {
            System.out.println("ERROR: El importe a pagar no puede ser negativo.");
            this.importe = 0.0;
        } else {
            this.importe = Math.round(importe * 100.0) / 100.0;
        }
    }

    public long getCodigoPago() {
        return codigoPago;
    }

    // Para saber si ya se ha pagado
    public boolean esPagado() {
        if (this.codigoPago == 0) {
            return false;
        } else {
            return true;
        }
    }

    // --- MÉT ODO AUXILIAR PRIVADO ---
    private void procesarPagoCorrecto() {
        this.importe = 0.0;
        Date fechaActual = new Date();
        this.codigoPago = fechaActual.getTime();
    }

    // --- MÉTODOS DE PAGO: TARJETA Y CUENTA ---

    public boolean Tarjeta(String numeroTarjeta) {
        if (numeroTarjeta == null || numeroTarjeta.trim().equals("")) {
            System.out.println("ERROR: El número de tarjeta no es válido.");
            return false;
        }

        // Si la tarjeta es válida, procesamos el pago
        this.procesarPagoCorrecto();
        return true;
    }

    public boolean Cuenta(String cuenta) {
        if (cuenta == null || cuenta.trim().equals("")) {
            System.out.println("ERROR: El número de cuenta no es válido.");
            return false;
        }

        this.procesarPagoCorrecto();
        return true;
    }
    public boolean Efectivo(float cantidadEntrega) {
        // 1. Validar que nos dan dinero suficiente
        if (cantidadEntrega < this.importe) {
            System.out.println("ERROR: La cantidad entregada (" + cantidadEntrega + "€) es menor que el importe (" + this.importe + "€).");
            return false; // El pago falla
        }

        // 2. Calcular el cambio a devolver
        double cambio = cantidadEntrega - this.importe;
        // Redondeamos el cambio a dos decimales para evitar problemas de precisión matemática
        cambio = Math.round(cambio * 100.0) / 100.0;

        System.out.println("\n SU CAMBIO ");
        System.out.println("******************");

        // 3. Desglose de billetes y monedas
        int billetes50 = (int) (cambio / 50);
        cambio = cambio % 50; // Nos quedamos con el resto

        int billetes20 = (int) (cambio / 20);
        cambio = cambio % 20;

        int billetes10 = (int) (cambio / 10);
        cambio = cambio % 10;

        int billetes5  = (int) (cambio / 5);
        cambio = cambio % 5;

        int monedas1   = (int) (cambio / 1);
        cambio = cambio % 1;

        // Lo que queda después de las monedas de 1€ son los céntimos
        double centimos = Math.round(cambio * 100.0) / 100.0;

        // Mostramos el resultado
        System.out.println(" 50€       " + billetes50 + " billete(s)");
        System.out.println(" 20€       " + billetes20 + " billete(s)");
        System.out.println(" 10€       " + billetes10 + " billete(s)");
        System.out.println(" 5€        " + billetes5 + " billete(s)");
        System.out.println(" 1€        " + monedas1 + " euro(s)");
        System.out.println(" cent      " + centimos + " céntimo(s)");

        this.procesarPagoCorrecto();
        return true;
    }
}
