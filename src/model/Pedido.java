package model;

import java.sql.Date;
import java.time.LocalDateTime;


public class Pedido {
    private int id;
    private int numeroMesa;
    private LocalDateTime horaPedido;
    private Date fechaPedido;
    private String tipo_pago;

    // Constructor 
    public Pedido(int numeroMesa, LocalDateTime horaPedido) {
        this.numeroMesa = numeroMesa;
        this.horaPedido = horaPedido;
    }

    // Getters y setters
    public int getId() { return id;}
    public int getNumeroMesa() {return numeroMesa; }
    public LocalDateTime getHoraPedido() { return horaPedido; }
    public Date getFechaPedido() { return fechaPedido; }
    public void setId(int id) { this.id = id; }
    public void setNumeroMesa(int numeroMesa) { this.numeroMesa = numeroMesa; }
    public void setHoraPedido(LocalDateTime horaPedido) { this.horaPedido = horaPedido; }
    public void setFechaPedido(Date fechaPedido) { this.fechaPedido = fechaPedido; }
    public String getTipo_pago() { return tipo_pago; }
    public void setTipo_pago(String tipo_pago) { this.tipo_pago = tipo_pago; }

    @Override
    public String toString() {
        return "Pedido: id = " + id + ", numeroMesa = " + numeroMesa + ", horaPedido = " + horaPedido ;
    }

}