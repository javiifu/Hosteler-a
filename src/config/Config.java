package config;

import java.util.ArrayList;

public class Config {
    private String nombre_restaurante;
    private int numero_mesas;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Tax> taxes;
    private ArrayList<Impresora> impresoras;
    private ArrayList<Horario> horarios;
    private ConfigCaja config_caja;

    // Getters y Setters
    public String getNombre_restaurante() {
        return nombre_restaurante;
    }

    public void setNombre_restaurante(String nombre_restaurante) {
        this.nombre_restaurante = nombre_restaurante;
    }

    public int getNumero_mesas() {
        return numero_mesas;
    }

    public void setNumero_mesas(int numero_mesas) {
        this.numero_mesas = numero_mesas;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public ArrayList<Tax> getTaxes() {
        return taxes;
    }

    public void setTaxes(ArrayList<Tax> taxes) {
        this.taxes = taxes;
    }

    public ArrayList<Impresora> getImpresoras() {
        return impresoras;
    }

    public void setImpresoras(ArrayList<Impresora> impresoras) {
        this.impresoras = impresoras;
    }

    public ArrayList<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<Horario> horarios) {
        this.horarios = horarios;
    }

    public ConfigCaja getConfig_caja() {
        return config_caja;
    }

    public void setConfig_caja(ConfigCaja config_caja) {
        this.config_caja = config_caja;
    }
}
