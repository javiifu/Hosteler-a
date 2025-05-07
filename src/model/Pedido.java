package model;

import java.sql.Date;
import java.sql.Time;


public class Pedido {
    private int id;
    private int numeroMesa;
    private Time horaPedido;
    private Date fechaPedido;
    private String tipo_pago;

    // Constructor 
    public Pedido(int numeroMesa, Time horaPedido) {
        this.numeroMesa = numeroMesa;
        this.horaPedido = new Time(System.currentTimeMillis());
        ;
    }
    public Pedido(int numeroMesa) {
        this.numeroMesa = numeroMesa;
        
    }
    public Pedido(int id, int numeroMesa, Time horaPedido, Date fechaPedido, String tipo_pago) {
        this.id = id;
        this.numeroMesa = numeroMesa;
        this.horaPedido = new Time(System.currentTimeMillis());
        this.fechaPedido = new Date(System.currentTimeMillis());
        this.tipo_pago = tipo_pago;
    }

    // Getters y setters
    public int getId() { return id;}
    public int getNumeroMesa() {return numeroMesa; }
    public Time getHoraPedido() { return horaPedido; }
    public Date getFechaPedido() { return fechaPedido; }
    public void setId(int id) { this.id = id; }
    public void setNumeroMesa(int numeroMesa) { this.numeroMesa = numeroMesa; }
    public void setHoraPedido(Time horaPedido) { this.horaPedido = horaPedido; }
    public void setFechaPedido(Date fechaPedido) { this.fechaPedido = fechaPedido; }
    public String getTipo_pago() { return tipo_pago; }
    public void setTipo_pago(String tipo_pago) { this.tipo_pago = tipo_pago; }

    @Override
    public String toString() {
        return "Pedido: id = " + id + ", numeroMesa = " + numeroMesa + ", horaPedido = " + horaPedido ;
    }

}