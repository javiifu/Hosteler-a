package model;

public class Mesa {
    // Atributos
    private String codigo;
    private int capacidad;
    private Boolean estado;

    // Constructor
    public Mesa(int capacidad) {
        this.capacidad = capacidad;
        this.estado = true; // Por defecto, la mesa está disponible
    }

    // Getters
    public int getCapacidad() {
        return capacidad;
    }

    // Setters
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    // Metodos
    public void cambiarEstadoMesa() {
        this.estado = !this.estado; // Cambia el estado de la mesa
    }

    @Override
    public String toString() {
        return "Mesa {" +
               "\n  Código de la mesa: " + codigo +
               ",\n  Capacidad: " + capacidad +
               ",\n  Estado: " + (estado ? "Disponible" : "Ocupada") +
               "\n}";
    }

    
}
