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
                String platoQuerry = "INSERT INTO Pedido_plato (id_pedido, codigo_producto) VALUES (?, ?)";
                platoStmt = conexion.prepareStatement(platoQuerry);

                for (PedidoPlato plato : platos) {
                    platoStmt.setInt(1, idPedidoGenerado);
                    platoStmt.setInt(2, plato.getCodigoProducto());
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
            String query = "SELECT pr.nombre AS nombre_plato " +
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
                    // Si el plato ya está en el mapa suma 1
                    mapaPlatosPedido.put(nombrePlato, mapaPlatosPedido.getOrDefault(nombrePlato, 0) + 1);
               
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

    //Metodo para añadir un plato a la cuenta
    public static boolean añadirPlatoPedido(int numeroMesa, String nombreProducto) {
        boolean resultado = false;
    
        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion != null) {
                String query = "INSERT INTO Pedido_plato (id_pedido, codigo_plato) " +
                                    "SELECT (SELECT id FROM Pedido WHERE numero_mesa = ? ORDER BY hora_pedido DESC LIMIT 1), " +
                                    "(SELECT codigo FROM Producto WHERE nombre = ?) ";
                                     
                try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                    stmt.setInt(1, numeroMesa);
                    stmt.setString(2, nombreProducto);
                    int filasInsertadas = stmt.executeUpdate();
                    resultado = (filasInsertadas > 0);
                    if (!resultado) {
                        
                        System.out.println("Error: No se pudo añadir el producto. Puede que no exista el pedido para la mesa o el producto.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al añadir el plato al pedido: " + e.getMessage());
        }
        return resultado;
    }
    
    //Metdo eliminar un plato del pedido
    public static boolean quitarPlatoPedido(int numeroMesa, String nombreProducto) {
        boolean resultado = false;
    
        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion != null) {
                String query = "DELETE FROM Pedido_plato " +
                                "WHERE id_pedido = (SELECT id FROM Pedido WHERE numero_mesa = ? ORDER BY hora_pedido DESC LIMIT 1) " +
                                "AND codigo_producto = (SELECT codigo FROM Producto WHERE nombre = ?) " +
                                "LIMIT 1" ;

                try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                    stmt.setInt(1, numeroMesa);
                    stmt.setString(2, nombreProducto);
                    int filasEliminadas = stmt.executeUpdate();
                    resultado = (filasEliminadas > 0);
                    if (!resultado) {
                        System.out.println("Advertencia: No se encontró el producto en el pedido para ser eliminado.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al quitar el plato del pedido: " + e.getMessage());
            resultado = false;
        }
        return resultado;
    }


}