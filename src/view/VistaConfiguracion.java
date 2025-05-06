package view;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.concurrent.Flow;

import config.*;
import dao.CategoriaDAO;
import dao.ProductoDAO;
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
        Boton botonCrearPlato = new Boton("Crear Plato");
        botonCrearPlato.addActionListener(e -> abrirFormularioPlato("Crear Plato"));

        Boton botonEliminarPlato = new Boton("Eliminar Plato");
        botonEliminarPlato.addActionListener(e -> abrirFormularioPlato("Eliminar Plato"));

        Boton botonCrearCategoria = new Boton("Crear Categoría");
        botonCrearCategoria.addActionListener(e -> abrirFormularioCategoria("Crear Categoría"));

        Boton botonEliminarCategoria = new Boton("Eliminar Categoría");
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

    // Metodo para abrir formulario para platos
    private void abrirFormularioPlato(String tipo) {
        JDialog dialog = new JDialog(tpvMain, tipo, true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));
        dialog.setLocationRelativeTo(this);

        JLabel labelNombre = new JLabel("Nombre del Producto:");
        JTextField campoNombre = new JTextField();

        JLabel labelDescripcion = new JLabel("Descripción:");
        JTextArea campoDescripcion = new JTextArea();

        JLabel labelPrecio = new JLabel("Precio:");
        JTextField campoPrecio = new JTextField();

        JLabel labelCategoria = new JLabel("Categoría:");
        JComboBox<String> comboCategoria = new JComboBox<>();
        cargarCategoriasEnCombo(comboCategoria); // Método para cargar categorías en el combo
        
        Boton botonGuardar = new Boton("Guardar");
        botonGuardar.addActionListener(e -> {
            String nombre = campoNombre.getText();
            String descripcion = campoDescripcion.getText();
            String precioTexto = campoPrecio.getText();
            String categoria = (String) comboCategorias.getSelectedItem();

            if (nombre.isEmpty() || descripcion.isEmpty() || precioTexto.isEmpty() || categoria == null) {
                JOptionPane.showMessageDialog(dialog, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double precio = Double.parseDouble(precioTexto);
                CategoriaDAO categoriaDAO = new CategoriaDAO();
                int idCategoria = categoriaDAO.obtenerIdCategoriaPorNombre(categoria);

                ProductoDAO productoDAO = new ProductoDAO();
                if (tipo.equals("Crear Producto")) {
                    productoDAO.crearProducto(nombre, descripcion, precio, idCategoria);
                    JOptionPane.showMessageDialog(dialog, "Producto creado con éxito.");
                } else if (tipo.equals("Eliminar Producto")) {
                    productoDAO.eliminarProducto(nombre);
                    JOptionPane.showMessageDialog(dialog, "Producto eliminado con éxito.");
                }

                dialog.dispose(); // Cerrar el diálogo después de guardar
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(labelNombre);
        dialog.add(campoNombre);
        dialog.add(labelDescripcion);
        dialog.add(campoDescripcion);
        dialog.add(labelPrecio);
        dialog.add(campoPrecio);
        dialog.add(labelCategoria);
        dialog.add(comboCategoria);
        dialog.add(new JLabel()); // Espacio vacio
        dialog.add(botonGuardar);

        dialog.setVisible(true); // Mostrar el diálogo

    }

    private void cargarCategoriasEnCombo(JComboBox<String> comboCategorias) {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        ArrayList<String> nombreCategorias = categoriaDAO.nombresCategoriaArray();
        for (String categoria : nombreCategorias) {
            comboCategorias.addItem(categoria);
        }
    }

}
