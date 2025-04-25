package model;

public class Producto {
    private int codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean disponible;
    private int categoriaCodigo;

    public Producto(String nombre, String descripcion, double precio, boolean disponible) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponible = disponible;
    }

    // Getters
    public int getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
    public boolean isDisponible() { return disponible; }
    public int getCategoriaCodigo() { return categoriaCodigo; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void cambiarEstadoProducto() { this.disponible = !this.disponible; } // Cambia el estado de disponibilidad
    public void setCategoriaCodigo(int categoriaCodigo) { this.categoriaCodigo = categoriaCodigo; }

    @Override
    public String toString() {
        return "Producto {" +
               "\n  Código: " + codigo +
               ",\n  Nombre: " + nombre +
               ",\n  Descripción: " + descripcion +
               ",\n  Precio: " + precio +
               ",\n  Disponible: " + (disponible ? "Sí" : "No") +
               ",\n  Código de categoría: " + categoriaCodigo +
               "\n}";
    }
}
