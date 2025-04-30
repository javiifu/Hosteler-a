package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileReader;
import config.Config;
import javax.swing.*;

public class VistaMesas extends JPanel {
    Config config = new Config(); // Instancia de la clase Config para acceder a la configuración
    public VistaMesas(TPVMain tpvMain) {
        setLayout(new BorderLayout()); // Diseño por regiones
        JLabel titulo = new JLabel("Gestion de mesas", SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH); // Añadir título en la parte superior
        setBackground(new Color(0x222222)); // Color de fondo gris oscuro

        JPanel panelMesas = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Panel para las mesas
        int numeroMesas = config.getNumero_mesas();  // Obtener el número de mesas desde la configuración
        for (int i = 1; i <= numeroMesas; i++) { // Crear botones según el número de mesas configurado
            JButton botonMesa = new JButton("Mesa " + i);
            botonMesa.setActionCommand("Mesa " + i);
            botonMesa.addActionListener(this::mostrarMensaje); // Añadir ActionListener para mostrar mensaje al hacer clic
            panelMesas.add(botonMesa);
        }
        add(new JScrollPane(panelMesas), BorderLayout.CENTER); // Añadir panel de mesas a la parte central

        JButton botonMenu = new JButton("Ir al Menu");
        botonMenu.setActionCommand("Ir al Menu");
        botonMenu.addActionListener(tpvMain); // Usar tpvMain como ActionListener
        add(botonMenu, BorderLayout.SOUTH); // Añadir botón de menú en la parte inferior
    }

    private void mostrarMensaje(ActionEvent e) {
        String command = e.getActionCommand();
        JOptionPane.showMessageDialog(this, "Has seleccionado " + command);
    }
}
