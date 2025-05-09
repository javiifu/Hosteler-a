package view;

import config.ColorPaleta;
import dao.CategoriaDAO;
import dao.PedidoDAO;
import dao.ProductoDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.*;
import main.TPVMain;
import model.Categoria;
import model.Mesa;
import model.Pedido;
import model.PedidoPlato;
import model.Producto;

public class VistaMenu extends JPanel {
    // Atributos
    private TPVMain tpvMain;
    private Mesa mesaSeleccionada;
    private JList<Categoria> listaCategorias;
    private JList<Producto> listaProductos;
    private JTextArea areaPedido;
    private Boton botonVolver;
    private Boton botonCobrar;
    private Boton botonEliminarProducto;
    private Boton botonGuardarPedido; // Nuevo botón para guardar el pedido
    private CardLayout cardLayoutCentral;
    private JPanel panelCentral;
    private ArrayList<PedidoPlato> productosDelPedido = new ArrayList<>();

    public VistaMenu(TPVMain tpvMain) {
        this.tpvMain = tpvMain;
        this.mesaSeleccionada = mesaSeleccionada;

        setLayout(new BorderLayout());
        setBackground(ColorPaleta.FONDO_SECUNDARIO);

        // Titulo
        JLabel titulo = new JLabel("Menu - Mesa ", SwingConstants.CENTER);
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

        listaProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    agregarProductoAPedido();
                }

            }
        });
        panelProductos.add(new JScrollPane(listaProductos), BorderLayout.CENTER);
        JButton botonVolverCategorias = new JButton("Volver a Categorías");
        botonVolverCategorias.addActionListener(e -> cardLayoutCentral.show(panelCentral, "Categorias"));
        panelProductos.add(botonVolverCategorias, BorderLayout.SOUTH);
        panelCentral.add(panelProductos, "Productos");
        add(panelCentral, BorderLayout.CENTER);

        // Panel para el área de pedido y los botones Eliminar y Guardar (vertical)
        JPanel panelPedidoControles = new JPanel();
        panelPedidoControles.setLayout(new BoxLayout(panelPedidoControles, BoxLayout.Y_AXIS));
        panelPedidoControles.setBackground(ColorPaleta.FONDO_SECUNDARIO);

        // Área de texto para mostrar el pedido
        areaPedido = new JTextArea(10, 20);
        areaPedido.setEditable(false);
        areaPedido.setBackground(ColorPaleta.TEXTAREA_FONDO);
        areaPedido.setForeground(ColorPaleta.TEXTAREA_TEXTO);
        areaPedido.setBorder(BorderFactory.createTitledBorder("Pedido Actual"));
        panelPedidoControles.add(new JScrollPane(areaPedido));

        // Panel para los botones Eliminar y Guardar (horizontal)
        JPanel panelBotonesPedido = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotonesPedido.setBackground(ColorPaleta.FONDO_SECUNDARIO);

        // Botón Eliminar Producto
        botonEliminarProducto = new Boton("Eliminar Producto", ColorPaleta.ACENTO, ColorPaleta.HOVER_ACENTO);
        botonEliminarProducto.addActionListener(e -> mostrarDialogoEliminarProducto());
        panelBotonesPedido.add(botonEliminarProducto);

        // Botón Guardar Pedido
        botonGuardarPedido = new Boton("Guardar Pedido", ColorPaleta.ENFASIS_ACCION, ColorPaleta.HOVER_ENFASIS_ACCION);
        botonGuardarPedido.addActionListener(e -> guardarPedido());
        panelBotonesPedido.add(botonGuardarPedido);

        panelPedidoControles.add(panelBotonesPedido);

        add(panelPedidoControles, BorderLayout.EAST);

        // Panel inferior para botones Volver y Cobrar
        JPanel panelBotonesPrincipal = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotonesPrincipal.setBackground(ColorPaleta.FONDO_SECUNDARIO);
        botonVolver = new Boton("Volver a las Mesas", ColorPaleta.VOLVER, ColorPaleta.HOVER_VOLVER);
        botonVolver.addActionListener(e -> tpvMain.mostrarVista("Mesas"));
        panelBotonesPrincipal.add(botonVolver);
        botonCobrar = new Boton("Cobrar", ColorPaleta.ENFASIS_ACCION, ColorPaleta.HOVER_ENFASIS_ACCION);
        botonCobrar.addActionListener(e -> {
            Pedido pedidoActual = obtenerPedidoActual(); // Solo obtener el pedido al cobrar
            if (pedidoActual != null) {
                tpvMain.getVistaCobro().setPedidoActual(pedidoActual);
                tpvMain.mostrarVista("Cobro");
            }
        });
        panelBotonesPrincipal.add(botonCobrar);
        
        // Botón para actualizar categorías
        Boton botonActualizarCategorias = new Boton("Actualizar Categorías", ColorPaleta.PRIMARIO, ColorPaleta.HOVER_PRIMARIO);
        botonActualizarCategorias.addActionListener(e -> cargarCategorias());
        panelBotonesPrincipal.add(botonActualizarCategorias);
        
        add(panelBotonesPrincipal, BorderLayout.SOUTH);

        // Cargar categorías al iniciar
        cargarCategorias();
    }

    private void cargarCategorias() {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        Map<Integer, String> nombresCategorias = categoriaDAO.obtenerCategorias();
        DefaultListModel<Categoria> modeloCategorias = (DefaultListModel<Categoria>) listaCategorias.getModel();
        modeloCategorias.clear(); // Limpiar la lista de categorías existente
        for (Map.Entry<Integer, String> entry : nombresCategorias.entrySet()) {
            modeloCategorias.addElement(new Categoria(entry.getKey(), entry.getValue())); // Agregar las categorías actualizadas
        }
        cardLayoutCentral.show(panelCentral, "Categorias"); // Mostrar la vista de categorías
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
            int codigo = productoSeleccionado.getCodigo();
            PedidoPlato nuevoPedidoPlato = new PedidoPlato(codigo, 1);
            productosDelPedido.add(nuevoPedidoPlato);
            actualizarAreaPedido();
            listaProductos.clearSelection();
        }
    }

    private void actualizarAreaPedido() {
        areaPedido.setText("");
        for (PedidoPlato pedidoPlato : productosDelPedido) {
            ProductoDAO productoDAO = new ProductoDAO();
            Producto producto = productoDAO.buscarProductoByID(pedidoPlato.getCodigoProducto());
            if (producto != null) {
                areaPedido.append(producto.getNombre() + " x " + pedidoPlato.getCantidad() + "\n");
            }
        }
    }

    public void setMesaSeleccionada(int numeroMesa) {
        this.mesaSeleccionada = new Mesa(numeroMesa);
        JLabel titulo = (JLabel) getComponent(0);
        titulo.setText("Menu - Mesa " + numeroMesa);
        areaPedido.setText("");
        productosDelPedido.clear();
    }

    private Pedido obtenerPedidoActual() {
        if (mesaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "No hay mesa seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        PedidoDAO pedidoDAO = new PedidoDAO();
        Pedido pedido = pedidoDAO.obtenerPedidoPorMesa(mesaSeleccionada.getNumero());
        if (pedido == null) {
            pedido = new Pedido(mesaSeleccionada.getNumero());
            pedidoDAO.insertarPedido(pedido);
        }
        return pedido;
    }

    private void guardarPedido() {
        if (mesaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "No hay mesa seleccionada", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (productosDelPedido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos en el pedido para guardar", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        PedidoDAO pedidoDAO = new PedidoDAO();
        Pedido pedido = new Pedido(mesaSeleccionada.getNumero());
        boolean resultado = pedidoDAO.insertarPedidoConPlatos(pedido, productosDelPedido);
        if (resultado) {
            JOptionPane.showMessageDialog(this, "Pedido guardado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el pedido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDialogoEliminarProducto() {
        if (productosDelPedido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos en el pedido para eliminar.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JDialog dialogoEliminar = new JDialog(tpvMain, "Eliminar Producto del Pedido", true);
        dialogoEliminar.setLayout(new BorderLayout(10, 10));
        dialogoEliminar.setSize(300, 200);
        dialogoEliminar.setLocationRelativeTo(this);

        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        for (PedidoPlato pedidoPlato : productosDelPedido) {
            ProductoDAO productoDAO = new ProductoDAO();
            Producto producto = productoDAO.buscarProductoByID(pedidoPlato.getCodigoProducto());
            if (producto != null) {
                modeloLista.addElement(producto.getNombre() + " x " + pedidoPlato.getCantidad());
            }
        }
        JList<String> listaProductosPedido = new JList<>(modeloLista);
        dialogoEliminar.add(new JScrollPane(listaProductosPedido), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Boton botonEliminar = new Boton("Eliminar", ColorPaleta.ACENTO, ColorPaleta.HOVER_ACENTO);
        botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indiceSeleccionado = listaProductosPedido.getSelectedIndex();
                if (indiceSeleccionado != -1) {
                    productosDelPedido.remove(indiceSeleccionado);
                    actualizarAreaPedido();
                    dialogoEliminar.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialogoEliminar, "Por favor, seleccione un producto para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        Boton botonCancelar = new Boton("Cancelar", ColorPaleta.VOLVER, ColorPaleta.HOVER_VOLVER);
        botonCancelar.addActionListener(e -> dialogoEliminar.dispose());

        panelBotones.add(botonEliminar);
        panelBotones.add(botonCancelar);
        dialogoEliminar.add(panelBotones, BorderLayout.SOUTH);

        dialogoEliminar.setVisible(true);
    }
}