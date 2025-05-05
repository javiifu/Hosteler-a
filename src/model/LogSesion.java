package model;

import java.sql.Date;
import java.sql.Time;
public class LogSesion {
    private Integer id;
    private String tipo;
    private String concepto;
    private Date fecha;
    private Time hora;
    private int id_usuario;

    public LogSesion(int id, String tipo, String concepto, Date fecha, Time hora, int id_usuario) {
        this.id = id;
        this.tipo = tipo;
        this.concepto = concepto;
        this.fecha = fecha;
        this.hora = hora;
        this.id_usuario = id_usuario;
    }
    public LogSesion(String tipo, String concepto, Date fecha, Time hora, int id_usuario) {
        this.tipo = tipo;
        this.concepto = concepto;
        this.fecha = fecha;
        this.hora = hora;
        this.id_usuario = id_usuario;
    }

    //getters
    public int getId() {
        return id;
    }
    public String getTipo() {
        return tipo;
    }
    public String getConcepto() {
        return concepto;
    }
    public Date getFecha() {
        return fecha;
    }
    public Time getHora() {
        return hora;
    }
    public int getId_usuario() {
        return id_usuario;
    }
    //setters
    public void setId(int id) {
        this.id = id;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public void setHora(Time hora) {
        this.hora = hora;
    }
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
