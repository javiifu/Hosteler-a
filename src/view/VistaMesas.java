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

public class VistaMesas extends JPanel {
    TPVMain tpvMain; 
    Config config = new Config(); // Instancia de la clase Config para acceder a la configuración
    MesaDAO mesaDAO = new MesaDAO(); // Instancia de la clase MesaDAO para acceder a la base de datos
    JPanel panelMesas; // Panel para las mesas

    public VistaMesas(TPVMain tpvMain) {
        this.tpvMain = tpvMain;

        setLayout(new BorderLayout()); // Diseño por regiones
        JLabel titulo = new JLabel("Gestión de Mesas", SwingConstants.CENTER);
        setBackground(ColorPaleta.FONDO_SECUNDARIO); // Color de fondo gris oscuro
        titulo.setForeground(ColorPaleta.TEXTO_PRINCIPAL_CLARO); // Color del texto del título
        add(titulo, BorderLayout.NORTH); // Añadir título en la parte superior

        // Panel para las mesas
        panelMesas = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelMesas.setBackground(ColorPaleta.FONDO_SECUNDARIO); // Color de fondo del panel de mesas
        add(new JScrollPane(panelMesas), BorderLayout.CENTER); // Añadir panel de mesas a la parte central

        // Panel inferior con botones
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Boton botonMenu = new Boton("Ir al Menú", ColorPaleta.VOLVER, ColorPaleta.HOVER_VOLVER); // Botón para ir al menú principal
        botonMenu.addActionListener(e -> tpvMain.mostrarVista("Inicio"));

        Boton botonActualizar = new Boton("Actualizar Mesas", ColorPaleta.PRIMARIO, ColorPaleta.HOVER_PRIMARIO); // Botón para actualizar mesas
        botonActualizar.addActionListener(e -> actualizarVistaMesas()); // Vincular al método de actualización

        panelBoton.add(botonActualizar); // Añadir botón de actualizar al panel
        panelBoton.add(botonMenu); // Añadir botón de menú al panel
        panelBoton.setBackground(ColorPaleta.FONDO_SECUNDARIO); // Color de fondo del panel de botones
        add(panelBoton, BorderLayout.SOUTH); // Añadir panel de botones en la parte inferior

        // Cargar las mesas al iniciar
        actualizarVistaMesas();
    }

    /**
     * Método para actualizar la vista de mesas.
     */
    private void actualizarVistaMesas() {
        panelMesas.removeAll(); // Limpiar el panel de mesas

        ArrayList<Mesa> mesas = mesaDAO.getMesas(); // Obtener la lista actualizada de mesas desde la base de datos
        for (Mesa mesa : mesas) {
            int numeroMesa = mesa.getNumero(); // Obtener el número de la mesa
            Boton botonMesa = new Boton("Mesa " + numeroMesa, ColorPaleta.PRIMARIO, ColorPaleta.HOVER_PRIMARIO); // Crear botón para cada mesa
            botonMesa.setActionCommand("Mesa " + numeroMesa); // Establecer el comando de acción del botón
            botonMesa.addActionListener(e -> {
                tpvMain.getVistaMenu().setMesaSeleccionada(numeroMesa); // Establecer la mesa seleccionada en la vista de menú
                tpvMain.mostrarVista("Menu");
            });
            panelMesas.add(botonMesa); // Añadir botón al panel de mesas
        }

        panelMesas.revalidate(); // Volver a validar el panel para aplicar los cambios
        panelMesas.repaint(); // Repintar el panel para reflejar los cambios
    }
}
