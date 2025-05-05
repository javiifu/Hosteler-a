package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Mesa;

public class MesaDAO {
    public static int totalMesas() {
        String sql = "SELECT COUNT(*) FROM Mesa";
        Connection conexion = ConexionBD.conectar();
        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al contar mesas: " + e.getMessage());
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return 0;
    }

    public static boolean comprobarOcupada(int num_mesa){
        String sql = "SELECT estado FROM Mesa WHERE numero = ?";
        Connection conexion = ConexionBD.conectar();
        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, num_mesa);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("estado");
            }
        } catch (SQLException e) {
            System.out.println("Error al comprobar estado de la mesa: " + e.getMessage());
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

    public static void cambiarEstadoMesa(int num_mesa) {
        String sql = "UPDATE Mesa SET estado = NOT estado WHERE numero = ?";
        Connection conexion = ConexionBD.conectar();
        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, num_mesa);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al cambiar el estado de la mesa: " + e.getMessage());
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

    public static void newMesa(Mesa mesa){
        String sql = "INSERT INTO Mesa (numero, estado) VALUES (?, ?)";
        Connection conexion = ConexionBD.conectar();
        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, mesa.getCodigo());
            statement.setBoolean(2, mesa.getEstado());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar la mesa: " + e.getMessage());
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

    public static void deleteMesa(int num_mesa) {
        String sql = "DELETE FROM Mesa WHERE numero = ?";
        Connection conexion = ConexionBD.conectar();
        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, num_mesa);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar la mesa: " + e.getMessage());
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
