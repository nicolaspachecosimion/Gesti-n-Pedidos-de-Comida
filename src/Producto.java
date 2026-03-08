public class Producto implements Comparable<Producto> {

    // 1. Atributos
    private String nombre;
    private double precio;

    // 2. Constructor
    public Producto(String nombre, double precio) {
        this.setNombre(nombre);
        this.setPrecio(precio);
    }

    // 3. Metodos get y set

    public String getNombre() {
        return nombre;
    }

    // Guarda el nombre en mayúsculas
    public void setNombre(String nombre) {
        if (nombre != null) {
            this.nombre = nombre.toUpperCase();
        } else {
            this.nombre = "";
        }
    }

    public double getPrecio() {
        return precio;
    }

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

    // Ordenarlos alfabéticamente por el nombre
    public int compareTo(Producto otro) {
        return this.nombre.compareTo(otro.getNombre());
    }

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
