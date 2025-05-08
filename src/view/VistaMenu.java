package view;

import config.ColorPaleta;
import dao.CategoriaDAO;
import dao.PedidoDAO;
import dao.ProductoDAO;
import java.awt.*;
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
    private CardLayout cardLayoutCentral;
    private JPanel panelCentral;

    public VistaMenu(TPVMain tpvMain) {
        this.tpvMain = tpvMain;

        setLayout(new BorderLayout());
        setBackground(ColorPaleta.FONDO_SECUNDARIO);

        // Título
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
        listaProductos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                agregarProductoAPedido();
            }
        });
        panelProductos.add(new JScrollPane(listaProductos), BorderLayout.CENTER);

        // Botón para volver a las categorías
        JButton botonVolverCategorias = new JButton("Volver a Categorías");
        botonVolverCategorias.addActionListener(e -> cardLayoutCentral.show(panelCentral, "Categorias"));
        panelProductos.add(botonVolverCategorias, BorderLayout.SOUTH);

        panelCentral.add(panelProductos, "Productos");

        add(panelCentral, BorderLayout.CENTER);

        // Área de texto para mostrar el pedido (fija)
        areaPedido = new JTextArea();
        areaPedido.setEditable(false);
        areaPedido.setBackground(ColorPaleta.TEXTAREA_FONDO);
        areaPedido.setForeground(ColorPaleta.TEXTAREA_TEXTO);
        areaPedido.setBorder(BorderFactory.createTitledBorder("Pedido Actual"));
        areaPedido.setText("No hay productos en el pedido."); // Mensaje inicial
        add(new JScrollPane(areaPedido), BorderLayout.EAST); // Siempre visible en el lado derecho

        // Panel inferior para botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(ColorPaleta.FONDO_SECUNDARIO);

        Boton botonVolver = new Boton("Volver a las Mesas", ColorPaleta.VOLVER, ColorPaleta.HOVER_VOLVER);
        botonVolver.addActionListener(e -> tpvMain.mostrarVista("Mesas"));
        panelBotones.add(botonVolver);

        Boton botonCobrar = new Boton("Cobrar", ColorPaleta.ENFASIS_ACCION, ColorPaleta.HOVER_ENFASIS_ACCION);
        botonCobrar.addActionListener(e -> {
            guardarPedido(); // Guardar el pedido antes de cambiar de vista
            Pedido pedidoActual = obtenerPedidoActual();
            tpvMain.getVistaCobro().setPedidoActual(pedidoActual); // Pasar el pedido actual a VistaCobro
            tpvMain.mostrarVista("Cobro");
        });
        panelBotones.add(botonCobrar);

        add(panelBotones, BorderLayout.SOUTH);

        // Cargar categorías al iniciar
        cargarCategorias();
    }

    private void cargarCategorias() {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        Map<Integer, String> nombresCategorias = categoriaDAO.obtenerCategorias();
        DefaultListModel<Categoria> modeloCategorias = (DefaultListModel<Categoria>) listaCategorias.getModel();
        modeloCategorias.clear();
        for (Map.Entry<Integer, String> entry : nombresCategorias.entrySet()) {
            modeloCategorias.addElement(new Categoria(entry.getKey(), entry.getValue()));
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

    private void agregarProductoAPedidoTabla() {
        
        Producto productoSeleccionado = listaProductos.getSelectedValue();
        ArrayList<PedidoPlato> pedidoPlato = new ArrayList<>();
        PedidoDAO pedidoDAO = new PedidoDAO();
        int numeroMesa = mesaSeleccionada.getNumero(); 

        Pedido pedidoMesa = pedidoDAO.obtenerPedidoPorMesa(numeroMesa);
        Pedido pedido;
        if (pedidoMesa != null) {
            pedido = pedidoMesa;
        } else {
            pedido = new Pedido(numeroMesa);
            pedidoDAO.insertarPedido(pedido);
        }
        if (productoSeleccionado != null) {
            areaPedido.append(productoSeleccionado.getNombre() + "\n");
            int codigo = productoSeleccionado.getCodigo();
            PedidoPlato nuevoPedido = new PedidoPlato(codigo);
            nuevoPedido.setPedidoId(pedido.getId());
            pedidoPlato.add(nuevoPedido);
        }
        pedidoDAO.insertarPlatosEnPedido(pedidoPlato);
    }

    public void setMesaSeleccionada(int numeroMesa) {
        // Actualizar la mesa seleccionada
        this.mesaSeleccionada = new Mesa(numeroMesa);

        // Actualizar el título de la vista
        JLabel titulo = (JLabel) getComponent(0);
        titulo.setText("Menu - Mesa " + numeroMesa);

        // Reiniciar la lista de productos del pedido
        productosDelPedido.clear();

        // Cargar el pedido de la mesa seleccionada
        PedidoDAO pedidoDAO = new PedidoDAO();
        Pedido pedido = pedidoDAO.obtenerPedidoPorMesa(numeroMesa);
        if (pedido != null) {
            Map<Producto, Integer> productos = PedidoDAO.listaPlatosPedidoFactura(pedido);
            for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
                productosDelPedido.add(new PedidoPlato(entry.getKey().getCodigo(), entry.getValue()));
            }
        }

        // Actualizar el área de pedido con los productos cargados
        actualizarAreaPedido();

        // Mostrar la vista de categorías
        cardLayoutCentral.show(panelCentral, "Categorias");
    }

    private Pedido obtenerPedidoActual() {
        if(mesaSeleccionada == null) {
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

    private ArrayList<PedidoPlato> productosDelPedido = new ArrayList<>(); // Lista para almacenar los productos del pedido

    private void agregarProductoAPedido() {
        Producto productoSeleccionado = listaProductos.getSelectedValue();
        if (productoSeleccionado != null) {
            // Agregar el producto a la lista de productos del pedido
            int codigo = productoSeleccionado.getCodigo();
            PedidoPlato nuevoPedidoPlato = new PedidoPlato(codigo, 1);
            productosDelPedido.add(nuevoPedidoPlato);

            // Actualizar el área de pedido
            actualizarAreaPedido();

            // Deseleccionar el producto para permitir seleccionarlo nuevamente
            listaProductos.clearSelection();
        }
    }

    private void actualizarAreaPedido() {
        areaPedido.setText(""); // Limpiar el área de texto
        if (productosDelPedido.isEmpty()) {
            areaPedido.setText("No hay productos en el pedido."); // Mensaje si no hay productos
        } else {
            ProductoDAO productoDAO = new ProductoDAO();
            for (PedidoPlato pedidoPlato : productosDelPedido) {
                Producto producto = productoDAO.buscarProductoByID(pedidoPlato.getCodigoProducto());
                if (producto != null) {
                    areaPedido.append(producto.getNombre() + " x " + pedidoPlato.getCantidad() + "\n");
                }
            }
        }
    }

    private void guardarPedido() {
        if (mesaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "No hay mesa seleccionada", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (productosDelPedido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos en el pedido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PedidoDAO pedidoDAO = new PedidoDAO();
        Pedido pedido = obtenerPedidoActual(); // Obtener el pedido actual
        boolean resultado = pedidoDAO.insertarPedidoConPlatos(pedido, productosDelPedido);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Pedido guardado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el pedido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

