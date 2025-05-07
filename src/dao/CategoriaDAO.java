package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoriaDAO {
    
 public void crearCategoria( String nombre){
   
        Connection conexion = ConexionBD.conectar();
       
        if(conexion != null){

        String query = "INSERT INTO Categoria (nombre) VALUES (?)";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            
            stmt.setString(1, nombre);
        
            stmt.executeUpdate();

            System.out.println("los datos se han introducido con exito");

        } catch (SQLException e) {

            System.out.println("error al introducir datos");

        }
    }
 }  
 
 public void borrarCategoria(int id){

        Connection conexion = ConexionBD.conectar();
        

        if(conexion != null){
             String query = "DELETE * Categoria WHERE id =" + id;

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            
            stmt.executeUpdate();
            System.out.println("los datos se han actualizado con exito");

        } catch (SQLException e) {

            System.out.println("error al borrar los datos");

        }
        }
 }

 public void modificarNombreCategoria(String nombre, String nuevonombre){

    Connection conexion = ConexionBD.conectar();
    if(conexion != null){
        String query = "UPDATE Categoria SET nombre = "+nuevonombre+" WHERE nombre=" + nombre;

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

          
            stmt.executeUpdate();
            System.out.println("los datos se han actualizado con exito");

        } catch (SQLException e) {

            System.out.println("error al borrar los datos");

        }
        }
    }

    public void mostrarCategorias(){

        Connection conexion = ConexionBD.conectar();
        if(conexion != null){
            String query = "SELECT * FROM Categoria";
        try (Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nombre: " + rs.getString("nombre"));

                System.out.println("-------------------------");

            }

        } catch (SQLException e) {
            System.out.println("error al realizar la consulta" + e.getMessage());

        }
        }

    }
    
    public ArrayList<String> nombresCategoriaArray(){

        Connection conexion = ConexionBD.conectar();
        ArrayList<String> nombresCategorias = new ArrayList<>();
        if(conexion!=null){ 
            String query = "SELECT nombre FROM Categoria;";
            try (Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
                    while (rs.next()) {
                        String nombreCategoria = rs.getString("nombre");
                        nombresCategorias.add(nombreCategoria);
                    }

                }catch(SQLException e){
                    System.out.println("error al realizar la consulta" + e.getMessage());

                }
                return nombresCategorias;
        }
        return null;
    }

    public static Map<Integer, String> obtenerCategorias() {
        Map<Integer, String> categorias = new HashMap<>();
        Connection conexion = ConexionBD.conectar();
        if (conexion != null) {
            String query = "SELECT id, nombre FROM Categoria";
            try (Statement stmt = conexion.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    categorias.put(id, nombre);
                }
            } catch (SQLException e) {
                System.out.println("Error al realizar la consulta: " + e.getMessage());
            }
        }
        return categorias;
    }
    public int ObtenerCategoriaPorNombre(String nombre){
        Connection conexion = ConexionBD.conectar();
        int idCategoria = 0;

    if (conexion != null) {
        String query = "SELECT id FROM Categoria WHERE nombre = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idCategoria = rs.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println("Error al realizar la consulta: " + e.getMessage());
        }
    }

    return idCategoria;
 }

    public void borrarCategoriaPorNombre(String nombre) {
        Connection conexion = ConexionBD.conectar();
        if (conexion != null) {
            String query = "DELETE FROM Categoria WHERE nombre = ?";
            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                stmt.setString(1, nombre);
                stmt.executeUpdate();
                System.out.println("Categoria eliminada con exito");
            } catch (SQLException e) {
                System.out.println("Error al eliminar la categoria: " + e.getMessage());
            }
        }
    }
}


