package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.LogSesion;
public class HistorialSesionesDAO {
    public static void newLog(LogSesion log){
        Connection conexion = ConexionBD.conectar();
        String sql = "INSERT INTO Historial_sesiones (fecha, hora, id_usuario, tipo, concepto) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setDate(1, log.getFecha());
            ps.setTime(2, log.getHora());
            ps.setInt(3, log.getId_usuario());
            ps.setString(4, log.getTipo());
            ps.setString(5, log.getConcepto());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar el log: " + e.getMessage());
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
