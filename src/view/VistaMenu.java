package view;
import main.TPVMain;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import config.ColorPaleta;
import model.Categoria; // Importa la clase Categoria
import model.Pedido;
import model.Producto;

public class VistaMenu extends JPanel implements ActionListener {

    public JList<Producto> listaProductos;
    public JTextArea areaPedido;
    public JButton botonAñadir;
    public JButton botonCobrar;
    public JButton botonVolver;
    public JPanel panelBotonesCategorias;
    public TPVMain tpvMain;
    public Pedido pedidoActual;
    public DefaultListModel<Producto> modeloLista;
    public List<Producto> listaCompletaProductos;
    public List<Categoria> listaCategorias; // Lista para almacenar las categorías

    // Simulamos un método para obtener las categorías (reemplazar con tu lógica real)
    public List<Categoria> obtenerCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria("Cafés"));
        categorias.add(new Categoria("Bocadillos"));
        categorias.add(new Categoria("Postres"));
        categorias.add(new Categoria("Bebidas"));
        return categorias;
    }

    public VistaMenu(TPVMain mainFrame, Pedido pedidoActual, List<Producto> productos) {
        this.tpvMain = mainFrame;
        this.pedidoActual = pedidoActual;
        this.listaCompletaProductos = productos;
        this.listaCategorias = obtenerCategorias(); // Obtener la lista de categorías

        setLayout(new BorderLayout());
        setBackground(ColorPaleta.FONDO_SECUNDARIO);

        JLabel titulo = new JLabel("Menú", SwingConstants.CENTER);
        titulo.setForeground(ColorPaleta.TEXTO_PRINCIPAL_CLARO);
        add(titulo, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();
        listaProductos = new JList<>(modeloLista);
        JScrollPane scrollListaProductos = new JScrollPane(listaProductos);
        add(scrollListaProductos, BorderLayout.CENTER);

        // aa
        panelBotonesCategorias = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotonesCategorias.setBackground(ColorPaleta.FONDO_SECUNDARIO);
        JScrollPane scrollPanelBotones = new JScrollPane(panelBotonesCategorias);
        scrollPanelBotones.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPanelBotones, BorderLayout.WEST);

        JButton botonTodos = new JButton("Todos");
        botonTodos.setActionCommand("Todos");
        botonTodos.addActionListener(this);
        panelBotonesCategorias.add(botonTodos);

        for (Categoria categoria : listaCategorias) {
            JButton botonCategoria = new JButton(categoria.getNombre());
            botonCategoria.setActionCommand(categoria.getNombre());
            botonCategoria.addActionListener(this);
            panelBotonesCategorias.add(botonCategoria);
        }
        // aa

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(ColorPaleta.FONDO_SECUNDARIO);

        Boton botonMenu = new Boton("Ir a Mesas");
        botonMenu.setActionCommand("Ir a Mesas");
        botonMenu.addActionListener(tpvMain);
        panelInferior.add(botonMenu);

        Boton botonCobrar = new Boton("Cobrar");
        botonCobrar.setActionCommand("Cobrar");
        botonCobrar.addActionListener(tpvMain);
        panelInferior.add(botonCobrar);

        add(panelInferior, BorderLayout.SOUTH);

        mostrarProductosPorCategoria("Todos"); // Mostrar todos los productos al inicio
    }

    private void mostrarProductosPorCategoria(String nombreCategoria) {
        modeloLista.clear();
        for (Producto producto : listaCompletaProductos) {
            CategoriaVIEW categoriaView = new CategoriaVIEW();
            if (nombreCategoria.equals("Todos") || categoriaView.getCategoria(nombreCategoria).equals(nombreCategoria)) {
                modeloLista.addElement(producto);
            }
        }
    }

    public void mostrarMensaje(ActionEvent e) {
        String command = e.getActionCommand();
        JOptionPane.showMessageDialog(this, "Has seleccionado " + command);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Todos")) {
            mostrarProductosPorCategoria("Todos");
        } else if (e.getSource() instanceof JButton) {
            mostrarProductosPorCategoria(command);
        }
    }
}