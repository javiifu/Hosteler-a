package main;
import javax.swing.*;

import config.ColorPaleta;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import view.VistaMesas;

public class TPVMain extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private VistaMesas vistaMesas;
    private VistaMenu vistaMenu;
    private VistaConfiguracion vistaConfiguracion;


    public TPVMain() {
        setTitle("TPV - Restaurante/Bar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setBackground(ColorPaleta.FONDO_PRINCIPAL); // Color de fondo gris oscuro
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
 
        vistaMesas = new VistaMesas(this);
        vistaMenu = new VistaMenu(this);
        vistaConfiguracion = new VistaConfiguracion(this);

        
        mainPanel.add(vistaMesas, "Mesas");
        mainPanel.add(vistaMenu, "Menu");
        mainPanel.add(vistaConfiguracion, "Configuracion");


        add(mainPanel);

        cardLayout.show(mainPanel, "Mesas"); // Muestra la vista de Mesas al inicio
    }
}
