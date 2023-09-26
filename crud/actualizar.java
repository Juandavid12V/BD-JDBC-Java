

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class actualizar {
    public static void main (String args [])
throws SQLException,
ClassNotFoundException{

String dbUrl =
"jdbc:postgresql://localhost/gente";

String usuario = "postgres";

String contrasena = "123456789";

//registrar la clase del driver, Cargar el controlador

Class.forName("org.postgresql.Driver");

//obtener el objeto de conexion

Connection c= DriverManager.getConnection(dbUrl,usuario,contrasena);

// Cargar el controlador (se registra solo)

Statement s = c.createStatement ( ) ;

String update = "update gente set NOMBRE = 'DANIEL' WHERE APELLIDO = VEGA' " ;

s.executeUpdate(update) ;

}//main

}//actualizar