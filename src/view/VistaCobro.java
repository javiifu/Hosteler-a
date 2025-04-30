package view;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow;

import main.TPVMain;
import model.Pedido;
import config.ColorPaleta;

public class VistaCobro extends JPanel implements ActionListener {
    private JLabel mesaLabel;
    private JLabel totalLabel;
    private JTextArea detalleCuentaArea;
    private JButton efectivoButton;
    private JButton tarjetaButton;
    private JButton volverButton;

    private Pedido pedidoActual;
    
    public VistaCobro(TPVMain tpvMain, Pedido pedido) {
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

        // JPanel para boton de volver (SOUTH)
        JPanel volverPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        volverPanel.setBackground(ColorPaleta.FONDO_SECUNDARIO); // Color de fondo gris oscuro
        volverButton = new Boton("Volver");
        volverButton.setActionCommand("volver");
        volverButton.addActionListener(this);
        volverPanel.add(volverButton);
        add(volverPanel, BorderLayout.SOUTH);

    }
}
