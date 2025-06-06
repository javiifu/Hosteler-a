package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Mesa;

public class MesaDAO {
    public int totalMesas() {
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

    public boolean comprobarOcupada(int num_mesa){
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

    public void cambiarEstadoMesa(int num_mesa) {
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

    // Metodo para desactivar la mesa
    public void desactivarMesa(int num_mesa) {
        String sql = "UPDATE Mesa SET activo = NOT activo WHERE numero = ?";
        Connection conexion = ConexionBD.conectar();
        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, num_mesa);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al desactivar la mesa: " + e.getMessage());
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

    public void newMesa(Mesa mesa){
        String sql = "INSERT INTO Mesa (numero, estado) VALUES (?, ?)";
        Connection conexion = ConexionBD.conectar();
        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, mesa.getNumero());
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

    public ArrayList<Mesa> getMesas() {
        ArrayList<Mesa> mesas = new ArrayList<>();
        String sql = "SELECT * FROM Mesa";
        Connection conexion = ConexionBD.conectar();
        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                mesas.add(new Mesa(resultSet.getInt("numero"), resultSet.getBoolean("estado")));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las mesas: " + e.getMessage());
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return mesas;
    }
    
    //Metodo obtener numeros de mesa
    public ArrayList<Integer> obtenerMesas() {
        Connection conexion = ConexionBD.conectar();
        ArrayList<Integer> numerosMesa = new ArrayList<>();
    
        if (conexion != null) {
            String query = "SELECT numero FROM Mesa WHERE activo = true";
    
            try (PreparedStatement stmt = conexion.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
    
                while (rs.next()) {
                    numerosMesa.add(rs.getInt("numero"));
                }    
            } catch (SQLException e) {
                System.out.println("Error al buscar mesas: " + e.getMessage());
            }
        }    
        return numerosMesa;
    }

    }

