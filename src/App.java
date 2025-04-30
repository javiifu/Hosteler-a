import config.*;
import dao.ConexionBD;
import dao.UserDataDAO;
import java.awt.FlowLayout;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.UserData;
public class App {
    static JFrame frame = new JFrame("Hosteler-a");
    static UserData userData = null;
    public static void main(String[] args){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        Config config = ConfigLoader.cargarConfig("config.json");
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
            
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Usuario o contraseña incorrectos.");
            }
        });

        dialog.setVisible(true);
        
        return currentUser[0];
    }

}
