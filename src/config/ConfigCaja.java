package config;

public class ConfigCaja {
    private boolean permitir_cambios;
    private boolean permitir_descuentos;
    private Double max_descuento;

    // Getters y Setters
    public boolean isPermitir_cambios() {
        return permitir_cambios;
    }

    public void setPermitir_cambios(boolean permitir_cambios) {
        this.permitir_cambios = permitir_cambios;
    }

    public boolean isPermitir_descuentos() {
        return permitir_descuentos;
    }

    public void setPermitir_descuentos(boolean permitir_descuentos) {
        this.permitir_descuentos = permitir_descuentos;
    }

    public Double getMax_descuento() {
        return max_descuento;
    }

    public void setMax_descuento(Double max_descuento) {
        this.max_descuento = max_descuento;
    }
}
