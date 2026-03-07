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
}
