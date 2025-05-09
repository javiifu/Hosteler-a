package main;
import config.ColorPaleta;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.UserData;
import view.Boton;
import view.VistaCobro;
import view.VistaConfiguracion;
import view.VistaMenu;
import view.VistaMesas;

public class TPVMain extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private VistaMesas vistaMesas;
    private VistaMenu vistaMenu;
    private VistaConfiguracion vistaConfiguracion;
    private VistaCobro vistaCobro;
    private App app;
    private UserData userData;

    // Constructor principal que recibe UserData y App
    public TPVMain(UserData userData, App app) {
        this.userData = userData;
        this.app = app;
        inicializar();
    }

    // Nuevo constructor que solo recibe UserData
    public TPVMain(UserData userData) {
        this(userData, new App()); // Llama al constructor principal y crea una nueva instancia de App
    }

    private void inicializar() {
        setTitle("TPV - Restaurante/Bar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setBackground(ColorPaleta.FONDO_PRINCIPAL); // Color de fondo gris oscuro

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Aplicar margen
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        vistaMesas = new VistaMesas(this);
        vistaMenu = new VistaMenu(this);
        vistaConfiguracion = new VistaConfiguracion(this);
        vistaCobro = new VistaCobro(this, null, app); // Inicializa con un pedido nulo
        vistaCobro.setApp(app); // Inyectar la instancia de App en VistaCobro

        // Configurar panel inicial con botones
        JPanel panelInicial = new JPanel(new GridLayout(2, 1, 10, 10));
        panelInicial.setBackground(ColorPaleta.FONDO_PRINCIPAL);

        Boton botonMesas = new Boton("Gestionar Mesas", ColorPaleta.SECUNDARIO, ColorPaleta.HOVER_SECUNDARIO);
        botonMesas.setActionCommand("Mesas");
        botonMesas.addActionListener(this);

        

        Boton botonConfiguracion = new Boton("Configuracion", ColorPaleta.SECUNDARIO, ColorPaleta.HOVER_SECUNDARIO);
        botonConfiguracion.setActionCommand("Configuracion");
        botonConfiguracion.addActionListener(this);

        panelInicial.add(botonMesas);
        panelInicial.add(botonConfiguracion);

        mainPanel.add(panelInicial, "Inicio");
        mainPanel.add(vistaMesas, "Mesas");
        mainPanel.add(vistaMenu, "Menu");
        mainPanel.add(vistaConfiguracion, "Configuracion");
        mainPanel.add(vistaCobro, "Cobro");

        add(mainPanel);

        cardLayout.show(mainPanel, "Inicio"); // Muestra la vista inicial
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Menu":
                cardLayout.show(mainPanel, "Menu");
                break;
            case "Mesas":
                cardLayout.show(mainPanel, "Mesas");
                break;
            case "Configuracion":
                if (userData.isAdmin()) {
                    cardLayout.show(mainPanel, "Configuracion");
                } else {
                    JOptionPane.showMessageDialog(this, "No tienes permisos para acceder a esta sección.", "Acceso Denegado", JOptionPane.WARNING_MESSAGE);
                }
                break;
            case "Inicio":
                cardLayout.show(mainPanel, "Inicio");
                break;
            default:
                break;
        }
    }

    public void mostrarVista(String vista) {
        cardLayout.show(mainPanel, vista);
    }

    public VistaMesas getVistaMesas() {
        return vistaMesas;
    }
    public VistaMenu getVistaMenu() {
        return vistaMenu;
    }
    public VistaConfiguracion getVistaConfiguracion() {
        return vistaConfiguracion;
    }
    public VistaCobro getVistaCobro() {
        return vistaCobro;
    }
}
