import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Reservas {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        String dbUrl = "jdbc:postgresql://localhost:5432/HOTEL";
        String usuario = "postgres";
        String contrasena = "123456789";
        Class.forName("org.postgresql.Driver");
        Connection c = DriverManager.getConnection(dbUrl, usuario, contrasena);
        Statement s = c.createStatement();
        Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("1. Buscar Reserva por Número");
                System.out.println("2. Actualizar Reserva");
                System.out.println("3. Eliminar Reserva");
                System.out.println("4. Insertar Nueva Reserva");
                System.out.println("5. Volver al Menú Principal");

                int opcion = sc.nextInt();
                sc.nextLine(); // Consume el newline

                switch (opcion) {
                    case 1:
                        System.out.println("Por favor ingrese el número de reserva:");
                        String numReserva = sc.nextLine();
                        buscarReservaPorNumero(s, numReserva);
                        break;
                    case 2:
                        System.out.println("Actualizar Reserva");
                        actualizarReserva(s);
                        break;
                    case 3:
                        System.out.println("Eliminar Reserva");
                        eliminarReserva(s);
                        break;
                    case 4:
                        System.out.println("Insertar Nueva Reserva");
                        insertarReserva(s);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
                }
            }
    }

    static void buscarReservaPorNumero(Statement s, String numReserva) throws SQLException {
        ResultSet r = s.executeQuery("SELECT NUM_HABITACION, NUM_CLIENTE, FECHA_LLEGADA, FECHA_SALIDA " +
                "FROM Reservas " +
                "WHERE " +
                "(NUM_HABITACION= '" + numReserva + "')" +
                " AND (FECHA_LLEGADA IS NOT NULL) " +
                "ORDER BY FECHA_SALIDA");

        while (r.next()) {
            System.out.println("Número de Habitación: " + r.getString("NUM_HABITACION") + ", " +
                    "Número de Cliente: " + r.getString("NUM_CLIENTE") + ", " +
                    "Fecha de Llegada: " + r.getString("FECHA_LLEGADA") + ", " +
                    "Fecha de Salida: " + r.getString("FECHA_SALIDA"));
        }
    }

    static void actualizarReserva(Statement s) throws SQLException {
        String update = "UPDATE Reservas SET NUM_CLIENTE = '001' WHERE FECHA_LLEGADA = '13-05-2023'";
        s.executeUpdate(update);
    }

    static void eliminarReserva(Statement s) throws SQLException {
        String sql = "DELETE FROM Reservas WHERE NUM_CLIENTE = '001'";
        s.executeUpdate(sql);
    }

    static void insertarReserva(Statement s) throws SQLException {
        String insert = "INSERT INTO Reservas (NUM_HABITACION, NUM_CLIENTE, FECHA_LLEGADA, FECHA_SALIDA) " +
                "VALUES ('', '7', '14-05-2023', '24-05-2023')";
        s.executeUpdate(insert);
    }
}
