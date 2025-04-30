import javax.swing.*;

import config.ColorPaleta;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import view.VistaMesas;

public class TPVMain extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private VistaMesas vistaMesas;
    private VistaMenu vistaMenu;
    private VistaPedidos vistaPedidos;

    public TPVMain() {
        setTitle("TPV - Restaurante/Bar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setBackground(ColorPaleta.FONDO_PRINCIPAL); // Color de fondo gris oscuro
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        pedidoActual = new Pedido(); 
        vistaMesas = new VistaMesas(this);
        vistaMenu = new VistaMenu(this, pedidoActual);
        vistaPedidos = new VistaPedidos(this, pedidoActual); // Pasar el pedido actual a la vista de pedidos
        
        mainPanel.add(vistaMesas, "Mesas");
        mainPanel.add(vistaMenu, "Menu");
        mainPanel.add(vistaPedidos, "Pedidos");

        add(mainPanel);

        cardLayout.show(mainPanel, "Mesas"); // Muestra la vista de Mesas al inicio
    }
}
