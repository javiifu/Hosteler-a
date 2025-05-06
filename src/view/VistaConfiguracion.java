package view;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.Flow;

import config.*;
import main.TPVMain;

public class VistaConfiguracion extends JPanel{
    private TPVMain tpvMain;

    public VistaConfiguracion(TPVMain tpvMain) {
        this.tpvMain = tpvMain;

        setLayout(new BorderLayout());
        setBackground(ColorPaleta.FONDO_SECUNDARIO);

        // Título
        JLabel titulo = new JLabel("Configuración", SwingConstants.CENTER);
        titulo.setForeground(ColorPaleta.TEXTO_PRINCIPAL_CLARO);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        // Panel central con botones
        JPanel panelOpciones = new JPanel(new GridLayout(4, 2, 20, 20));
        panelOpciones.setBackground(ColorPaleta.FONDO_SECUNDARIO);

        // Botones para las opciones
        JButton botonCrearPlato = new JButton("Crear Plato");
        botonCrearPlato.addActionListener(e -> abrirFormularioPlato("Crear Plato"));

        JButton botonEliminarPlato = new JButton("Eliminar Plato");
        botonEliminarPlato.addActionListener(e -> abrirFormularioPlato("Eliminar Plato"));

        JButton botonCrearCategoria = new JButton("Crear Categoría");
        botonCrearCategoria.addActionListener(e -> abrirFormularioCategoria("Crear Categoría"));

        JButton botonEliminarCategoria = new JButton("Eliminar Categoría");
        botonEliminarCategoria.addActionListener(e -> abrirFormularioCategoria("Eliminar Categoría"));

        // Añadir botones al panel
        panelOpciones.add(botonCrearPlato);
        panelOpciones.add(botonEliminarPlato);
        panelOpciones.add(botonCrearCategoria);
        panelOpciones.add(botonEliminarCategoria);

        add(panelOpciones, BorderLayout.CENTER);

        // Botón para volver al menú principal
        JButton botonVolver = new JButton("Volver");
        botonVolver.addActionListener(e -> tpvMain.mostrarVista("Mesas"));
        add(botonVolver, BorderLayout.SOUTH);
    }

}
