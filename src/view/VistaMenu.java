package view;
import main.TPVMain;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import config.ColorPaleta;
import model.Pedido;
import model.Producto; 

public class VistaMenu extends JPanel{

    public JList<Producto> listaProductos;
    public JTextArea areaPedido;
    public JButton botonAñadir;
    public JButton botonCobrar;
    public JButton botonVolver;

    public VistaMenu(TPVMain mainFrame, Pedido pedidoActual) {
        setLayout(new BorderLayout());
        JLabel titulo = new JLabel("Menu", SwingConstants.CENTER);
        setBackground(ColorPaleta.FONDO_SECUNDARIO); // Color de fondo gris oscuro
        titulo.setForeground(ColorPaleta.TEXTO_PRINCIPAL_CLARO); // Color del texto del título
        add(titulo, BorderLayout.NORTH); // Añadir título en la parte superior

        DefaultListModel<Producto> modeloLista = new DefaultListModel<>(); // Crear un modelo de lista para los productos
        listaProductos = new JList<>(modeloLista); // Crear la lista de productos

        add(new JScrollPane(listaProductos), BorderLayout.WEST); // Añadir lista en el lado izquierdo
        
        //Nombre descripcion Precio disponible consultas sql para contructor


        Producto producto = new Producto();

        int numeroMesas = config.getNumero_mesas();  // Obtener el número de mesas desde la configuración
        for (int i = 1; i <= numeroMesas; i++) { // Crear botones según el número de mesas configurado

            Boton botonMenu = new Boton(producto.getNombre()); // Crear botón para cada mesa
            botonMenu.setActionCommand(Producto.nombre); // Establecer el comando de acción del botón
            botonMenu.addActionListener(tpvMain); // Usar tpvMain como ActionListener
            botonMenu.addActionListener(this::mostrarMensaje); // Mostrar mensaje al hacer clic
            botonMenu.add(botonMenu, BorderLayout.CENTER); // Añadir botón al panel de mesas
        }
        Boton botonMenu = new Boton("Ir al Mesas"); // Crear botón para ir al menú
        botonMenu.setActionCommand("Ir al Mesas");
        botonMenu.addActionListener(tpvMain); // Usar tpvMain como ActionListener
        add(botonMenu, BorderLayout.SOUTH); // Añadir botón de menú en la parte inferior

        Boton botonMenu = new Boton("Cobrar"); 
        botonMenu.setActionCommand("Cobrar");
        botonMenu.addActionListener(tpvMain); 
        add(botonMenu, BorderLayout.SOUTH); 


        public void mostrarMensaje(ActionEvent e) {
            String command = e.getActionCommand();
            JOptionPane.showMessageDialog(this, "Has seleccionado " + command);
        }


        

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonAñadir) {

        }
    }

}
