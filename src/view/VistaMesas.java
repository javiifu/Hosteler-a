package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import config.*;
import dao.MesaDAO;
import main.TPVMain;
import model.Mesa;

import javax.swing.*;

public class VistaMesas extends JPanel implements ActionListener {
    TPVMain tpvMain; 
    Config config = new Config(); // Instancia de la clase Config para acceder a la configuración
    MesaDAO mesaDAO = new MesaDAO(); // Instancia de la clase MesaDAO para acceder a la base de datos
    public VistaMesas(TPVMain tpvMain) {
        setLayout(new BorderLayout()); // Diseño por regiones
        JLabel titulo = new JLabel("Gestion de mesas", SwingConstants.CENTER);
        setBackground(ColorPaleta.FONDO_SECUNDARIO); // Color de fondo gris oscuro
        titulo.setForeground(ColorPaleta.TEXTO_PRINCIPAL_CLARO); // Color del texto del título
        add(titulo, BorderLayout.NORTH); // Añadir título en la parte superior

        JPanel panelMesas = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Panel para las mesas
        panelMesas.setBackground(ColorPaleta.FONDO_SECUNDARIO); // Color de fondo del panel de mesas
        
        ArrayList<Mesa> mesas = mesaDAO.getMesas();  // Obtener el número de mesas desde la configuración
        for (int i = 0; i < mesas.size(); i++) {
            int numeroMesa = mesas.get(i).getNumero(); // Crear botones según el número de mesas configurado
            Boton botonMesa = new Boton("Mesa " + numeroMesa); // Crear botón para cada mesa
            botonMesa.setActionCommand("Mesa " + numeroMesa); // Establecer el comando de acción del botón
            botonMesa.addActionListener(tpvMain); // Usar tpvMain como ActionListener
            botonMesa.addActionListener(e -> {
                tpvMain.getVistaMenu().setMesaSeleccionada(numeroMesa); // Establecer la mesa seleccionada en la vista de menú
                tpvMain.mostrarVista("Menu");
                
            }); // Mostrar mensaje al hacer clic
            
            panelMesas.add(botonMesa); // Añadir botón al panel de mesas
        }
        add(new JScrollPane(panelMesas), BorderLayout.CENTER); // Añadir panel de mesas a la parte central

        Boton botonMenu = new Boton("Ir al Menu"); // Crear botón para ir al menú
        botonMenu.setActionCommand("Ir al Menu");
        botonMenu.addActionListener(this); 
        add(botonMenu, BorderLayout.SOUTH); // Añadir botón de menú en la parte inferior
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Ir al Menu")) {
            tpvMain.mostrarVista("Inicio"); // Cambiar a la vista de menú
        }
    }
}
