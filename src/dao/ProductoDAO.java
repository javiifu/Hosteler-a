package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductoDAO {

    public void crearProducto( String nombre, String descripcion, double precio, int idcategoria){
        
        Connection conexion = ConexionBD.conectar();
       

        if(conexion != null){

        String query = "INSERT INTO Prodcuto (nombre, descripcion, precio, id_categotia) VALUES (?,?,?,?)";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setDouble(3, precio);
            stmt.setInt(4, idcategoria);
        
            stmt.executeUpdate();

            System.out.println("los datos se han introducido con exito");

        } catch (SQLException e) {

            System.out.println("error al introducir datos");

        }
    }
 }  

 public void borrarProducto(int id){

    Connection conexion = ConexionBD.conectar();
        

        if(conexion != null){
             String query = "DELETE * Producto WHERE id =" + id;

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.executeUpdate();
            System.out.println("los datos se han actualizado con exito");

        } catch (SQLException e) {

            System.out.println("error al borrar los datos");

        }
        }
 }
 public void borrarProductoPorNombre(String nombre){

    Connection conexion = ConexionBD.conectar();
        

        if(conexion != null){
             String query = "DELETE * Producto WHERE nombre =" + nombre;

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.executeUpdate();
            System.out.println("los datos se han actualizado con exito");

        } catch (SQLException e) {

            System.out.println("error al borrar los datos");

        }
        }
 }
 public void modificarNombreProdcuto(int id, String nuevonombre){

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
    public void modificarDescripcionProdcuto(int id, String nuevadescripcion){

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
        public void modificarPrecioProdcuto(int id, double nuevoprecio){

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
        public void mostrarTodosProductos(){
            Connection conexion = ConexionBD.conectar();
            if(conexion != null){
                String query = "SELECT * FROM Productos";
            try (Statement stmt = conexion.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    System.out.println("Codigo: " + rs.getInt("id"));
                    System.out.println("Nombre: " + rs.getString("nombre"));
                    System.out.println("Descripcion: " + rs.getString("descripcion"));
                    System.out.println("Precio: " + rs.getInt("precio"));
                    System.out.println("Categoria: " + rs.getInt("id_categoria"));

                    System.out.println("-------------------------");

                }

            } catch (SQLException e) {
                System.out.println("error al realizar la consulta" + e.getMessage());

        }
        }

        }
        public void mostrarProductosByID(int id){
            Connection conexion = ConexionBD.conectar();
            if(conexion != null){
                String query = "SELECT * FROM Productos WHERE codigo=" + id;
            try (Statement stmt = conexion.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                   
                    System.out.println("Nombre: " + rs.getString("nombre"));
                    System.out.println("Descripcion: " + rs.getString("descripcion"));
                    System.out.println("Precio: " + rs.getInt("precio"));
                    System.out.println("Categoria: " + rs.getInt("id_categoria"));

                    System.out.println("-------------------------");

                }

            } catch (SQLException e) {
                System.out.println("error al realizar la consulta" + e.getMessage());

        }
        }

        } 
        public void mostrarProductosByCategoria(int idcategoria){
            Connection conexion = ConexionBD.conectar();
            if(conexion != null){
                String query = "SELECT * FROM Productos WHERE id_categoria="+idcategoria;
            try (Statement stmt = conexion.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    System.out.println("Codigo: " + rs.getInt("id"));
                    System.out.println("Nombre: " + rs.getString("nombre"));
                    System.out.println("Descripcion: " + rs.getString("descripcion"));
                    System.out.println("Precio: " + rs.getInt("precio"));
                  

                    System.out.println("-------------------------");

                }

            } catch (SQLException e) {
                System.out.println("error" + e.getMessage());

        }
        }

        }
        public ArrayList<String> obtenernombresProductoArray(){

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
    public ArrayList<String> obtenerProductosPorCatagoriaArray (int idCategoria){

        Connection conexion = ConexionBD.conectar();
        ArrayList<String> nombresProductoCategoria = new ArrayList<>();
        if(conexion!=null){ 
            String query = "SELECT nombre FROM Producto WHERE id_categoria=" + idCategoria;
            try (Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
                    while (rs.next()) {
                        String nombreproducto = rs.getString("nombre");
                        nombresProductoCategoria.add(nombreproducto);
                    }

                }catch(SQLException e){
                    System.out.println("error" + e.getMessage());

                }
                return nombresProductoCategoria;
        }
        return null;
    }      
}
