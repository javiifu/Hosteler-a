package dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.Pedido;
import model.PedidoPlato;
import model.Producto;

public class PedidoDAO {
    //Metodo para insertar platos en los pedidos
    public boolean insertarPedidoConPlatos(Pedido pedido, ArrayList<PedidoPlato> platos) {
        Connection conexion = ConexionBD.conectar();
        PreparedStatement pedidoStmt = null;
        PreparedStatement platoStmt = null;
        boolean resultado = false;

        if (conexion != null) {
            try {
                // Deshabilitar el auto-commit para la transacción
                conexion.setAutoCommit(false);

                // Insertar el encabezado del pedido y obtener su ID
                String pedidoQuerry = "INSERT INTO Pedido (numero_mesa, hora_pedido) VALUES (?, CURRENT_TIMESTAMP)";
                pedidoStmt = conexion.prepareStatement(pedidoQuerry, Statement.RETURN_GENERATED_KEYS);
                pedidoStmt.setInt(1, pedido.getNumeroMesa());
                 // Usar setObject para CurrentTimeStamp
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
                String platoQuerry = "INSERT INTO Pedido_plato (id_pedido, codigo_plato) VALUES (?, ?)";
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
    public double calcularCuenta(Integer numeroMesa) {
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


    public double obtenerPrecioPlato(String nombrePlato) {
        double precio = 0.0;
        Connection conexion = ConexionBD.conectar();

        if (conexion != null) {
            String query = "SELECT precio FROM Producto WHERE nombre = ?";

            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                stmt.setString(1, nombrePlato);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    precio = rs.getDouble("precio");
                } else {
                    System.out.println("Error: No se encontró el precio del plato: " + nombrePlato);
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener el precio del plato: " + e.getMessage());
            }
        }
        return precio;
    }

    public int obtenerNumeroPedido(int numeroMesa) {
        int idPedido = 0 ;
        boolean resultado = false;
    
        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion != null) {
                String query = "SELECT id FROM Pedido WHERE numero_mesa = ? ORDER BY hora_pedido DESC LIMIT 1";
                                     
                try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                    stmt.setInt(1, numeroMesa);
                                        
                    idPedido = stmt.executeUpdate();
                    resultado = (idPedido > 0);
                    if (!resultado) {
                        System.out.println("Error: No se pudo añadir el producto. Puede que no exista el pedido para la mesa o el producto.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al añadir el plato al pedido: " + e.getMessage());
        }
        return idPedido;
    }
    //Metodo para añadir un plato a la cuenta
    public boolean añadirPlatoPedido(int numeroMesa, int codigoProducto) {
        boolean resultado = false;
    
        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion != null) {
                String query = "INSERT INTO Pedido_plato (id_pedido, codigo_plato) " +
                                "VALUES (SELECT id FROM Pedido WHERE numero_mesa = ? ORDER BY hora_pedido DESC LIMIT 1 , ?)";
                                     
                try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                    stmt.setInt(1, numeroMesa);
                    stmt.setInt(2, codigoProducto);
                    
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
    public boolean quitarPlatoPedido(int numeroMesa, String nombreProducto) {
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
                        System.out.println("Error: No se encontró el producto en el pedido para ser eliminado.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al quitar el plato del pedido: " + e.getMessage());
            resultado = false;
        }
        return resultado;
    }

    public static Map<Producto, Integer> listaPlatosPedidoFactura(Pedido pedido) {
        Map<Producto, Integer> platosPedido = new HashMap<>();
        Connection conexion = ConexionBD.conectar();

        if (conexion != null) {
            String query = "SELECT pr.nombre AS nombre_plato, pr.precio AS precio_plato, pr.id_categoria AS categoria, pp.cantidad AS cantidad_plato" +
                    "FROM Pedido_plato AS pp " +
                    "INNER JOIN Producto AS pr ON pp.codigo_producto = pr.codigo " +
                    "WHERE pp.id_pedido = ?";

            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                stmt.setInt(1, pedido.getId());
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    String nombrePlato = rs.getString("nombre_plato");
                    double precioPlato = rs.getDouble("precio_plato");
                    int cantidad = rs.getInt("cantidad");
                    Integer categoria = rs.getInt("categoria");
                    platosPedido.put(new Producto(nombrePlato, precioPlato, categoria), cantidad);
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener los platos del pedido: " + e.getMessage());
            }
        }
        return platosPedido;
    }

    //Metodo cambiar estado pagado
    public void cambiarEstadoPagado(int numeroMesa) {
        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion != null) {
                String query = "UPDATE Mesa SET estado = NOT estado WHERE numero = ?";
                
                try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            
                    stmt.setInt(1, numeroMesa);
                    stmt.executeUpdate();
            
                } catch (SQLException e) {
                    System.out.println("Error: no se encuentra la mesa " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al cambiar a cobrado: " + e.getMessage());
        }

    }

    public static ArrayList<Pedido> pedidosPorDia(Date fecha){
        ArrayList<Pedido> pedidos = new ArrayList<>();
        Connection conexion = ConexionBD.conectar();

        if (conexion != null) {
            String query = "SELECT * FROM Pedido WHERE fecha = ?";

            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                stmt.setDate(1, fecha);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    int numeroMesa = rs.getInt("numero_mesa");
                    Time horaPedido = rs.getTime("hora_pedido");
                    String tipo_pago = rs.getString("tipo_pago");
                    pedidos.add(new Pedido(id, numeroMesa, horaPedido, fecha, tipo_pago));
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener los pedidos por día: " + e.getMessage());
            }
        }
        return pedidos;
    }

    public Pedido obtenerPedidoPorMesa(int numeroMesa) {
        String query = "SELECT * FROM Pedido WHERE numero_mesa = ? AND completado = FALSE ORDER BY hora_pedido DESC LIMIT 1";
        Connection conexion = ConexionBD.conectar();
        Pedido pedido = null;

        if (conexion != null) {
            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                stmt.setInt(1, numeroMesa);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getInt("numero_mesa"),
                        rs.getTime("hora_pedido"),
                        rs.getDate("fecha_pedido"),
                        rs.getString("tipo_pago"),
                        rs.getBoolean("completado"),
                        rs.getBoolean("pagado"),
                        rs.getDouble("precio_total")
                    );
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener el pedido por mesa: " + e.getMessage());
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

        return pedido;
    }

    public void insertarPedido(Pedido pedido) {
        String query = "INSERT INTO Pedido (precio_total, numero_mesa, completado, pagado, tipo_pago) VALUES (?, ?, ?, ?, ?)";
        Connection conexion = ConexionBD.conectar();
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setDouble(1, pedido.getPrecio_total());
            stmt.setInt(2, pedido.getNumeroMesa());
            stmt.setBoolean(3, pedido.isCompletado());
            stmt.setBoolean(4, pedido.isPagado());
            stmt.setString(5, pedido.getTipo_pago());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                pedido.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar el pedido: " + e.getMessage());
            
        }
    }
}