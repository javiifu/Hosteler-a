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
}
