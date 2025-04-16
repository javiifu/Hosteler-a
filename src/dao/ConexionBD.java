package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Se tendra que poner el usuario y contraseña que nos diga Saul
public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/Hosteleria";
    private static final String USUARIO = "dev";
    private static final String CONTRASENA = "password";


//Metodo para establecer conexion con la DB con el uso de la URL, usuario y contraseña
    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }
}

