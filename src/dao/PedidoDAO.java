package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.List;

import model.Pedido;
import model.PedidoPlato;

public class PedidoDAO {

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
}