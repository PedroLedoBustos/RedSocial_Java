package BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public Connection getConexion(){
        Connection conexion= null;

        String url= "jdbc:mysql://localhost:3306/red";
        String user= "root";
        String password= "rembombory";

        try {
            conexion= DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida con éxito");
            return conexion;
            
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos. "+ e);
        }
        return conexion;
    }
}
