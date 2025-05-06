package model;

public class Mesa {
    // Atributos
    private int numero;
    private Boolean estado =true;


    // Constructor
    public Mesa(int numero, Boolean estado) {
        this.numero = numero;
        this.estado = estado;
    }
    // Constructor
    public Mesa(int numero) {
        this.numero = numero;
        
    }

    // Getters
    public int getNumero() { return numero; }
    public Boolean getEstado() { return estado; }


    // Metodos
    public void cambiarEstadoMesa() {
        this.estado = !this.estado; // Cambia el estado de la mesa
    }


    @Override
    public String toString() {
        return "Mesa {" +
               "\n  Código de la mesa: " + numero +
               ",\n  Estado: " + (estado ? "Disponible" : "Ocupada") +
               "\n}";
    }

    
}
