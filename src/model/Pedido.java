package model;

import java.time.LocalDateTime;

public class Pedido {
    private int id;
    private int numeroMesa;
    private LocalDateTime horaPedido;

    // Constructor 
    public Pedido(int numeroMesa, LocalDateTime horaPedido) {
        this.numeroMesa = numeroMesa;
        this.horaPedido = horaPedido;
    }

    // Getters y setters
    public int getId() { return id;}
    public int getNumeroMesa() {return numeroMesa; }
    public LocalDateTime getHoraPedido() { return horaPedido; }

    @Override
    public String toString() {
        return "Pedido: id = " + id + ", numeroMesa = " + numeroMesa + ", horaPedido = " + horaPedido ;
    }
}