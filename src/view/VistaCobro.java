package view;

import config.ColorPaleta;
import dao.PedidoDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.Border;

import main.TPVMain;
import main.App;
import model.Pedido;

public class VistaCobro extends JPanel implements ActionListener {
    private App app;
    private TPVMain tpvMain;
    private JLabel mesaLabel;
    private JTextArea detalleCuentaArea;
    private JButton efectivoButton;
    private Boton tarjetaButton;
    private Boton volverButton;
    private Boton botonFactura;
    private Pedido pedidoActual;

    public VistaCobro(TPVMain tpvMain, Pedido pedido) {
        this.tpvMain = tpvMain;
        this.pedidoActual = pedido;

        setBackground(ColorPaleta.FONDO_SECUNDARIO); // Color de fondo gris oscuro
        setLayout(new BorderLayout()); // Diseño por regiones

        // Información de la mesa y total (NORTH)
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.setBackground(ColorPaleta.FONDO_SECUNDARIO); // Color de fondo gris oscuro
        mesaLabel = new JLabel("Mesa: ", SwingConstants.CENTER);
        mesaLabel.setForeground(ColorPaleta.TEXTO_PRINCIPAL_CLARO); // Color del texto del título
        infoPanel.add(mesaLabel);
        add(infoPanel, BorderLayout.NORTH);

        // Detalle de la cuenta y total (CENTER)
        detalleCuentaArea = new JTextArea("Seleccione un método de pago para ver los detalles del pedido.");
        detalleCuentaArea.setEditable(false);
        detalleCuentaArea.setBackground(ColorPaleta.TEXTAREA_FONDO); // Color de fondo gris claro
        detalleCuentaArea.setForeground(ColorPaleta.TEXTAREA_TEXTO); // Color del texto gris oscuro
        add(new JScrollPane(detalleCuentaArea), BorderLayout.CENTER); // Agrega el área de texto con scroll

        // Opciones de pago (WEST)
        JPanel pagoPanel = new JPanel(new GridLayout(2, 1, 5, 10));
        pagoPanel.setBackground(ColorPaleta.FONDO_SECUNDARIO); // Color de fondo gris oscuro
        efectivoButton = new Boton("Efectivo", ColorPaleta.SECUNDARIO, ColorPaleta.HOVER_SECUNDARIO);
        efectivoButton.setActionCommand("efectivo");
        efectivoButton.addActionListener(this);
        tarjetaButton = new Boton("Tarjeta", ColorPaleta.SECUNDARIO, ColorPaleta.HOVER_SECUNDARIO);
        tarjetaButton.setActionCommand("tarjeta");
        tarjetaButton.addActionListener(this);
        pagoPanel.add(efectivoButton);
        pagoPanel.add(tarjetaButton);
        add(pagoPanel, BorderLayout.WEST);

        // JPanel para botón de volver (SOUTH)
        JPanel volverPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        volverPanel.setBackground(ColorPaleta.FONDO_SECUNDARIO); // Color de fondo gris oscuro
        volverButton = new Boton("Volver",ColorPaleta.VOLVER, ColorPaleta.HOVER_VOLVER);
        volverButton.setActionCommand("volver");
        volverButton.addActionListener(this);
        
        botonFactura = new Boton("Factura", ColorPaleta.SECUNDARIO, ColorPaleta.HOVER_SECUNDARIO);
        botonFactura.setActionCommand("factura");
        botonFactura.addActionListener(this);

        volverPanel.add(botonFactura);
        volverPanel.add(volverButton);
        add(volverPanel, BorderLayout.SOUTH);

        // Inicializar los datos del pedido
        actualizarVistaInicial();
    }

    // Método para mostrar la vista inicial sin detalles del pedido
    private void actualizarVistaInicial() {
        if (pedidoActual != null) {
            mesaLabel.setText("Mesa: " + pedidoActual.getNumeroMesa());
            detalleCuentaArea.setText("Seleccione un método de pago para ver los detalles del pedido.");
        } else {
            mesaLabel.setText("Mesa: -");
            detalleCuentaArea.setText("No hay pedido.");
        }
    }

    // Método para actualizar la vista con los detalles del pedido
    private void actualizarVistaConDetalles(String metodoPago) {
        if (pedidoActual != null) {
            PedidoDAO pedidoDAO = new PedidoDAO();
            StringBuilder sb = new StringBuilder("Pedido:\n");
            Map<String, Integer> platosPedido = pedidoDAO.obtenerPlatosPedido(pedidoActual);
            for (Map.Entry<String, Integer> entrada : platosPedido.entrySet()) {
                String nombrePlato = entrada.getKey();
                int cantidad = entrada.getValue();
                double subtotal = cantidad * pedidoDAO.obtenerPrecioPlato(nombrePlato);
                sb.append(nombrePlato).append(" x ").append(cantidad)
                  .append(" = ").append(String.format("%.2f €", subtotal)).append("\n");
            }

            // Agregar total
            sb.append("\n");
            double total = pedidoDAO.calcularCuenta(pedidoActual);
            sb.append("TOTAL: ").append(String.format("%.2f €", total)).append("\n");

            // Mostrar el método de pago seleccionado
            sb.append("\nMétodo de Pago: ").append(metodoPago);

            // Actualizar el área de texto con los detalles del pedido
            detalleCuentaArea.setText(sb.toString());
        } else {
            mesaLabel.setText("Mesa: -");
            detalleCuentaArea.setText("No hay pedido.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if ("volver".equals(comando)) {
            tpvMain.mostrarVista("Mesas");
        } else if ("efectivo".equals(comando)) {
            actualizarVistaConDetalles("Efectivo");
            JOptionPane.showMessageDialog(this, "Pago en efectivo seleccionado. Proceda con el cobro.");
            PedidoDAO pedidoDAO = new PedidoDAO();
            pedidoDAO.cambiarEstadoPagado(pedidoActual.getId());
            pedidoDAO.setMetodoPago(pedidoActual.getId(), "EFECTIVO");

        } else if ("tarjeta".equals(comando)) {
            actualizarVistaConDetalles("Tarjeta");
            JOptionPane.showMessageDialog(this, "Pago con tarjeta seleccionado. Proceda con el cobro.");
            PedidoDAO pedidoDAO = new PedidoDAO();
            pedidoDAO.cambiarEstadoPagado(pedidoActual.getId());
            pedidoDAO.setMetodoPago(pedidoActual.getId(), "TARJETA");

        } else if ("factura".equals(comando)) {
            if (pedidoActual != null) {
                app.generarFactura(pedidoActual);
                mostrarFacturaEnDialogo(pedidoActual);
                JOptionPane.showMessageDialog(this, "Factura generada para la mesa " + pedidoActual.getNumeroMesa() + ".");
            } else {
                JOptionPane.showMessageDialog(this, "No hay pedido para generar factura.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void setPedidoActual(Pedido pedido) {
        this.pedidoActual = pedido;
        actualizarVistaInicial(); // Actualiza la vista inicial sin detalles
    }

    public void setApp(App app) {
        this.app = app;
    }

    private void mostrarFacturaEnDialogo(Pedido pedido) {
        String nombreArchivo = "factura_" + pedido.getId() + ".html";
        JDialog dialogoFactura = new JDialog(tpvMain, "Factura - Mesa " + pedido.getNumeroMesa(), true );
        dialogoFactura.setSize(800, 600);
        dialogoFactura.setLocationRelativeTo(this);

        try {
            JEditorPane editorPane = new JEditorPane();
            editorPane.setEditable(false);
            editorPane.setContentType("text/html");
            editorPane.setPage(new java.io.File(nombreArchivo).toURI().toURL());

            JScrollPane scrollPane = new JScrollPane(editorPane);
            dialogoFactura.add(scrollPane, BorderLayout.CENTER);

            Boton botonCerrar = new Boton("Cerrar", ColorPaleta.VOLVER, ColorPaleta.HOVER_VOLVER);
            botonCerrar.addActionListener(e -> dialogoFactura.dispose());
            JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panelBotones.add(botonCerrar);
            dialogoFactura.add(panelBotones, BorderLayout.SOUTH);

            dialogoFactura.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la factura " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
