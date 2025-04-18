package model;

import java.util.ArrayList;

public class Categoria {
    private int codigo;
    private String nombre;
    private ArrayList<Producto> productos;

    public Categoria(String nombre) {
        this.nombre = nombre;
        this.productos = new ArrayList<>();
    }

    // Getters
    public int getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public ArrayList<Producto> getProductos() { return productos; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setProductos(ArrayList<Producto> productos) { this.productos = productos; }

    @Override
    public String toString() {
        return "Categoria {" +
               "\n  Código: " + codigo +
               ",\n  Nombre: " + nombre +
               ",\n  Productos: " + productos +
               "\n}";
    }
}
