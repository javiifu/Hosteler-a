package view;
import config.*;
import dao.CategoriaDAO;
import dao.MesaDAO;
import dao.ProductoDAO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import main.App;
import main.TPVMain;
import model.Mesa;

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
        JPanel panelOpciones = new JPanel(new GridLayout(4, 3, 10, 10));
        panelOpciones.setBackground(ColorPaleta.FONDO_SECUNDARIO);

        // Botones para las opciones
        Boton botonCrearPlato = new Boton("Crear Plato", ColorPaleta.ENFASIS_ACCION, ColorPaleta.HOVER_ENFASIS_ACCION);
        botonCrearPlato.addActionListener(e -> abrirFormularioProducto("Crear Producto"));

        Boton botonModificarPlato = new Boton("Modificar Plato",  ColorPaleta.NARANJA_BOTON, ColorPaleta.NARANJA_HOVER);
        botonModificarPlato.addActionListener(e -> abrirFormularioProducto("Modificar Producto"));
        
        Boton botonEliminarPlato = new Boton("Eliminar Plato",ColorPaleta.ACENTO, ColorPaleta.HOVER_ACENTO);
        botonEliminarPlato.addActionListener(e -> abrirFormularioProducto("Eliminar Producto"));
        
        Boton botonCrearCategoria = new Boton("Crear Categoría", ColorPaleta.ENFASIS_ACCION, ColorPaleta.HOVER_ENFASIS_ACCION);
        botonCrearCategoria.addActionListener(e -> abrirFormularioCategoria("Crear Categoría"));
        
        Boton botonEliminarCategoria = new Boton("Eliminar Categoría", ColorPaleta.ACENTO, ColorPaleta.HOVER_ACENTO);
        botonEliminarCategoria.addActionListener(e -> abrirFormularioCategoria("Eliminar Categoría"));

        Boton botonCrearMesa = new Boton("Crear Mesa", ColorPaleta.ENFASIS_ACCION, ColorPaleta.HOVER_ENFASIS_ACCION);
        botonCrearMesa.addActionListener(e -> abrirFormularioMesa("Crear Mesa"));

        Boton botonEliminarMesa = new Boton("Eliminar Mesa", ColorPaleta.ACENTO, ColorPaleta.HOVER_ACENTO);
        botonEliminarMesa.addActionListener(e -> abrirFormularioMesa("Eliminar Mesa"));
        
        Boton botonGenerarResumenDiario = new Boton("Generar Resumen del Dia", ColorPaleta.ENFASIS_ACCION, ColorPaleta.HOVER_ENFASIS_ACCION);

        botonGenerarResumenDiario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    App.generarResumenDia();
                    JOptionPane.showMessageDialog(tpvMain, "Resumen del día generado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(tpvMain, "Error al generar el resumen del día.", "Error", JOptionPane.ERROR_MESSAGE);
                    error.printStackTrace();
                }
            }
        });

        // Añadir botones al panel
            //1 Linea
        panelOpciones.add(botonCrearPlato);
        panelOpciones.add(botonModificarPlato);
        panelOpciones.add(botonEliminarPlato);
            //2 Linea
        panelOpciones.add(botonCrearCategoria);
        panelOpciones.add(new JLabel());
        panelOpciones.add(botonEliminarCategoria);
            //3 Linea
        panelOpciones.add(botonCrearMesa);
        panelOpciones.add(new JLabel());
        panelOpciones.add(botonEliminarMesa);
            //4 Linea
            
        panelOpciones.add(botonGenerarResumenDiario);

        add(panelOpciones, BorderLayout.CENTER);

        // Botón para volver al menú principal
        Boton botonVolver = new Boton("Volver", ColorPaleta.VOLVER, ColorPaleta.HOVER_VOLVER);
        botonVolver.addActionListener(e -> tpvMain.mostrarVista("Inicio"));
        add(botonVolver, BorderLayout.SOUTH);
    }

    // Metodo para abrir formulario para platos
    private void abrirFormularioProducto(String tipo) {
        ProductoDAO productoDAO = new ProductoDAO();

        JDialog dialog = new JDialog(tpvMain, tipo, true);
        dialog.setSize(600, 250);
        dialog.setLayout(new GridLayout(4, 4, 10, 10));
        dialog.setLocationRelativeTo(this);

        JLabel labelNombre = new JLabel("Nombre del Producto:");
        JLabel labelNuevoNombre = new JLabel("Nuevo nombre:");
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

        
       /* JLabel labelDescripcion = new JLabel("Descripción:");
        JTextArea campoDescripcion = new JTextArea(); */

        JLabel labelDescripcion = new JLabel("Descripción:");
        JTextField campoDescripcion = new JTextField();

        JLabel labelPrecio = new JLabel("<html> Precio: <br> Ejemplo 0.0 <html>");
        JTextField campoPrecio = new JTextField();

        JLabel labelCategoria = new JLabel("Categoría:");
        JLabel labelCategoriaModificar = new JLabel("<html> Categoría: <br> <font color='red'> Fijarse en la categoria <html>");
        comboCategorias.removeAllItems(); // Limpia el combo si ya tiene datos
        cargarCategoriasEnCombo(comboCategorias); // Método para cargar categorías en el combo
        
        Boton botonGuardar = new Boton("Guardar", ColorPaleta.ENFASIS_ACCION, ColorPaleta.HOVER_ENFASIS_ACCION);
        botonGuardar.addActionListener(e -> {
            String nombre = null;
            String categoria = (String) comboCategorias.getSelectedItem();
            boolean modificado = false;

            if (tipo.equals("Eliminar Producto")) {
                nombre = (String) comboProductos.getSelectedItem(); // desplegable
                if (nombre == null || categoria == null) {
                    JOptionPane.showMessageDialog(dialog, "Por favor, seleccione un producto y una categoría.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    
                    productoDAO.borrarProductoPorNombre(nombre);
                    JOptionPane.showMessageDialog(dialog, "Producto eliminado con éxito.");
                    dialog.dispose(); // Cerrar el diálogo después de guardar
                    
                }  catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "El producto no se ha eliminado.", "Error", JOptionPane.ERROR_MESSAGE);
            
                }
        
            } else if (tipo.equals("Crear Producto")) {
                nombre = campoNombre.getText();
                String descripcion = campoDescripcion.getText();
                String precioTexto = campoPrecio.getText();
        
                if (nombre.isEmpty() || descripcion.isEmpty() || precioTexto.isEmpty() || categoria == null) {
                    JOptionPane.showMessageDialog(dialog, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                try {
                    double precio = Double.parseDouble(precioTexto);
                    CategoriaDAO categoriaDAO = new CategoriaDAO();
                    int idCategoria = categoriaDAO.ObtenerCategoriaPorNombre(categoria);
                    if(precio<0){
                        JOptionPane.showMessageDialog(dialog, "Por favor, ingresa un precio valido.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    productoDAO.crearProducto(nombre, descripcion, precio, idCategoria);
                    JOptionPane.showMessageDialog(dialog, "Producto creado con éxito.");
                    dialog.dispose(); // Cerrar el diálogo después de guardar
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else if (tipo.equals("Modificar Producto")) {
                String nombreAntiguo = (String) comboProductos.getSelectedItem();
                nombre = campoNombre.getText();
                String descripcion = campoDescripcion.getText();
                String precioTexto = campoPrecio.getText();
                categoria = (String) comboCategorias.getSelectedItem();
                int idProductoAModificar = -1;
                modificado = false;
            
                try {
                    idProductoAModificar = productoDAO.obtenerIdProductoPorNombre(nombreAntiguo);
            
                    if (idProductoAModificar != -1) {
                        if (!nombre.isEmpty()) {
                            modificado = productoDAO.actualizarCampoProducto("nombre", idProductoAModificar, nombre) || modificado;
                        }
                        if (!descripcion.isEmpty()) {
                            modificado = productoDAO.actualizarCampoProducto("descripcion", idProductoAModificar, descripcion) || modificado;
                        }
                        if (!precioTexto.isEmpty()) {
                            try {
                                double precio = Double.parseDouble(precioTexto);
                                modificado = productoDAO.actualizarCampoProducto("precio", idProductoAModificar, precio) || modificado;
                                if(precio<0){
                                    JOptionPane.showMessageDialog(dialog, "Por favor, ingresa un precio valido.", "Error", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(dialog, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        if (categoria != null) {
                            CategoriaDAO categoriaDAO = new CategoriaDAO();
                            Integer idCategoria = categoriaDAO.ObtenerCategoriaPorNombre(categoria);
                            modificado = productoDAO.actualizarCampoProducto("id_categoria", idProductoAModificar, idCategoria) || modificado;
                        }
            
                        if (modificado) {
                            JOptionPane.showMessageDialog(dialog, "Producto modificado con éxito.");
                            dialog.dispose();
                        } else {
                            JOptionPane.showMessageDialog(dialog, "No se modificó ningún campo.",  "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Error: No se encontró el producto a modificar.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    System.err.println("Error al obtener o modificar el producto: " + ex.getMessage());
                    JOptionPane.showMessageDialog(dialog, "Error al intentar modificar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        dialog.add(labelNombre);
        if (tipo.equals("Eliminar Producto")) {
            dialog.add(comboProductos);//desplegable
            dialog.add(labelCategoria);
        } else if (tipo.equals("Modificar Producto")) {
            cargarTodosProductosCombo(comboProductos);
            dialog.add(labelNombre);
            dialog.add(comboProductos);//desplegable
            dialog.add(new JLabel()); // Espacio vacio
            dialog.add(new JLabel()); // Espacio vacio
            dialog.add(labelNuevoNombre);
            dialog.add(campoNombre);
            dialog.add(labelDescripcion);
            dialog.add(campoDescripcion);
            dialog.add(labelPrecio);
            dialog.add(campoPrecio);
            dialog.add(labelCategoriaModificar);
            
        }else  {
            dialog.add(campoNombre);
            dialog.add(labelDescripcion);
            dialog.add(campoDescripcion);
            dialog.add(labelPrecio);
            dialog.add(campoPrecio);
            dialog.add(labelCategoria);
        }
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

        Boton botonGuardar = new Boton("Guardar", ColorPaleta.ENFASIS_ACCION, ColorPaleta.HOVER_ENFASIS_ACCION);
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

    // Metodo para abrir formulario para mesas
    private void abrirFormularioMesa(String tipo) {
        JComboBox<Integer> comboMesas = new JComboBox<>();
        JDialog dialog = new JDialog(tpvMain, tipo, true);
        dialog.setSize(300, 250);
        dialog.setLayout(new GridLayout(3, 2, 10, 10));
        dialog.setLocationRelativeTo(this);

        JLabel labelMesa = new JLabel("Número de la mesa:");
        JTextField campoNumero = new JTextField();
        
        if (tipo.equalsIgnoreCase("Eliminar Mesa")) {
            cargarNumerosMesaCombo(comboMesas);
        }

        Boton botonGuardar = new Boton("Guardar", ColorPaleta.ENFASIS_ACCION, ColorPaleta.HOVER_ENFASIS_ACCION);
        botonGuardar.addActionListener(e -> {
            Integer numero = null;

            if (tipo.equalsIgnoreCase("Eliminar mesa")) {
                numero = (Integer) comboMesas.getSelectedItem(); 
            } else {
                try {
                    numero = Integer.parseInt(campoNumero.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Ingrese un número válido para la mesa", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
    
            if (numero == null) { 
                JOptionPane.showMessageDialog(dialog, "Seleccione o ingrese el número de la mesa", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            MesaDAO mesaDAO = new MesaDAO();
            if (tipo.equalsIgnoreCase("Crear Mesa")) {
                numero = Integer.parseInt(campoNumero.getText());
                Mesa mesa = new Mesa(numero);
                mesaDAO.newMesa(mesa);
                JOptionPane.showMessageDialog(dialog, "Mesa creada con exito");      
            } else if (tipo.equals("Eliminar Mesa")) {a
                numero = (Integer) comboMesas.getSelectedItem();
                mesaDAO.deleteMesa(numero);
                JOptionPane.showMessageDialog(dialog, "Mesa eliminada con exito");
            }

            dialog.dispose();
        });

        dialog.add(labelMesa);
        if (tipo.equalsIgnoreCase("Eliminar Mesa")) {
            dialog.add(comboMesas);//desplegable si es eliminar
        } else {
            dialog.add(campoNumero);
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
    //Metodo abrir desplegable todos productos
    private void cargarTodosProductosCombo(JComboBox<String> comboProductos) {
        comboProductos.removeAllItems();
        ProductoDAO productoDAO = new ProductoDAO();
        ArrayList<String> productos = productoDAO.nombresProdcutoArray();
        for (String nombre : productos) {
            comboProductos.addItem(nombre);
        }
    }

    //Metodo abrir desplegable mesas
    private void cargarNumerosMesaCombo(JComboBox<Integer> comboMesas) {
        comboMesas.removeAllItems();
        MesaDAO mesaDAO = new MesaDAO();
        ArrayList<Integer> numerosMesa = mesaDAO.obtenerMesas();
        for (Integer numero : numerosMesa) {
            comboMesas.addItem(numero);
        }
    }

}
