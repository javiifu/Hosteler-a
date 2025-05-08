package view;
import config.*;
import dao.CategoriaDAO;
import dao.ProductoDAO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import main.TPVMain;

public class VistaConfiguracion extends JPanel{
    private TPVMain tpvMain;
    JComboBox<String> comboCategorias = new JComboBox<>();

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
        botonCrearPlato.setBackground(ColorPaleta.ENFASIS_ACCION);
        botonCrearPlato.addActionListener(e -> abrirFormularioProducto("Crear Producto"));
        
        Boton botonEliminarPlato = new Boton("Eliminar Plato");
        botonEliminarPlato.setBackground(ColorPaleta.BOTON_ACENTO_FONDO);
        botonEliminarPlato.addActionListener(e -> abrirFormularioProducto("Eliminar Producto"));
        
        Boton botonCrearCategoria = new Boton("Crear Categoría");
        botonCrearCategoria.setBackground(ColorPaleta.ENFASIS_ACCION);
        botonCrearCategoria.addActionListener(e -> abrirFormularioCategoria("Crear Categoría"));
        
        Boton botonEliminarCategoria = new Boton("Eliminar Categoría");
        botonEliminarCategoria.setBackground(ColorPaleta.BOTON_ACENTO_FONDO);
        botonEliminarCategoria.addActionListener(e -> abrirFormularioCategoria("Eliminar Categoría"));

        // Añadir botones al panel
        panelOpciones.add(botonCrearPlato);
        panelOpciones.add(botonEliminarPlato);
        panelOpciones.add(botonCrearCategoria);
        panelOpciones.add(botonEliminarCategoria);

        add(panelOpciones, BorderLayout.CENTER);

        // Botón para volver al menú principal
        JButton botonVolver = new JButton("Volver");
        botonVolver.addActionListener(e -> tpvMain.mostrarVista("Inicio"));
        add(botonVolver, BorderLayout.SOUTH);
    }

    // Metodo para abrir formulario para platos
    private void abrirFormularioProducto(String tipo) {
        JDialog dialog = new JDialog(tpvMain, tipo, true);
        dialog.setSize(600, 250);
        dialog.setLayout(new GridLayout(3, 4, 10, 10));
        dialog.setLocationRelativeTo(this);

        JLabel labelNombre = new JLabel("Nombre del Producto:");
        JTextField campoNombre = new JTextField();
        JComboBox<String> comboProductos = new JComboBox<>();

        if (tipo.equals("Eliminar Producto")) {
                    
            comboCategorias.addActionListener(e -> {
                String categoriaSeleccionada = (String) comboCategorias.getSelectedItem();
                if (categoriaSeleccionada != null) {
                    cargarProductosPorCategoriaEnCombo(comboProductos, categoriaSeleccionada);
                }
            });
        
            // Cargar productos al iniciar si ya hay categoría seleccionada
            if (comboCategorias.getItemCount() > 0) {
                cargarProductosPorCategoriaEnCombo(comboProductos, (String) comboCategorias.getSelectedItem());
            }
        }

        JLabel labelDescripcion = new JLabel("Descripción:");
        JTextArea campoDescripcion = new JTextArea();

        JLabel labelPrecio = new JLabel("<html> Precio: <br> Ejemplo 0.0 <html>");
        JTextField campoPrecio = new JTextField();

        JLabel labelCategoria = new JLabel("Categoría:");
        comboCategorias.removeAllItems(); // Limpia el combo si ya tiene datos
        cargarCategoriasEnCombo(comboCategorias); // Método para cargar categorías en el combo
        
        Boton botonGuardar = new Boton("Guardar");
        botonGuardar.addActionListener(e -> {
            String nombre; 
            if (tipo.equals("Eliminar Producto")) {
                nombre = (String) comboProductos.getSelectedItem(); //desplegable
            } else {
                nombre = campoNombre.getText();
            }
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
                int idCategoria = categoriaDAO.ObtenerCategoriaPorNombre(categoria);

                ProductoDAO productoDAO = new ProductoDAO();
                if (tipo.equals("Crear Producto")) {
                    productoDAO.crearProducto(nombre, descripcion, precio, idCategoria);
                    JOptionPane.showMessageDialog(dialog, "Producto creado con éxito.");
                } else if (tipo.equals("Eliminar Producto")) {
                    productoDAO.borrarProductoPorNombre(nombre);
                    JOptionPane.showMessageDialog(dialog, "Producto eliminado con éxito.");
                }

                dialog.dispose(); // Cerrar el diálogo después de guardar
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(labelNombre);
        if (tipo.equals("Eliminar Producto")) {
            dialog.add(comboProductos);//desplegable

        } else {
            dialog.add(campoNombre);
            dialog.add(labelDescripcion);
            dialog.add(campoDescripcion);
            dialog.add(labelPrecio);
            dialog.add(campoPrecio);
        }
        dialog.add(labelCategoria);
        dialog.add(comboCategorias);
        dialog.add(new JLabel()); // Espacio vacio
        dialog.add(botonGuardar);

        dialog.setVisible(true); // Mostrar el diálogo

    }

    // Metodo para abrir formulario para categorias
    private void abrirFormularioCategoria(String tipo) {
        JDialog dialog = new JDialog(tpvMain, tipo, true);
        dialog.setSize(600, 250);
        dialog.setLayout(new GridLayout(3, 4, 10, 10));
        dialog.setLocationRelativeTo(this);

        JLabel labelNombre = new JLabel("Nombre de la Categoria:");
        JTextField campoNombre = new JTextField();
        

        if (tipo.equalsIgnoreCase("Eliminar Categoría")) {
            cargarCategoriasEnCombo(comboCategorias);
        }

        Boton botonGuardar = new Boton("Guardar");
        botonGuardar.addActionListener(e -> {
            String nombre = campoNombre.getText();

            if (tipo.equalsIgnoreCase("Eliminar Categoría")) {
                nombre = (String) comboCategorias.getSelectedItem();
            } else {
                nombre = campoNombre.getText();
            }
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Por favor, ingrese el nombre de la categoria", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            CategoriaDAO categoriaDAO = new CategoriaDAO();
            if (tipo.equalsIgnoreCase("Crear Categoría")) {
                categoriaDAO.crearCategoria(nombre);
                JOptionPane.showMessageDialog(dialog, "Categoria creada con exito");      
            } else if (tipo.equals("Eliminar Categoría")) {
                categoriaDAO.borrarCategoriaPorNombre(nombre);
                JOptionPane.showMessageDialog(dialog, "Categoria eliminada con exito");
            }

            dialog.dispose();
        });

        dialog.add(labelNombre);
        if (tipo.equalsIgnoreCase("Eliminar Categoría")) {
            dialog.add(comboCategorias);//desplegable si es eliminar
        } else {
            dialog.add(campoNombre);
        }
        dialog.add(new JLabel()); // Espacio en blanco
        dialog.add(botonGuardar);

        dialog.setVisible(true); // Mostrar el dialogo


    }
    private void cargarCategoriasEnCombo(JComboBox<String> comboCategorias) {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        ArrayList<String> nombreCategorias = categoriaDAO.nombresCategoriaArray();
        for (String categoria : nombreCategorias) {
            comboCategorias.addItem(categoria);
        }
    }

    //Metodo abrir desplegable productos
    private void cargarProductosPorCategoriaEnCombo(JComboBox<String> comboProductos, String nombreCategoria) {
        comboProductos.removeAllItems();
        ProductoDAO productoDAO = new ProductoDAO();
        ArrayList<String> productos = productoDAO.obtenerNombresPorCategoria(nombreCategoria);
        for (String nombre : productos) {
            comboProductos.addItem(nombre);
        }
    }

}
