package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.UserData;

public class UserDataDAO {
    public static boolean validarCredenciales(String user, String password){
        String sql = "SELECT contraseña FROM Usuarios WHERE nombre_usuario = ?";
        Connection conexion = ConexionBD.conectar();
        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, user);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String passwordBD = resultSet.getString("contraseña");
                return password.equals(passwordBD);
            }
        } catch (SQLException e) {
            System.out.println("Error al validar credenciales: " + e.getMessage());
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return false;
    }

    public static UserData obtenerUsuario(String user) {
        String sql = "SELECT * FROM Usuarios WHERE nombre_usuario = ?";
        Connection conexion = ConexionBD.conectar();
        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, user);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new UserData(resultSet.getInt("id"), resultSet.getBoolean("administrador"), resultSet.getString("nombre_usuario"), resultSet.getString("contraseña"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener usuario: " + e.getMessage());
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return null;
    }
}
