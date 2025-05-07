package main;
import javax.swing.*;

import config.ColorPaleta;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import view.VistaMesas;
import view.VistaMenu;
import view.Boton;
import view.VistaConfiguracion;

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

        // Panel inicial con botones
        JPanel panelInicial = new JPanel(new GridLayout(3, 1, 20, 20));
        panelInicial.setBackground(ColorPaleta.FONDO_PRINCIPAL);
        Boton botonMesas = new Boton("Gestionar Mesas");
        botonMesas.setActionCommand("Mesas");
        botonMesas.addActionListener(this);

        Boton botonMenu = new Boton("Gestionar Menu");
        botonMenu.setActionCommand("Mesas");
        botonMenu.addActionListener(this);

        Boton botonConfiguracion = new Boton("Configuracion");
        botonConfiguracion.setActionCommand("Configuracion");
        botonConfiguracion.addActionListener(this);

        panelInicial.add(botonMesas);
        panelInicial.add(botonMenu);
        panelInicial.add(botonConfiguracion);
        
        mainPanel.add(panelInicial, "Inicio");
        mainPanel.add(vistaMesas, "Mesas");
        mainPanel.add(vistaMenu, "Menu");
        mainPanel.add(vistaConfiguracion, "Configuracion"); 

        add(mainPanel);

        cardLayout.show(mainPanel, "Inicio"); // Muestra la vista de Mesas al inicio
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Ir al Menu":
                cardLayout.show(mainPanel, "Menu");
                break;
            case "Volver":
                cardLayout.show(mainPanel, "Mesas");
                break;
            case "Configuracion":
                cardLayout.show(mainPanel, "Configuracion");
                break;
            case "Inicio":
                cardLayout.show(mainPanel, "Inicio");
                break;
            default:
                break;
        }
    }

    public void mostrarVista(String vista) {
        cardLayout.show(mainPanel, vista);
    }

    public VistaMesas getVistaMesas() {
        return vistaMesas;
    }
    public VistaMenu getVistaMenu() {
        return vistaMenu;
    }
    public VistaConfiguracion getVistaConfiguracion() {
        return vistaConfiguracion;
    }
}
