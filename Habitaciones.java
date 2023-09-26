import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Habitaciones {
    public static void main (String [ ] args) throws IOException, SQLException, ClassNotFoundException {
        String dbUrl = "jdbc:postgresql://localhost:5432/HOTEL";
        //String dbUrl = "jdbc:odbc:postgres";
        String usuario = "postgres";
        String contrasena = "123456789";
        //registrar la clase del driver, Cargar el controlador
        Class.forName("org.postgresql.Driver");
        //Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
        //obtener el objeto de conexión
        Connection c= DriverManager.getConnection(dbUrl,usuario,contrasena);
        // Cargar el controlador (se registra solo)
        Statement s = c.createStatement ( ) ;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Buscar Habitación por Tipo");
            System.out.println("2. Actualizar Habitación");
            System.out.println("3. Eliminar Habitación");
            System.out.println("4. Insertar Nueva Habitación");
            System.out.println("5. Volver al Menú Principal");


            int opcion = sc.nextInt();
            sc.nextLine(); // Consume el newline

            switch (opcion) {
                case 1:
                    System.out.println("Por favor ingrese el tipo de habitación (Doble - Individual):");
                    String tipo = sc.nextLine();
                    buscarHabitacionesPorTipo(s, tipo);
                    break;
                case 2:
                    System.out.println("Actualizar Numero Habitación ");
                    actualizarHabitacion(s);
                    break;
                case 3:
                    System.out.println("Eliminar Habitación ");
                    borrarHabitacion(s);
                    break;
                case 4:
                    System.out.println("Insertar Nueva Habitación");
                    insertarHab(s);
                    break;
                case 5:
                    return; 
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
                }
            }
        }
    
        //BUSCAR
        // Código Habitaciones
        static void buscarHabitacionesPorTipo(Statement s, String tipo) throws SQLException {
            ResultSet r = s.executeQuery("SELECT NUM_HABITACION, TIPO, PRECIO, CAMAS " +
                    "FROM Habitaciones " +
                    "WHERE " +
                    "(TIPO= '" + tipo + "')" +
                    " AND (PRECIO IS NOT NULL) " +
                    "ORDER BY CAMAS");


            while(r.next ( )){
            //El uso de mayúsculas no importa:
            System.out.println( "Num_habitacion: " + r.getString ("NUM_HABITACION") + " TIPO:" +
            r.getString("TIPO") +
            " PRECIO:" +
             r.getString("PRECIO") +
            " CAMAS:" +
            r.getString("CAMAS") +
            " " );
        }
             }
         static void actualizarHabitacion(Statement s) throws SQLException {
                String borrarReservas = "DELETE FROM Reservas WHERE NUM_HABITACION = 3" ;
                s.executeUpdate(borrarReservas);
            
                String update = "update Habitaciones set TIPO = 'Grupal' WHERE PRECIO = '600000' " ;
                s.executeUpdate(update);
            }
            
         static void borrarHabitacion(Statement s) throws SQLException {
            String borrarReservas = "DELETE FROM Reservas WHERE NUM_HABITACION = 1" ;
            s.executeUpdate(borrarReservas);

            String sql = "DELETE FROM Habitaciones WHERE (PRECIO = '500000' ) ";
            s.executeUpdate(sql) ;

        }
         static void insertarHab(Statement s) throws SQLException {
             String insert = "INSERT INTO Habitaciones(TIPO, PRECIO, CAMAS)" +
             "VALUES ('Doble' , 550000, 5) ";
             s.executeUpdate(insert);
        

        
        s.close ( ) ; // También cierra el Resultset
    }
} //Busca

