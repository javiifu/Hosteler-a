package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.UserData;

public class UserDataDAO {
    public static boolean validarCredenciales(String user, String password){
        String sql = "SELECT contraseña FROM Usuarios WHERE BINARY nombre_usuario = ?";
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

    public static boolean existenUsuarios(){
        String sql = "SELECT COUNT(*) FROM Usuarios";
        Connection conexion = ConexionBD.conectar();
        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar usuarios: " + e.getMessage());
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

    public static void newUsuario(String user, String password, boolean admin){
        String sql = "INSERT INTO Usuarios (nombre_usuario, contraseña, administrador) VALUES (?, ?, ?)";
        Connection conexion = ConexionBD.conectar();
        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, user);
            statement.setString(2, password);
            statement.setBoolean(3, admin);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
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
