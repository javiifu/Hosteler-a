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

            System.out.println("Categoría creada con exito");

        } catch (SQLException e) {

            System.out.println("error al introducir datos" +e.getMessage());

        }
    }
 }  
 
 public void borrarCategoria(int id) {
    Connection conexion = ConexionBD.conectar();

    if (conexion != null) {
        String desactivarCategoria = "UPDATE Categoria SET activo = FALSE WHERE id = ?";
        String desactivarProductos = "UPDATE Producto SET activo = FALSE WHERE id_categoria = ?";

        try (
            PreparedStatement stmtCategoria = conexion.prepareStatement(desactivarCategoria);
            PreparedStatement stmtProducto = conexion.prepareStatement(desactivarProductos)
        ) {
            // Desactivar la categoría
            stmtCategoria.setInt(1, id);
            stmtCategoria.executeUpdate();

            // Desactivar los productos de esa categoría
            stmtProducto.setInt(1, id);
            stmtProducto.executeUpdate();

            System.out.println("Categoría y productos desactivados correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al desactivar la categoría o productos: " + e.getMessage());
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
        if(conexion != null){ 
            String query = "SELECT nombre FROM Categoria WHERE activo = TRUE";
            try (Statement stmt = conexion.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    nombresCategorias.add(rs.getString("nombre"));
                }
            } catch(SQLException e){
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
            String query = "SELECT id, nombre FROM Categoria WHERE activo = TRUE";
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
        String query = "SELECT id FROM Categoria WHERE nombre = ? AND activo = TRUE";
        // Se utiliza un PreparedStatement para evitar inyecciones SQL
         // y mejorar la seguridad de la consulta.
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
        String obtenerIdCategoria = "SELECT id FROM Categoria WHERE nombre = ?";
        String desactivarCategoria = "UPDATE Categoria SET activo = FALSE WHERE id = ?";
        String desactivarProductos = "UPDATE Producto SET activo = FALSE WHERE id_categoria = ?";

        try (
            PreparedStatement stmtId = conexion.prepareStatement(obtenerIdCategoria);
        ) {
            stmtId.setString(1, nombre);
            ResultSet rs = stmtId.executeQuery();

            if (rs.next()) {
                int idCategoria = rs.getInt("id");

                try (
                    PreparedStatement stmtCategoria = conexion.prepareStatement(desactivarCategoria);
                    PreparedStatement stmtProductos = conexion.prepareStatement(desactivarProductos)
                ) {
                    // Desactivar categoría
                    stmtCategoria.setInt(1, idCategoria);
                    stmtCategoria.executeUpdate();

                    // Desactivar productos relacionados
                    stmtProductos.setInt(1, idCategoria);
                    stmtProductos.executeUpdate();

                    System.out.println("La categoría y sus productos han sido desactivados correctamente.");
                }

            } else {
                System.out.println("No se encontró ninguna categoría con ese nombre.");
            }

        } catch (SQLException e) {
            System.out.println("Error al desactivar la categoría o productos: " + e.getMessage());
        }
    }
}

}


