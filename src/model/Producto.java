package model;

public class Producto {

 
    private Integer codigo;

    private String nombre;
    private String descripcion;
    private double precio;
    private Boolean disponible;
    private Integer categoriaCodigo;

    public Producto(String nombre, String descripcion, double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponible = true;
    }
    public Producto(String nombre, String descripcion, double precio, int categoriaCodigo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoriaCodigo = categoriaCodigo; 
        this.disponible = true;
    }
    public Producto(int codigo, String nombre, String descripcion, double precio, int categoriaCodigo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoriaCodigo = categoriaCodigo; 
        this.disponible = true;
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

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public Producto(String nombre, double precio, Integer categoriaCodigo) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoriaCodigo = categoriaCodigo;
    }

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
