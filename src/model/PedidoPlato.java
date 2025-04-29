package model;

public class PedidoPlato {
    private int pedidoId;
    private int codigoProducto;
    private int cantidad;

    // Constructor
    public PedidoPlato(int codigoProducto, int cantidad) {
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public int getPedidoId() { return pedidoId; }
    public int getCodigoProducto() { return codigoProducto; }
    public int getCantidad() { return cantidad;  }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    @Override
    public String toString() {
        return "PedidoPlato{" +
               ", pedidoId=" + pedidoId +
               ", codigoProducto=" + codigoProducto +
               ", cantidad=" + cantidad +
               '}';
    }
}