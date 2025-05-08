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
    public PedidoPlato(int codigoProducto ) {
        this.codigoProducto = codigoProducto;
        
    }

    public PedidoPlato(int pedidoId, int codigoProducto, int cantidad) {
        this.pedidoId = pedidoId;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
    }


    // Getters y setters
    public int getPedidoId() { return pedidoId; }
    public int getCodigoProducto() { return codigoProducto; }
    public int getCantidad() { return cantidad;  }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setPedidoId(int pedidoId) { this.pedidoId = pedidoId; }

    @Override
    public String toString() {
        return "PedidoPlato: id = " + pedidoId +  ", codigoProducto = " + codigoProducto + ", cantidad = " + cantidad;
    }
}