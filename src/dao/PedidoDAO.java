package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Pedido;
import model.PedidoPlato;

public class PedidoDAO {
    //Metodo para insertar platos en los pedidos
    public boolean insertarPedidoConPlatos(Pedido pedido, List<PedidoPlato> platos) {
        Connection conexion = ConexionBD.conectar();
        PreparedStatement pedidoStmt = null;
        PreparedStatement platoStmt = null;
        boolean resultado = false;

        if (conexion != null) {
            try {
                // Deshabilitar el auto-commit para la transacción
                conexion.setAutoCommit(false);

                // Insertar el encabezado del pedido y obtener su ID
                String pedidoQuerry = "INSERT INTO Pedido (numero_mesa, hora_pedido) VALUES (?, ?)";
                pedidoStmt = conexion.prepareStatement(pedidoQuerry, Statement.RETURN_GENERATED_KEYS);
                pedidoStmt.setInt(1, pedido.getNumeroMesa());
                pedidoStmt.setObject(2, pedido.getHoraPedido()); // Usar setObject para CurrentTimeStamp
                pedidoStmt.executeUpdate();

                int idPedidoGenerado = -1; // Validar que se haya creado
                // Obtener id de pedido
                try (ResultSet generatedKeys = pedidoStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idPedidoGenerado = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Error al obtener el ID del pedido.");
                    }
                }

                // Insertar los platos del pedido
                String platoQuerry = "INSERT INTO Pedido_plato (id_pedido, codigo_producto, cantidad) VALUES (?, ?, ?)";
                platoStmt = conexion.prepareStatement(platoQuerry);

                for (PedidoPlato plato : platos) {
                    platoStmt.setInt(1, idPedidoGenerado);
                    platoStmt.setInt(2, plato.getCodigoProducto());
                    platoStmt.setInt(3, plato.getCantidad());
                    platoStmt.addBatch(); // Se ejecute el conjunto de inserciones
                }

                int[] resultadosPlatos = platoStmt.executeBatch();
                boolean todosPlatosInsertados = true;

                // Comprueba que hayan varios platos
                for (int resultadoPlato : resultadosPlatos) {
                    if (resultadoPlato <= 0) {
                        todosPlatosInsertados = false;
                        break;
                    }
                }
                //Se realiza la insercion
                if (todosPlatosInsertados) {
                    conexion.commit();
                    resultado = true;
                } else {
                    conexion.rollback();
                    System.out.println("Error al insertar los platos del pedido. Se ha revertido la transacción.");
                }

            } catch (SQLException e) {
                System.out.println("Error al insertar el pedido con platos: " + e.getMessage());
                resultado = false;
            }
            // Cierra conexiones y volver a habilitar el auto-comit
            try { 
                if (platoStmt != null) platoStmt.close(); 
                if (pedidoStmt != null) pedidoStmt.close(); 
                if (conexion != null) conexion.setAutoCommit(true);
                if (conexion != null) conexion.close();
            
            } catch (SQLException e) { 
                e.printStackTrace(); // Muestra los errores de cerrar las conexiones
            }
        }
        // True o flase
        return resultado;
    }

    //Metodo para obtener todos los platos/bebidas en un hasmap
    public static Map<String, Integer> obtenerPlatosPedido(Integer numeroMesa) {
        HashMap<String, Integer> mapaPlatosPedido = new HashMap<>();
        Connection conexion = ConexionBD.conectar();

        if (conexion != null) {
                        
            //Hay que introducir el numero de la mesa
            String query = "SELECT pr.nombre AS nombre_plato, pp.cantidad " +
                    "FROM Pedido_plato AS pp " +
                    "INNER JOIN Producto AS pr ON pp.codigo_plato = pr.codigo " +
                    "WHERE " + 
                        "pp.id_pedido = ( " +
                            "SELECT id FROM Pedido " +
                            "WHERE numero_mesa = ? " +
                            "ORDER BY hora_pedido DESC " +
                            "LIMIT 1 )" ;

            try ( PreparedStatement stmt = conexion.prepareStatement(query)) {
                stmt.setInt(1, numeroMesa);
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) {
                    //Obtiene nombre del plato y cantidad y lo mete en el mapa
                    String nombrePlato = rs.getString("nombre_plato");
                    int cantidad = rs.getInt("cantidad");
                    mapaPlatosPedido.put(nombrePlato, cantidad);
                }
                
            } catch (SQLException e) {
                System.out.println("Error al buscar platos por numero de mesa: " + e.getMessage());
            }
        }
        return mapaPlatosPedido;
    }
    
    //Metodo Obtener precio total
    public static double calcularCuenta(Integer numeroMesa) {
        Map<String, Integer> platosPedido = obtenerPlatosPedido(numeroMesa);
        double precioTotal = 0.0;
        Connection conexion = ConexionBD.conectar();

        if (conexion != null && platosPedido != null && !platosPedido.isEmpty()) {
            String query = "SELECT precio FROM Producto WHERE nombre = ?";

            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                //Recorre el hasmap y saca el nombre y cantidad
                for (Map.Entry<String, Integer> entrada : platosPedido.entrySet()) {
                    String nombrePlato = entrada.getKey();
                    int cantidad = entrada.getValue();

                    //Pasa el nombre de plato en la query
                    stmt.setString(1, nombrePlato);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        //Calcula el precio total
                        double precioUnitario = rs.getDouble("precio");
                        precioTotal += precioUnitario * cantidad;
                    } else {
                        System.out.println("Error: No se encontró el precio del plato: " + nombrePlato);
                    }
                }
                
            } catch (SQLException e) {
                System.out.println("Error al calcular el precio total del pedido: " + e.getMessage());
            }
        }
        return precioTotal;
    }

    //Metodo Quitar praductos, introduciendo numero de mesa, nombre producto y cantidad que se quita
    public static boolean quitarCantidadProductoPedido(int numeroMesa, String nombreProducto, int cantidadQuitar) {
        Connection conexion = ConexionBD.conectar();
        boolean resultado = false;
        Integer idPedidoUltimo;
        Integer codigoProducto;

        if (conexion != null) {
            try {
                //Obtener el ID del pedido más reciente relacionado a la mesa
                String queryObtenerUltimoPedido = "SELECT id FROM Pedido WHERE numero_mesa = ? ORDER BY hora_pedido DESC LIMIT 1";
                PreparedStatement stmtObtenerUltimoPedido = conexion.prepareStatement(queryObtenerUltimoPedido);
                //Se pasa el numero de mesa
                stmtObtenerUltimoPedido.setInt(1, numeroMesa);
                ResultSet rsUltimoPedido = stmtObtenerUltimoPedido.executeQuery();

                if (rsUltimoPedido.next()) {
                    idPedidoUltimo = rsUltimoPedido.getInt("id");
                    rsUltimoPedido.close();
                    stmtObtenerUltimoPedido.close();

                    // Obtener el código del producto a partir de su nombre
                    String queryObtenerCodigoProducto = "SELECT codigo FROM Producto WHERE nombre = ?";
                    PreparedStatement stmtObtenerCodigoProducto = conexion.prepareStatement(queryObtenerCodigoProducto);
                    stmtObtenerCodigoProducto.setString(1, nombreProducto);
                    ResultSet rsCodigoProducto = stmtObtenerCodigoProducto.executeQuery();

                    if (rsCodigoProducto.next()) {
                        codigoProducto = rsCodigoProducto.getInt("codigo");
                        rsCodigoProducto.close();
                        stmtObtenerCodigoProducto.close();

                        // Si se encontraron el pedido y el producto se modifica cantidad 
                        if (idPedidoUltimo != null && codigoProducto != null) {

                            // Obtener la cantidad actual del producto en el pedido
                            String querySelect = "SELECT cantidad FROM Pedido_plato WHERE id_pedido = ? AND codigo_plato = ?";
                            ResultSet rsCantidad;
                            //Se pasa el codigo del producto y el id del pedido
                            try (PreparedStatement stmtSelect = conexion.prepareStatement(querySelect)) {
                                
                                stmtSelect.setInt(1, idPedidoUltimo);
                                stmtSelect.setInt(2, codigoProducto);
                                rsCantidad = stmtSelect.executeQuery();

                                if (rsCantidad.next()) {
                                    int cantidadActual = rsCantidad.getInt("cantidad");
                                    int nuevaCantidad = cantidadActual - cantidadQuitar;
    
                                    // Actualiza cantidad si es mayor que 0
                                    if (nuevaCantidad > 0) {
                                        String queryUpdate = "UPDATE Pedido_plato SET cantidad = ? WHERE id_pedido = ? AND codigo_plato = ?";
                                        try (PreparedStatement stmtUpdate = conexion.prepareStatement(queryUpdate)) {
                                            stmtUpdate.setInt(1, nuevaCantidad);
                                            stmtUpdate.setInt(2, idPedidoUltimo);
                                            stmtUpdate.setInt(3, codigoProducto);
                                            int filasActualizadas = stmtUpdate.executeUpdate();
                                            resultado = (filasActualizadas > 0);
                                        }
                                        catch (SQLException e) {
                                            System.out.println("Error al actualizar la cantidad: " + e.getMessage());
                                        }
    
                                    //Eliminar el producto si la cantidad llega a 0
                                    } else if (nuevaCantidad <= 0) {
                                        String queryBorrar = "DELETE FROM Pedido_plato WHERE id_pedido = ? AND codigo_plato = ?";
                                        try (PreparedStatement stmtBorrar = conexion.prepareStatement(queryBorrar)) {
                                            stmtBorrar.setInt(1, idPedidoUltimo);
                                            stmtBorrar.setInt(2, codigoProducto);
                                            int filasEliminadas = stmtBorrar.executeUpdate();
                                            resultado = (filasEliminadas > 0);
                                        }
                                        catch (SQLException e){
                                            System.out.println("Error al eliminar producto del pedido: " + e.getMessage());
                                        }
                                    }
                                    rsCantidad.close();
                                } else {
                                    System.out.println("Error: No se encontró el producto " + nombreProducto + " en el pedido con ID " + idPedidoUltimo);
                                }
                            }
                            catch (SQLException e) {
                                System.out.println("Error al obtener la cantidad de producto: " + e.getMessage());

                            }
                        }
                    } else {
                        System.out.println("Error: No se encontró ningún producto para la mesa " + numeroMesa);
                    }
                } else {
                    System.out.println("Error: No se encontró ningún pedido para la mesa " + numeroMesa);
                }
            } catch (SQLException e) {
                System.out.println("Error al quitar cantidad del producto por mesa y nombre: " + e.getMessage());
            }
        }
        return resultado;
    }

}