package model;

import java.time.LocalDateTime;

public class Reserva {
    //Atributos
    private int id;
    private int mesaId;
    private String nombreCliente;
    private LocalDateTime fechaHora;
    private int numPersonas;
    private boolean cancelada; 


     // Constructor para nueva reserva     
    public Reserva(int mesaId, String nombreCliente, LocalDateTime fechaHora, int numPersonas) {
        this.mesaId = mesaId;
        this.nombreCliente = nombreCliente;
        this.fechaHora = fechaHora;
        this.numPersonas = numPersonas;
        this.cancelada = false;
    }

    //estado de cancelación).
     
    public Reserva(int id, int mesaId, String nombreCliente, LocalDateTime fechaHora, int numPersonas, boolean cancelada) {
        this.id = id;
        this.mesaId = mesaId;
        this.nombreCliente = nombreCliente;
        this.fechaHora = fechaHora;
        this.numPersonas = numPersonas;
        this.cancelada = cancelada;
    }

    //getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMesaId() { return mesaId; }
    public void setMesaId(int mesaId) { this.mesaId = mesaId; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public int getNumPersonas() { return numPersonas; }
    public void setNumPersonas(int numPersonas) { this.numPersonas = numPersonas; }

    public boolean isCancelada() { return cancelada; }
    public void setCancelada(boolean cancelada) { this.cancelada = cancelada; }
}
