package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Producto;

public class ProductoDAO {

    public void crearProducto( String nombre, String descripcion, double precio, int idcategoria){
        
        Connection conexion = ConexionBD.conectar();
       
        if(conexion != null){

        String query = "INSERT INTO Producto (nombre, descripcion, precio, id_categoria) VALUES (?,?,?,?)";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setDouble(3, precio);
            stmt.setInt(4, idcategoria);
        
            stmt.executeUpdate();

            System.out.println("Producto creado se han introducido con exito");

        } catch (SQLException e) {

            System.out.println("error al introducir datos");

        }
    }
 }  

     public void borrarProductoPorNombre(String nombre){
 
    Connection conexion = ConexionBD.conectar();
       
 
    if (conexion != null) {
        String query = "UPDATE Producto SET activo = FALSE WHERE nombre = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.executeUpdate();
            System.out.println("Producto eliminado con éxito");

        } catch (SQLException e) {
            System.out.println("Error al borrar el producto: " + e.getMessage());
        }
    }
 }
    public void modificarNombreProducto(int id, String nuevonombre){

        Connection conexion = ConexionBD.conectar();
        if(conexion != null){
            String query = "UPDATE Producto SET nombre = "+nuevonombre+" WHERE codigo=" + id;

            try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            
                stmt.executeUpdate();
                System.out.println("los datos se han actualizado con exito");

            } catch (SQLException e) {

                System.out.println("error al borrar los datos");

            }
        }
    }
    public void modificarDescripcionProducto(int id, String nuevadescripcion){

        Connection conexion = ConexionBD.conectar();
        if(conexion != null){
            String query = "UPDATE Producto SET descripcion = "+nuevadescripcion+" WHERE codigo=" + id;
    
            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
    
              
                stmt.executeUpdate();
                System.out.println("los datos se han actualizado con exito");
    
            } catch (SQLException e) {
    
                System.out.println("error al borrar los datos");
    
            }
        }
    }
    public void modificarCategoriaProducto(int id, int idCategoria){

        Connection conexion = ConexionBD.conectar();
        if(conexion != null){
            String query = "UPDATE Producto SET categoria = "+ id +" WHERE id_categoria=" + idCategoria;
    
            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
    
              
                stmt.executeUpdate();
                System.out.println("los datos se han actualizado con exito");
    
            } catch (SQLException e) {
    
                System.out.println("error al actualizar los datos");
    
            }
        }
    }
    public void modificarPrecioProducto(int id, double nuevoprecio){

        Connection conexion = ConexionBD.conectar();
        if(conexion != null){
            String query = "UPDATE Producto SET precio = "+nuevoprecio+" WHERE codigo=" + id;
    
            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
    
                
                stmt.executeUpdate();
                System.out.println("los datos se han actualizado con exito");
    
            } catch (SQLException e) {
    
                System.out.println("error al borrar los datos");
    
            }
            }
        }
     public Producto buscarProductoByID(int codigo){
        Connection conexion = ConexionBD.conectar();
        Producto producto = null;
        if(conexion != null){
            String query = "SELECT * FROM Producto WHERE codigo=" + codigo;
            try (Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
                
                if (rs.next()) {
                    producto = new Producto(
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("id_categoria")
                    );
                }
            }
            catch (SQLException e) {
                System.out.println("error al realizar la consulta" + e.getMessage());

    
            } 
        }
        return producto;
    }

    public ArrayList<String> obtenerNombresPorCategoria(String nombreCategoria) {
        ArrayList<String> nombres = new ArrayList<>();
        Connection conexion = ConexionBD.conectar();
    
        if (conexion != null) {
            String query = "SELECT p.nombre FROM Producto p INNER JOIN Categoria c ON p.id_categoria = c.id WHERE c.nombre = ? AND p.activo = TRUE AND c.activo = TRUE";

    
            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                stmt.setString(1, nombreCategoria);
                ResultSet rs = stmt.executeQuery();
    
                while (rs.next()) {
                    nombres.add(rs.getString("nombre"));
                }
    
            } catch (SQLException e) {
                System.out.println("Error al obtener nombres por categoría: " + e.getMessage());
            }
        }
        return nombres;
    }

    public int obtenerIdProductoPorNombre(String nombreProducto) {
        String sql = "SELECT codigo FROM Producto WHERE nombre = ?";
        int idProducto = -1; 

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, nombreProducto);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idProducto = resultSet.getInt("codigo"); 
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ID del producto por nombre: " + e.getMessage());
        }
        return idProducto;
    }

    public ArrayList<String> nombresProdcutoArray(){
        Connection conexion = ConexionBD.conectar();
        ArrayList<String> nombresProducto = new ArrayList<>();
        if(conexion!=null){ 
            String query = "SELECT nombre FROM Producto;";
            try (Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
                    while (rs.next()) {
                        String nombreproducto = rs.getString("nombre");
                        nombresProducto.add(nombreproducto);
                    }
                }catch(SQLException e){
                    System.out.println("error" + e.getMessage());
                }
                return nombresProducto;
        }
        return null;
    }
    
    public ArrayList<Producto> obtenerProductosPorCategoriaArray (int idCategoria){

    Connection conexion = ConexionBD.conectar();
    ArrayList<Producto> nombresProductoCategoria = new ArrayList<>();
    if(conexion!=null){
        String query = "SELECT * FROM Producto WHERE id_categoria = ?";
        try{
                PreparedStatement stmt = conexion.prepareStatement(query);
                stmt.setInt(1, idCategoria);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    nombresProductoCategoria.add(new Producto(rs.getInt("codigo"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        idCategoria
                    ));
                    
                }

            }catch(SQLException e){
                System.out.println("error" + e.getMessage());

            }
            return nombresProductoCategoria;
    }
    return null;
}            

    public boolean actualizarCampoProducto(String columna, int codigoProducto, Object valor) {
        String query = "UPDATE Producto SET " + columna + " = ? WHERE codigo = ?";
        try (Connection conexion = ConexionBD.conectar();
            PreparedStatement stmt = conexion.prepareStatement(query)) {
            if (valor instanceof Double) {
                stmt.setDouble(1, (Double) valor);
            } else if (valor instanceof Integer) {
                stmt.setInt(1, (Integer) valor);
            } else {
                stmt.setString(1, (String) valor);
            }
            stmt.setInt(2, codigoProducto);

            int filas_afectadas = stmt.executeUpdate();
            return filas_afectadas == 1;
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto (" + columna + "): " + e.getMessage());
            return false;
        }
    }
}
