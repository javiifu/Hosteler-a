package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import main.TPVMain;
import model.Pedido;
import config.ColorPaleta;
import dao.PedidoDAO;

// TODO: Hay que cambiar esta clase, arreglar fallos y comprobar la logica
public class VistaCobro extends JPanel implements ActionListener {
    private TPVMain tpvMain;
    private JLabel mesaLabel;
    private JTextArea detalleCuentaArea;
    private JButton efectivoButton;
    private JButton tarjetaButton;
    private JButton volverButton;
    private Pedido pedidoActual;

    public VistaCobro(TPVMain tpvMain, Pedido pedido) {
        this.tpvMain = tpvMain;
        this.pedidoActual = pedido;

        setBackground(ColorPaleta.FONDO_SECUNDARIO); // Color de fondo gris oscuro
        setLayout(new BorderLayout()); // Diseño por regiones

        // Informacion de la mesa y total (NORTH)
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.setBackground(ColorPaleta.FONDO_SECUNDARIO); // Color de fondo gris oscuro
        mesaLabel = new JLabel("Mesa: " + pedido.getNumeroMesa());
        mesaLabel.setForeground(ColorPaleta.TEXTO_PRINCIPAL_CLARO); // Color del texto del título
        infoPanel.add(mesaLabel);
        add(infoPanel, BorderLayout.NORTH);

        // Detalle de la cuenta y total (CENTER)
        detalleCuentaArea = new JTextArea("...");
        detalleCuentaArea.setEditable(false);
        detalleCuentaArea.setBackground(ColorPaleta.TEXTAREA_FONDO); // Color de fondo gris claro
        detalleCuentaArea.setForeground(ColorPaleta.TEXTAREA_TEXTO); // Color del texto gris oscuro
        add(new JScrollPane(detalleCuentaArea), BorderLayout.CENTER); // Agrega el área de texto con scroll
        
        // Opciones de pago (WEST)
        JPanel pagoPanel = new JPanel(new GridLayout(2, 1, 5, 10));
        pagoPanel.setBackground(ColorPaleta.FONDO_SECUNDARIO); // Color de fondo gris oscuro
        efectivoButton = new Boton("Efectivo");
        tarjetaButton = new Boton("Tarjeta");
        pagoPanel.add(efectivoButton);
        pagoPanel.add(tarjetaButton);
        add(pagoPanel, BorderLayout.WEST);

        // JPanel para boton de volver (SOUTH)
        JPanel volverPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        volverPanel.setBackground(ColorPaleta.FONDO_SECUNDARIO); // Color de fondo gris oscuro
        volverButton = new Boton("Volver");
        volverButton.setActionCommand("volver");
        volverButton.addActionListener(this);
        volverPanel.add(volverButton);
        add(volverPanel, BorderLayout.SOUTH);

        // Inicializar los datos del pedido
        actualizarVista();
    }

    public void actualizarVista() {
    if (pedidoActual != null) {
        PedidoDAO pedidoDAO = new PedidoDAO(); 
        // Actualizar el label de la mesa
        mesaLabel.setText("Mesa: " + pedidoActual.getNumeroMesa());

        // Detalles del pedido
        StringBuilder sb = new StringBuilder("Pedido:\n");
        Map<String, Integer> platosPedido = pedidoDAO.obtenerPlatosPedido(pedidoActual.getNumeroMesa());
        for (Map.Entry<String, Integer> entrada : platosPedido.entrySet()) {
            String nombrePlato = entrada.getKey();
            int cantidad = entrada.getValue();
            double subtotal = cantidad * PedidoDAO.obtenerPrecioPlato(nombrePlato); // Método auxiliar para obtener el precio
            sb.append(nombrePlato).append(" x ").append(cantidad)
              .append(" = ").append(String.format("%.2f €", subtotal)).append("\n");
        }

        // Lógica para el pago
        sb.append("\n"); // Línea en blanco antes del total
        double total = pedidoDAO.calcularCuenta(pedidoActual.getNumeroMesa());
        sb.append("TOTAL: ").append(String.format("%.2f €", total)); // EJ: 20.00 €

        // Ventana emergente para el pago
        int opcionPago = JOptionPane.showOptionDialog(
            this,
            "Seleccione el método de pago:",
            "Método de Pago",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Efectivo", "Tarjeta"},
            "Tarjeta"
        );

        if (opcionPago == JOptionPane.YES_OPTION) {
            // Pago en efectivo
            String input = JOptionPane.showInputDialog(this, "Ingrese el dinero recibido:");
            if (input != null) {
                try {
                    double dineroRecibido = Double.parseDouble(input);
                    if (dineroRecibido >= total) {
                        double cambio = dineroRecibido - total;
                        JOptionPane.showMessageDialog(this, "Pago exitoso. Cambio: " + String.format("%.2f €", cambio));
                    } else {
                        JOptionPane.showMessageDialog(this, "El dinero recibido no es suficiente.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Entrada inválida. Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (opcionPago == JOptionPane.NO_OPTION) {
            // Pago con tarjeta
            JOptionPane.showMessageDialog(this, "Continue en el datafono");

            if (tpvMain != null) {
                tpvMain.mostrarVista("Mesas"); // Cambia a la vista del datáfono
            }
        }

        // Actualizar el área de texto con los detalles del pedido
        detalleCuentaArea.setText(sb.toString());
    } else {
        // Si no hay pedido, mostrar un mensaje por defecto
        mesaLabel.setText("Mesa: -");
        detalleCuentaArea.setText("No hay pedido.");
    }
}
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if ("volver".equals(comando)) {
            // Añadir logica para volver a la vista anterior
        } else if ("efectivo".equals(comando)) {
            // Lógica para el pago en efectivo
            // Tiene que salir una ventana emergente para que el usuario ingrese el dinero
            // y luego calcular el cambio
        } else if ("tarjeta".equals(comando)) {
            // Lógica para el pago con tarjeta
            // Tiene que salir una ventana emergente para que el usuario ingrese los datos de la tarjeta
            // y luego procesar el pago
        }
    }
}
