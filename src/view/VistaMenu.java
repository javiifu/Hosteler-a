package view;

import config.ColorPaleta;
import dao.CategoriaDAO;
import dao.ProductoDAO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import main.TPVMain;
import model.Categoria;
import model.Mesa;
import model.Producto;

public class VistaMenu extends JPanel {
    // Atributos
    private TPVMain tpvMain;
    private Mesa mesaSeleccionada;
    private JList<Categoria> listaCategorias;
    private JList<Producto> listaProductos;
    private JTextArea areaPedido;
    private JButton botonVolver;
    private JButton botonCobrar;
    private CardLayout cardLayoutCentral;
    private JPanel panelCentral;

    public VistaMenu(TPVMain tpvMain) {
        this.tpvMain = tpvMain;
        this.mesaSeleccionada = mesaSeleccionada;

        setLayout(new BorderLayout());
        setBackground(ColorPaleta.FONDO_SECUNDARIO);

        // Titulo
        JLabel titulo = new JLabel("Menu - Mesa " + mesaSeleccionada.getNumero(), SwingConstants.CENTER);
        titulo.setForeground(ColorPaleta.TEXTO_PRINCIPAL_CLARO);
        add(titulo, BorderLayout.NORTH);

        // Panel central con CardLayout
        cardLayoutCentral = new CardLayout();
        panelCentral = new JPanel(cardLayoutCentral);
        panelCentral.setBackground(ColorPaleta.FONDO_SECUNDARIO);

        // Panel para categorías
        JPanel panelCategorias = new JPanel(new BorderLayout());
        DefaultListModel<Categoria> modeloCategorias = new DefaultListModel<>();
        listaCategorias = new JList<>(modeloCategorias);
        listaCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaCategorias.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarProductosDeCategoria();
            }
        });
        panelCategorias.add(new JScrollPane(listaCategorias), BorderLayout.CENTER);
        panelCentral.add(panelCategorias, "Categorias");

        // Panel para productos
        JPanel panelProductos = new JPanel(new BorderLayout());
        DefaultListModel<Producto> modeloProductos = new DefaultListModel<>();
        listaProductos = new JList<>(modeloProductos);
        listaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaProductos.addListSelectionListener(e -> agregarProductoAPedido());
        panelProductos.add(new JScrollPane(listaProductos), BorderLayout.CENTER);

        // Botón para volver a las categorías
        JButton botonVolverCategorias = new JButton("Volver a Categorías");
        botonVolverCategorias.addActionListener(e -> cardLayoutCentral.show(panelCentral, "Categorias"));
        panelProductos.add(botonVolverCategorias, BorderLayout.SOUTH);

        panelCentral.add(panelProductos, "Productos");

        add(panelCentral, BorderLayout.CENTER);

        // Área de texto para mostrar el pedido
        areaPedido = new JTextArea();
        areaPedido.setEditable(false);
        areaPedido.setBackground(ColorPaleta.TEXTAREA_FONDO);
        areaPedido.setForeground(ColorPaleta.TEXTAREA_TEXTO);
        add(new JScrollPane(areaPedido), BorderLayout.EAST);

        // Panel inferior para botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(ColorPaleta.FONDO_SECUNDARIO);

        botonVolver = new JButton("Volver");
        botonVolver.addActionListener(e -> tpvMain.mostrarVista("Mesas"));
        panelBotones.add(botonVolver);

        botonCobrar = new JButton("Cobrar");
        botonCobrar.addActionListener(e -> tpvMain.mostrarVista("Cobro"));
        panelBotones.add(botonCobrar);

        add(panelBotones, BorderLayout.SOUTH);

        // Cargar categorías al iniciar
        cargarCategorias();
    }

    private void cargarCategorias() {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        ArrayList<String> nombresCategorias = categoriaDAO.nombresCategoriaArray();
        DefaultListModel<Categoria> modeloCategorias = (DefaultListModel<Categoria>) listaCategorias.getModel();
        modeloCategorias.clear();
        for (String nombre : nombresCategorias) {
            modeloCategorias.addElement(new Categoria(nombre));
        }
        cardLayoutCentral.show(panelCentral, "Categorias");
    }

    private void cargarProductosDeCategoria() {
        Categoria categoriaSeleccionada = listaCategorias.getSelectedValue();
        if (categoriaSeleccionada != null) {
            ProductoDAO productoDAO = new ProductoDAO();
            ArrayList<Producto> productos = productoDAO.obtenerProductosPorCategoriaArray(categoriaSeleccionada.getCodigo());
            DefaultListModel<Producto> modeloProductos = (DefaultListModel<Producto>) listaProductos.getModel();
            modeloProductos.clear();
          
            for (Producto producto : productos) {
                modeloProductos.addElement((producto));
            }
            cardLayoutCentral.show(panelCentral, "Productos");
        }
    }

    private void agregarProductoAPedido() {
        Producto productoSeleccionado = listaProductos.getSelectedValue();
        if (productoSeleccionado != null) {
            areaPedido.append(productoSeleccionado.getNombre() + "\n");
        }
    }

    public void setMesaSeleccionada(int numeroMesa) {
        // Actualizar la mesa seleccionada
        this.mesaSeleccionada = new Mesa(numeroMesa);

        // Actualizar el título de la vista
        JLabel titulo = (JLabel) getComponent(0);
        titulo.setText("Menu - Mesa " + numeroMesa);

        // Limpiar el área de texto del pedido
        areaPedido.setText("");
    }
}
