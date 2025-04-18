package model;

import java.util.ArrayList;

public class Carta {
    private ArrayList<Categoria> categorias;
    
    // Constructor
    public Carta() {
        this.categorias = new ArrayList<>();
    }

    // Getters
    public ArrayList<Categoria> getCategorias() { return categorias; }

    // Setters
    public void setCategorias(ArrayList<Categoria> categorias) { this.categorias = categorias; }

    

}
