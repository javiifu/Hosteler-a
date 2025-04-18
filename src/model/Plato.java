package model;

public class Plato {
    //Atributos
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;

    
    //Constructor
    public Plato (String nombre, String descripcion, double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    // Getters
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setPrecio(double precio) { this.precio = precio; }

    @Override
    public String toString() {
        return "Plato {" +
               "\n  Código: " + codigo +
               ",\n  Nombre: " + nombre +
               ",\n  Descripción: " + descripcion +
               ",\n  Precio: " + precio +
               "\n}";
    }

    
}
