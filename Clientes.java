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
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Buscar Cliente por Nombre");
            System.out.println("2. Actualizar Cliente");
            System.out.println("3. Eliminar Cliente");
            System.out.println("4. Insertar Nuevo Cliente");
            System.out.println("5. Volver al Menú Principal");

            int opcion = sc.nextInt();
            sc.nextLine(); // Consume el newline

            switch (opcion) {
                case 1:
                    System.out.println("Por favor ingrese el nombre del cliente:");
                    String nombre = sc.nextLine();
                    buscarClientePorNombre(s, nombre);
                    break;
                case 2:
                    System.out.println("Actualizar Cliente ");
                    actualizarCliente(s);
                    break;
                case 3:
                    System.out.println("Eliminar Cliente ");
                    eliminarCliente(s);
                    break;
                case 4:
                    System.out.println("Insertar Nuevo Cliente");
                    insertarCliente(s);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
            }
        }
    }

    //BUSCAR
    // Código Clientes
    static void buscarClientePorNombre(Statement s, String nombre) throws SQLException {
        ResultSet r = s.executeQuery("SELECT NUM_CLIENTE, NOMBRE, DIRECCION, TELEFONO1, TELEFONO2, EMAIL, NACIONALIDAD " +
                "FROM Clientes " +
                "WHERE " +
                "(NOMBRE= '" + nombre + "')" +
                " AND (EMAIL IS NOT NULL) " +
                "ORDER BY NUM_CLIENTE");

        while (r.next()) {
            // El uso de mayúsculas no importa:
            System.out.println(r.getString("NOMBRE") + ", " +
                    r.getString("DIRECCION") +
                    ": " +
                    r.getString("EMAIL") +
                    ": " +
                    r.getString("NACIONALIDAD"));
        }
    }

    static void actualizarCliente(Statement s) throws SQLException {

        //String borrarReservas = "DELETE FROM Reservas WHERE NUM_CLIENTE = 3" ;
        //s.executeUpdate(borrarReservas);
    
        String update = "update Clientes set Nombre = 'David' WHERE DIRECCION = 'Cra 8 12 24' " ;
        s.executeUpdate(update);
    
    }

    static void eliminarCliente(Statement s) throws SQLException {
       // String borrarReservas = "DELETE FROM Reservas WHERE NUM_CLIENTE = 2" ;  //PARA BORRAR LA LLAVE FORANEA
        //s.executeUpdate(borrarReservas);

        String sql = "DELETE FROM Clientes WHERE (DIRECCION = 'Cra 5 12 21' ) ";
        s.executeUpdate(sql) ;
    }

    static void insertarCliente(Statement s) throws SQLException {
        String insert = "INSERT INTO Clientes (NOMBRE, DIRECCION, TELEFONO1, TELEFONO2, EMAIL, NACIONALIDAD) " +
                       "VALUES ('santiago', 'cra 3 12 12', 3127761111, 3147915151, 'santiago@gmail.com', 'colombia')";
        s.executeUpdate(insert);
    }
    
}
