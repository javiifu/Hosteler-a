package model;

public class Mesa {
    // Atributos
    private String codigo;
    private Boolean estado =true;



    // Getters
    public String getCodigo() { return codigo; }
    public Boolean getEstado() { return estado; }


    // Metodos
    public void cambiarEstadoMesa() {
        this.estado = !this.estado; // Cambia el estado de la mesa
    }

    @Override
    public String toString() {
        return "Mesa {" +
               "\n  Código de la mesa: " + codigo +
               ",\n  Estado: " + (estado ? "Disponible" : "Ocupada") +
               "\n}";
    }

    
}
