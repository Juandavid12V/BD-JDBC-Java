import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java. sql.*;
import java.util.Scanner;
import java.io.InputStreamReader;

public class buscar{
    public static void main (String [ ] args) throws IOException, SQLException, ClassNotFoundException {
        String dbUrl = "jdbc:postgresql://localhost:5432/gente";
        //String dbUrl = "jdbc:odbc:postgres";
        String usuario = "postgres";
        String contrasena = "123456789";
        //registrar la clase del driver, Cargar el controlador
        Class.forName("org.postgresql.Driver");
        //Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
        //obtener el objeto de conexión
        Connection c=DriverManager.getConnection(dbUrl,usuario,contrasena);
        // Cargar el controlador (se registra solo)
        Statement s = c.createStatement ( ) ;

        Scanner sc = new Scanner(System.in);
        System.out.println("Por favor ingrese apelllido"); 
        String apellidoBuscado = sc.nextLine();

        // Código SQL:
        
        ResultSet r =s.executeQuery("SELECT NOMBRE, APELLIDO, CORREO " +
                "FROM gente " +
                "WHERE " +
                "(APELLIDO= '" + apellidoBuscado+ "')" +
                " AND (CORREO Is Not Null) " +
                "ORDER BY NOMBRE") ;
        while(r.next ( )){
            //El uso de mayúsculas no importa:
            System.out.println(r.getString("APELLIDO") + ", " +
                    r.getString("NOMBRE") +
                    ": " +
                    r.getString("CORREO") ) ;
        }
        s.close ( ) ; // También cierra el Resultset
    }
} //Buscar