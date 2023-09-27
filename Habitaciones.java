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
                    System.out.println("Por favor ingrese el tipo de habitación ");
                    String tipo = sc.nextLine();
                    buscarHabitacionesPorTipo(s, tipo);
                    break;
                case 2:
                    System.out.println("Actualizar Numero Habitación ");
                    actualizarHabitacion(c, sc);
                    break;
                case 3:
                    System.out.println("Eliminar Habitación ");
                    eliminarHabitacion(c, sc);
                    break;
                case 4:
                    System.out.println("Insertar Nueva Habitación");
                    insertarHabitacion(s);
                    break;
                case 5:
                    return; 
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
                }
            }
        }
    
  
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
   
        static void actualizarHabitacion(Connection connection, Scanner scanner) throws SQLException {
            Statement statement = connection.createStatement();
            System.out.println("Ingrese el número de habitación que desea actualizar: ");
            int numHabitacion = scanner.nextInt();
            scanner.nextLine(); 
        
            String seleccionar = "SELECT NUM_HABITACION FROM Habitaciones WHERE NUM_HABITACION = " + numHabitacion;
            ResultSet resultSet = statement.executeQuery(seleccionar);
        
            if (resultSet.next()) {
                System.out.println("Ingrese el nuevo tipo de habitación: ");
                String nuevoTipo = scanner.nextLine();
        
                System.out.println("Ingrese el nuevo precio: ");
                int nuevoPrecio = scanner.nextInt();
        
                System.out.println("Ingrese el nuevo numero de camas: ");
                int nuevasCamas = scanner.nextInt();
        
                String updateQuery = "UPDATE Habitaciones SET TIPO = '" + nuevoTipo + "', PRECIO = '" + nuevoPrecio + "', CAMAS = '" + nuevasCamas
                + "' WHERE NUM_HABITACION = " + numHabitacion;

                statement.executeUpdate(updateQuery);

            } else {
                System.out.println("No se encontro una habitacion con el numero ingresado.");
            }
        
            statement.close();
        }
        
        static void eliminarHabitacion(Connection connection, Scanner scanner) throws SQLException {
            Statement statement = connection.createStatement();
            System.out.println("Ingrese el numero de habitacion que desea eliminar: ");
            int numHabitacion = scanner.nextInt();
            scanner.nextLine(); 
        
            String seleccionar = "SELECT NUM_HABITACION FROM Habitaciones WHERE NUM_HABITACION = " + numHabitacion;
            ResultSet resultSet = statement.executeQuery(seleccionar);
        
            if (resultSet.next()) {
        
                numHabitacion = resultSet.getInt("NUM_HABITACION");
                String eliminarReserva = "DELETE FROM Reservas WHERE NUM_HABITACION = " + numHabitacion;
                statement.executeUpdate(eliminarReserva);

                String eliminarHabitacion = "DELETE FROM Habitaciones WHERE NUM_HABITACION = " + numHabitacion;
                statement.executeUpdate(eliminarHabitacion);

            } else {
                System.out.println("No se encontró una habitacion con el número ingresado.");
            }
        
            statement.close();
        }

        
        static void insertarHabitacion(Statement s) throws SQLException {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese el tipo de habitación: ");
            String tipo = scanner.nextLine();
        
            System.out.println("Ingrese el precio: ");
            double precio = scanner.nextDouble();
        
            System.out.println("Ingrese el número de camas: ");
            int camas = scanner.nextInt();
        
            String insert = "INSERT INTO Habitaciones (TIPO, PRECIO, CAMAS) VALUES ('" + tipo + "', " + precio + ", " + camas + ")";
            s.executeUpdate(insert);
    
        }
    
    }


