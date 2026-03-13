/**
 * Representa un producto de la carta
 */
public class Producto implements Comparable<Producto> {

    // 1. Atributos
    private String nombre;
    private double precio;

    /**
     * Constructor de la clase Producto.
     *
     * @param nombre El nombre del producto.
     * @param precio El precio del producto en euros.
     */
    // 2. Constructor
    public Producto(String nombre, double precio) {
        this.setNombre(nombre);
        this.setPrecio(precio);
    }

    // 3. Metodos get y set

    /**
     * Obtiene el nombre del producto.
     * @return El nombre del producto en mayúsculas.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto, eliminando espacios y convirtiéndolo a mayúsculas.
     * @param nombre El nombre a establecer.
     */
    // Guarda el nombre en mayúsculas
    public void setNombre(String nombre) {
        if (nombre != null) {
            this.nombre = nombre.trim().toUpperCase();
        } else {
            this.nombre = "";
        }
    }

    /**
     * Obtiene el precio del producto.
     * @return El precio en euros.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto. Si es negativo, asigna 0.0.
     * @param precio El precio a establecer.
     */
    // Guarda el precio validando que no sea negativo y con 2 decimales
    public void setPrecio(double precio) {
        if (precio < 0) {
            System.out.println("ERROR: El precio no puede ser negativo.");
            this.precio = 0.0; //
        } else {
            this.precio = Math.round(precio * 100.0) / 100.0;
        }
    }

    // --- MÉTODOS PARA ORDENAR Y COMPARAR ---

    /**
     * Compara este producto con otro basándose en su nombre (orden alfabético).
     * @param otro El producto con el que se va a comparar.
     * @return Un valor negativo, cero o positivo según el orden alfabético.
     */
    // Ordenarlos alfabéticamente por el nombre
    public int compareTo(Producto otro) {
        return this.nombre.compareTo(otro.getNombre());
    }

    /**
     * Comprueba si dos productos son iguales basándose en su nombre.
     * @param obj El objeto a comparar.
     * @return true si tienen el mismo nombre, false en caso contrario.
     */
    // Saber si dos productos son el mismo por su nombre
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Producto otro = (Producto) obj;
        return this.nombre.equals(otro.getNombre());
    }
}
