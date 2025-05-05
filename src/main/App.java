package main;
import config.*;
import dao.ConexionBD;
import dao.HistorialSesionesDAO;
import dao.UserDataDAO;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.LogSesion;
import model.UserData;
public class App {
    static JFrame frame = new JFrame("Hosteler-a");
    static UserData userData = null;
    public static void main(String[] args){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        Config config = ConfigLoader.cargarConfig("config.json");

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
            String password = txt_pass.getText();
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
    dialog.setSize(300, 200);
    dialog.setLayout(new FlowLayout());
    dialog.setUndecorated(true);
    dialog.setLocationRelativeTo(frame);


    JTextField txt_user = new JTextField(16);
    JPasswordField txt_pass = new JPasswordField(16);

    JButton btn_login = new JButton("Iniciar Sesión");
    

    dialog.add(txt_user);
    dialog.add(txt_pass);
    dialog.add(btn_login);
    

    final UserData[] currentUser = {null};
    
        btn_login.addActionListener(e -> {
            String user = txt_user.getText();
            String password = txt_pass.getText();

            if (UserDataDAO.validarCredenciales(user, password)) {
                currentUser[0] = UserDataDAO.obtenerUsuario(user);
                JOptionPane.showMessageDialog(dialog, "Bienvenido " + user);
                HistorialSesionesDAO.newLog(new LogSesion("INICIO_SESION", "Inicio de sesion del usuario " + user, Date.valueOf(java.time.LocalDate.now()), Time.valueOf(java.time.LocalTime.now()), currentUser[0].getId()));
            
                dialog.dispose();
                // Iniciar la aplicacion princial (TPVMain)
                SwingUtilities.invokeLater(() -> {
                    TPVMain tpvMain = new TPVMain();
                    tpvMain.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(dialog, "Usuario o contraseña incorrectos.");
            }
        });

        dialog.setVisible(true);
        
        return currentUser[0];
    }

}
