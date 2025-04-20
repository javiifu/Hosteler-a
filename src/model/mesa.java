package model;

public class Mesa {
    //Atributos
    private int id;
    private int numero;
    private int capacidad;
    private boolean estado; 

    public Mesa(int numero, int capacidad, boolean estado) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.estado = estado;
}
    //Constructor para consultas y actualizaciones (con el id)
    public Mesa(int id, int numero, int capacidad, boolean estado) {
    this.id = id;
    this.numero = numero;
    this.capacidad = capacidad;
    this.estado = estado;
}
    //Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}


