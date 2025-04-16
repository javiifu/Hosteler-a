import dao.ConexionBD;
import java.sql.Connection;

public class App {
    public static void main(String[] args){
        //Se intenta conectar a la base de datos
        Connection conexion = ConexionBD.conectar();
        if (conexion != null) {
            System.out.println("Conexion establecida correctamente.");
        } else{
            System.out.println("No se pudo establecer la conexión");
        }
    }
}
