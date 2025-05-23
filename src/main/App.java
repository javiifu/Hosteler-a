package main;
import config.*;
import dao.CategoriaDAO;
import dao.ConexionBD;
import dao.HistorialSesionesDAO;
import dao.PedidoDAO;
import dao.UserDataDAO;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import model.LogSesion;
import model.Pedido;
import model.Producto;
import model.UserData;
public class App {
    static JFrame frame = new JFrame("Hosteler-a");
    static UserData userData = null;
    static Config config = ConfigLoader.cargarConfig("config.json");
    public static void main(String[] args){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        if(!UserDataDAO.existenUsuarios()){
            noUserProtocol(frame);
        }
        userData = iniciarSesion(frame);

         //PlatoVIEW platoVIEW = new PlatoVIEW();
         //Se intenta conectar a la base de datos
         Connection conexion = ConexionBD.conectar();
         if (conexion != null) {
            System.out.println("Conexion establecida correctamente.");
        } else{
            System.out.println("No se pudo establecer la conexión");
        }
        
        //platoVIEW.menu();

        frame.dispose();
    }

    public static void noUserProtocol(JFrame frame){
        JDialog dialog = new JDialog (frame, "Configuracion Inicial de usuario", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new FlowLayout());
        dialog.setUndecorated(true);
        dialog.setLocationRelativeTo(frame);
        JLabel label = new JLabel("<html>No existen usuarios.<br>Se creara un usuario admin.<br>Introduce sus credenciales</html>");
        JTextField txt_user = new JTextField(16);
        JPasswordField txt_pass = new JPasswordField(16);
        JButton btn_create = new JButton("Crear Usuario");
        dialog.add(label);
        dialog.add(txt_user);
        dialog.add(txt_pass);
        dialog.add(btn_create);
        btn_create.addActionListener(e -> {
            String user = txt_user.getText();
            String password = txt_pass.getText(); //Que pasa aquí ? 
            if (user.isEmpty() || password.isEmpty()){
                JOptionPane.showMessageDialog(dialog, "Introduce un usuario y una contraseña");
            } else {
                UserDataDAO.newUsuario(user, password, true);
                JOptionPane.showMessageDialog(dialog, "Usuario creado correctamente");
                dialog.dispose();
            }
        });
        dialog.setVisible(true);
    }

    public static UserData iniciarSesion(JFrame frame) {
    JDialog dialog = new JDialog(frame, "Inicio de Sesión", true);
    dialog.setSize(350, 200);
    dialog.setLayout(new BorderLayout());
    dialog.setUndecorated(false); 
    dialog.setLocationRelativeTo(frame);

    // titulo
    JLabel lblTitulo = new JLabel("Bienvenido a la gestión del restaurante", SwingConstants.CENTER);
    lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); //margen superior e inferior

    //login
    JPanel panelCentral = new JPanel();
    panelCentral.setLayout(new GridLayout(3, 2, 5, 10));
    panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel lblUser = new JLabel("Usuario:");
    JTextField txt_user = new JTextField(16);
    JLabel lblPass = new JLabel("Contraseña:");
    JPasswordField txt_pass = new JPasswordField(16);
    JButton btn_login = new JButton("Iniciar Sesión");
    JLabel espacio = new JLabel(""); // espaciar el botón

    //descripcion y caja txt
    panelCentral.add(lblUser);
    panelCentral.add(txt_user);
    panelCentral.add(lblPass);
    panelCentral.add(txt_pass);
    panelCentral.add(espacio);
    panelCentral.add(btn_login);

    //añadir titulo y login
    dialog.add(lblTitulo, BorderLayout.NORTH);
    dialog.add(panelCentral, BorderLayout.CENTER);

    final UserData[] currentUser = {null};

    btn_login.addActionListener(e -> {
        String user = txt_user.getText();
        String password = new String(txt_pass.getPassword()); // Obtener contraseña

        if (UserDataDAO.validarCredenciales(user, password)) {
            currentUser[0] = UserDataDAO.obtenerUsuario(user);
            JOptionPane.showMessageDialog(dialog, "¡Bienvenido, " + user + "!", "Inicio de Sesión", JOptionPane.INFORMATION_MESSAGE);
            HistorialSesionesDAO.newLog(new LogSesion(
                    "INICIO_SESION",
                    "Inicio de sesión del usuario " + user,
                    Date.valueOf(java.time.LocalDate.now()),
                    Time.valueOf(java.time.LocalTime.now()),
                    currentUser[0].getId()
            ));
            dialog.dispose();

            SwingUtilities.invokeLater(() -> {
                TPVMain tpvMain = new TPVMain(currentUser[0]);
                tpvMain.setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(dialog, "Usuario o contraseña incorrectos.", "Error de Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
        }
    });

    dialog.setVisible(true);

    return currentUser[0];
}

    public void generarFactura(Pedido pedido) {
        StringBuilder factura = new StringBuilder();
        factura.append("<!DOCTYPE html><html lang=\"es\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>Factura</title><style>body {font-family: Arial, sans-serif;margin: 20px;padding: 20px;background-color: #f4f4f4;}.factura {background-color: #fff;padding: 20px;             border-radius: 10px;box-shadow: 0 0 10px rgba(0,0,0,0.1);max-width: 400px;             margin: auto;             padding-bottom: 20px;}.factura h2, .factura p {text-align: center;}.tabla {width: 100%;margin-top: 10px;border-collapse: collapse;}.tabla th, .tabla td {padding: 10px;text-align: left;}.tabla th {background-color: #ddd;}.totales {margin-top: 20px;padding: 10px;background-color: #ddd;border-radius: 5px;}.total {font-weight: bold;text-align: right;}</style></head><body><div class=\"factura\">");
        factura.append("<h2>"+config.getNombre_restaurante()+"</h2>");
        factura.append("<p>Fecha: "+pedido.getFechaPedido()+"</p>");
        factura.append("<p>Nº del pedido: "+pedido.getId()+"</p><table class=\"tabla\" border=\"1\"><tr><th>Descripción</th><th>Cantidad</th><th>Precio</th></tr>");
        Map<Producto, Integer> lista_productos = PedidoDAO.listaPlatosPedidoFactura(pedido);
        double total = 0;
        for(Map.Entry<Producto, Integer> entry : lista_productos.entrySet()){
            factura.append("<tr><td>"+entry.getKey().getNombre()+"</td><td>"+entry.getValue()+"</td><td>"+entry.getKey().getPrecio()+"</td></tr>");
            total += entry.getKey().getPrecio() * entry.getValue();
        }
        factura.append("</table><div class=\"totales\"><table class=\"tabla\"><tr><td class=\"total\">Total sin IVA</td><td class=\"total\">"+total+"</td></tr><tr><td class=\"total\">IVA (10%)</td><td class=\"total\">"+String.format("%.2f",total*0.10)+"</td></tr><tr><td class=\"total\">Total con IVA</td><td class=\"total\">"+String.format("%.2f",total + total*0.10)+"</td></tr></table></div></div></body></html>");

        String html = factura.toString();
        String nombreArchivo = "factura_" + pedido.getId() + ".html";
        
        try (java.io.FileWriter fileWriter = new java.io.FileWriter(nombreArchivo)) {
            fileWriter.write(html);
            System.out.println("Factura generada: " + nombreArchivo);
        } catch (java.io.IOException e) {
            System.err.println("Error al generar la factura: " + e.getMessage());
        }

    }

    public static double totalVendidoPorTipoPagoSinIVA(String tipo_pago){
        double total = 0;
        Time horaApertura = config.getHorarios().get(0).getInicio();
        ArrayList<Pedido> lista_pedidos = PedidoDAO.pedidosPorDia(horaApertura);
        for(Pedido pedido : lista_pedidos){
            if(pedido.isPagado()){
                if(pedido.getTipo_pago().equals(tipo_pago)){
                    Map<Producto, Integer> lista_productos = PedidoDAO.listaPlatosPedidoFactura(pedido);
                    for(Map.Entry<Producto, Integer> entry : lista_productos.entrySet()){
                        total += entry.getKey().getPrecio() * entry.getValue();
                    }
                }
            }
        }
        return (total);
    }

    public static double totalVendidoSinIVA(){
        double total = 0;
        total += totalVendidoPorTipoPagoSinIVA("TARJETA");
        total += totalVendidoPorTipoPagoSinIVA("EFECTIVO");
        return (total);
    }

    public static void generarResumenDia(){
        Date fecha = new Date(System.currentTimeMillis());
        Time hora = new Time(System.currentTimeMillis());
        StringBuilder resumen = new StringBuilder();
        resumen.append("<!DOCTYPE html><html lang=\"es\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>Resumen</title><style>body {font-family: Arial, sans-serif;margin: 20px;padding: 20px;background-color: #f4f4f4;}.factura {background-color: #fff;padding: 20px;             border-radius: 10px;box-shadow: 0 0 10px rgba(0,0,0,0.1);max-width: 400px;             margin: auto;             padding-bottom: 20px;}.factura h2, .factura p {text-align: center;}.tabla {width: 100%;margin-top: 10px;border-collapse: collapse;}.tabla th, .tabla td {padding: 10px;text-align: left;}.tabla th {background-color: #ddd;}.totales {margin-top: 20px;padding: 10px;background-color: #ddd;border-radius: 5px;}.total {font-weight: bold;text-align: right;}</style></head><body><div class=\"factura\">");
        resumen.append("<h2>"+config.getNombre_restaurante()+"</h2>");
        resumen.append("<p>Fecha y hora del resumen: "+fecha+" || "+hora+"</p>");
        ArrayList<Pedido> lista_pedidos = PedidoDAO.pedidosPorDia(config.getHorarios().get(0).getInicio());
        List<Map<Producto, Integer>> lista_total_productos = new ArrayList<>();
        for(Pedido pedido : lista_pedidos){
            Map<Producto, Integer> lista_productos = PedidoDAO.listaPlatosPedidoFactura(pedido);
            lista_total_productos.add(lista_productos);
        }
        Map<Producto, Integer> lista_total_productos_agrupados = lista_total_productos.stream()
        .flatMap(map -> map.entrySet().stream())
        .collect(Collectors.groupingBy(
            Map.Entry::getKey, 
            Collectors.summingInt(Map.Entry::getValue)
        ));
            double total_categoria = 0;
        Map<Integer, String> lista_categorias = CategoriaDAO.obtenerCategorias();
        for(Map.Entry<Integer, String> entry_categoria : lista_categorias.entrySet()){
            resumen.append("<h3>"+entry_categoria.getValue()+"</h3>");
            resumen.append("<table class=\"tabla\" border=\"1\"><tr><th>Descripción</th><th>Cantidad</th><th>Precio</th></tr>");
            total_categoria = 0;
            for(Map.Entry<Producto, Integer> entry : lista_total_productos_agrupados.entrySet()){
                if(entry.getKey().getCategoriaCodigo() == entry_categoria.getKey()){
                    resumen.append("<tr><td>"+entry.getKey().getNombre()+"</td><td>"+entry.getValue()+"</td><td>"+entry.getKey().getPrecio()+"</td></tr>");
                    total_categoria += entry.getKey().getPrecio() * entry.getValue();
                }
            }
            resumen.append("<tr><td class=\"total\">Total "+String.format("%.2f", total_categoria)+"</td><td class=\"total\">IVA "+String.format("%.2f", (total_categoria*0.10))+"</td><td class=\"total\">Total con IVA "+String.format("%.2f", (total_categoria + total_categoria*0.10))+"</tr></table>");
        }
        double tarjetaSinIVA = totalVendidoPorTipoPagoSinIVA("TARJETA");
        double efectivoSinIVA = totalVendidoPorTipoPagoSinIVA("EFECTIVO");
        double total = totalVendidoSinIVA();
        resumen.append("<div class=\"totales\"><table class=\"tabla\"><tr><td class=\"total\">Total Tarjeta sin IVA</td><td class=\"total\">"+tarjetaSinIVA+"</td></tr><tr><td class=\"total\">IVA Tarjeta(10%)</td><td class=\"total\">"+String.format("%.2f", (tarjetaSinIVA*0.10))+"</td></tr><tr><td class=\"total\">Tarjeta con IVA</td><td class=\"total\">"+String.format("%.2f",(tarjetaSinIVA + tarjetaSinIVA*0.10))+"</td></tr>");

        resumen.append("<tr><td class=\"total\">Total Efectivo sin IVA</td><td class=\"total\">"+efectivoSinIVA+"</td></tr><tr><td class=\"total\">IVA Efectivo(10%)</td><td class=\"total\">"+String.format("%.2f",efectivoSinIVA*0.10)+"</td></tr><tr><td class=\"total\">Efectivo con IVA</td><td class=\"total\">"+String.format("%.2f",(efectivoSinIVA + efectivoSinIVA*0.10))+"</td></tr>");

        resumen.append("<tr><td class=\"total\">Total sin IVA</td><td class=\"total\">"+total+"</td></tr><tr><td class=\"total\">IVA(10%)</td><td class=\"total\">"+String.format("%.2f",total*0.10)+"</td></tr><tr><td class=\"total\" con IVA</td></tr><td class=\"total\">Total con IVA</td><td class=\"total\">"+String.format("%.2f",(total + total*0.10))+"</td></tr></table></div></div></body></html>");

        String html = resumen.toString();
        String nombreArchivo = "resumen_" + fecha.toString()+"_"+hora.getHours()+"-"+hora.getMinutes()+"-"+hora.getSeconds() +".html";
        
        try (java.io.FileWriter fileWriter = new java.io.FileWriter(nombreArchivo)) {
            fileWriter.write(html);
            System.out.println("Resumen generado: " + nombreArchivo);
        } catch (java.io.IOException e) {
            System.err.println("Error al generar el resumen: " + e.getMessage());
        }
    }

}


