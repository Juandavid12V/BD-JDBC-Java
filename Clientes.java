import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Clientes {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        String dbUrl = "jdbc:postgresql://localhost:5432/HOTEL";
        String usuario = "postgres";
        String contrasena = "123456789";
        
        Class.forName("org.postgresql.Driver");
        Connection c = DriverManager.getConnection(dbUrl, usuario, contrasena);
        Statement s = c.createStatement();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Buscar Cliente por Nombre");
            System.out.println("2. Actualizar Cliente");
            System.out.println("3. Eliminar Cliente");
            System.out.println("4. Insertar Nuevo Cliente");
            System.out.println("5. Volver al Menú Principal");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el newline

            switch (opcion) {
                case 1:
                    buscarClientePorNombre(s);
                    break;
                case 2:
                    System.out.println("Actualizar Cliente ");
                    actualizarCliente(c, scanner);
                    break;
                case 3:
                    System.out.println("Eliminar Cliente ");
                    eliminarCliente(c, scanner);
                    break;
                case 4:
                    System.out.println("Insertar Nuevo Cliente");
                    insertarCliente(s);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opcion no valida. Por favor, ingrese una opción valida.");
            }
        }
    }

    //BUSCAR
    // Código Clientes
    static void buscarClientePorNombre(Statement s) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del cliente: ");
        String nombre = scanner.nextLine();

        ResultSet r = s.executeQuery("SELECT NUM_CLIENTE, NOMBRE, DIRECCION, TELEFONO1, TELEFONO2, EMAIL, NACIONALIDAD " +
                "FROM Clientes " +
                "WHERE " +
                "(NOMBRE= '" + nombre + "')" +
                " AND (EMAIL IS NOT NULL) " +
                "ORDER BY NUM_CLIENTE");

        while (r.next()) {
            System.out.println(r.getString("NOMBRE") + ", " +
                    r.getString("DIRECCION") +
                    ": " +
                    r.getString("EMAIL") +
                    ": " +
                    r.getString("NACIONALIDAD"));
        }
    }

    static void actualizarCliente(Connection connection, Scanner scanner) throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println("Ingrese el correo electrónico del cliente que desea actualizar: ");
        String email = scanner.nextLine();
    
        String seleccionar = "SELECT NUM_CLIENTE FROM Clientes WHERE EMAIL = '" + email + "'";
        ResultSet s = statement.executeQuery(seleccionar); //consulta sql
    
        if (s.next()) {
            int numCliente = s.getInt("num_cliente"); //obtener el valor de una columna llamada "num_cliente" de la fila actual en el conjunto de resultados 
    
            System.out.println("Ingrese el nuevo nombre: ");
            String nuevoNombre = scanner.nextLine();
    
            String updateQuery = "UPDATE Clientes SET Nombre = '" + nuevoNombre + "' WHERE NUM_CLIENTE = " + numCliente;
            statement.executeUpdate(updateQuery);
        } 
        
    
        statement.close();
    }
    

    static void eliminarCliente(Connection connection, Scanner scanner) throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println("Ingrese el correo electrónico del cliente a eliminar: ");
        String email = scanner.nextLine();
    
        String seleccionar = "SELECT NUM_CLIENTE FROM Clientes WHERE EMAIL = '" + email + "'";
        ResultSet s = statement.executeQuery(seleccionar);
    
        if (s.next()) {
            int numCliente = s.getInt("num_cliente");
           
            // Consulta la reserva
            String seleccionar2 = "SELECT NUM_HABITACION FROM Reservas WHERE num_cliente = '" + numCliente + "'";
            ResultSet s2 = statement.executeQuery(seleccionar2);
    
            if (s2.next()) {
                int numHabitacion = s2.getInt("NUM_HABITACION");
    
                // Elimina la reserva
                String eliminarReserva = "DELETE FROM Reservas WHERE NUM_HABITACION = " + numHabitacion;
                statement.executeUpdate(eliminarReserva);
            }
    
            // Elimina el cliente
            String eliminarCliente = "DELETE FROM Clientes WHERE NUM_CLIENTE = " + numCliente;
            statement.executeUpdate(eliminarCliente);
        }
    }

    static void insertarCliente(Statement s) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del nuevo cliente: ");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese la dirección del nuevo cliente: ");
        String direccion = scanner.nextLine();

        System.out.println("Ingrese el teléfono 1 del nuevo cliente: ");
        String telefono1 = scanner.nextLine();

        System.out.println("Ingrese el teléfono 2 del nuevo cliente: ");
        String telefono2 = scanner.nextLine();

        System.out.println("Ingrese el correo electrónico del nuevo cliente: ");
        String email = scanner.nextLine();

        System.out.println("Ingrese la nacionalidad del nuevo cliente: ");
        String nacionalidad = scanner.nextLine();

        String insert = "INSERT INTO Clientes (NOMBRE, DIRECCION, TELEFONO1, TELEFONO2, EMAIL, NACIONALIDAD) " +
                "VALUES ('" + nombre + "', '" + direccion + "', '" + telefono1 + "', '" + telefono2 + "', '" + email + "', '" + nacionalidad + "')";
        s.executeUpdate(insert);
    }
    
}
