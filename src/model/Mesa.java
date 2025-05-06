package model;

public class Mesa {
    // Atributos
    private int codigo;
    private Boolean estado =true;



    // Getters
    public int getCodigo() { return codigo; }
    public Boolean getEstado() { return estado; }


    // Metodos
    public void cambiarEstadoMesa() {
        this.estado = !this.estado; // Cambia el estado de la mesa
    }

    // Constructor
    public Mesa(int codigo, Boolean estado) {
        this.codigo = codigo;
        this.estado = estado;
    }
    // Constructor
    public Mesa(int codigo) {
        this.codigo = codigo;
        
    }

    @Override
    public String toString() {
        return "Mesa {" +
               "\n  Código de la mesa: " + codigo +
               ",\n  Estado: " + (estado ? "Disponible" : "Ocupada") +
               "\n}";
    }

    
}
