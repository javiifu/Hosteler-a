package view;

import javax.swing.*;
import java.awt.*;

import main.TPVMain;
import model.Pedido;
import config.ColorPaleta;

public class VistaCobro extends JPanel {
    private JTextArea areaPedido;
    private JLabel etiquetaTotal;
    private JButton botonEfectivo;
    private JButton botonTarjeta;
    private JButton botonVolver;
    
    public VistaCobro(TPVMain tpvMain, Pedido pedido) {
        setLayout(new BorderLayout());
        JLabel titulo = new JLabel("Cobro", SwingConstants.CENTER);
        setBackground(ColorPaleta.FONDO_SECUNDARIO); // Color de fondo gris oscuro
        titulo.setForeground(ColorPaleta.TEXTO_PRINCIPAL_CLARO); // Color del texto del título
        add(titulo, BorderLayout.NORTH); // Añadir título en la parte superior

        areaPedido = new JTextArea(10, 30);
        areaPedido.setEditable(false); // Hacer el área de texto no editable
        areaPedido.setBackground(ColorPaleta.TEXTAREA_FONDO); // Color de fondo del área de texto
        areaPedido.setForeground(ColorPaleta.TEXTAREA_TEXTO); // Color del texto del área de texto
        add(new JScrollPane(areaPedido), BorderLayout.CENTER); // Añadir área de texto con scroll

        
        botonEfectivo = new Boton("Pagar en Efectivo");
        botonEfectivo.setBackground(ColorPaleta.BOTON_PRIMARIO_FONDO); // Color de fondo del botón
        botonEfectivo.setForeground(ColorPaleta.BOTON_PRIMARIO_TEXTO); // Color del texto del botón
        botonEfectivo.setActionCommand("Pagar en Efectivo");
        botonEfectivo.addActionListener(tpvMain); // Usar tpvMain como ActionListener
        
        botonTarjeta = new Boton("Pagar con Tarjeta");
        botonTarjeta.setBackground(ColorPaleta.BOTON_PRIMARIO_FONDO); // Color de fondo del botón
        botonTarjeta.setForeground(ColorPaleta.BOTON_PRIMARIO_TEXTO); // Color del texto del botón
        botonTarjeta.setActionCommand("Pagar con Tarjeta");
        botonTarjeta.addActionListener(tpvMain); // Usar tpvMain como ActionListener

        JPanel panelBotones = new JPanel( new GridLayout(2, 1, 0, 10)); // Panel para los botones
        panelBotones.setBackground(ColorPaleta.FONDO_SECUNDARIO); // Color de fondo del panel de botones
        panelBotones.add(botonEfectivo); // Añadir botón de efectivo
        panelBotones.add(botonTarjeta); // Añadir botón de tarjeta
        add(panelBotones, BorderLayout.EAST); // Añadir panel de botones a la derecha
    }
}
