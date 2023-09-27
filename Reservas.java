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
                        buscarReservasPorNumero(s, numReserva);
                        break;
                    case 2:
                        System.out.println("Actualizar Reserva");
                        actualizarReserva(c, sc);
                        break;
                    case 3:
                        System.out.println("Eliminar Reserva");
                        eliminarReserva(c, sc);
                        break;
                    case 4:
                        System.out.println("Insertar Nueva Reserva");
                        insertarReserva(s);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Opcion no valida. Por favor, ingrese una opcion valida.");
                }
            }
    }
            // Código Reservas
            static void buscarReservasPorNumero(Statement s, String numReserva) throws SQLException {
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

            static void actualizarReserva(Connection connection, Scanner scanner) throws SQLException {
                Statement statement = connection.createStatement();
                System.out.println("Ingrese el número de reserva que desea actualizar: ");
                int numReserva = scanner.nextInt();
                scanner.nextLine();

                String seleccionar = "SELECT NUM_HABITACION FROM Reservas WHERE NUM_HABITACION = " + numReserva;
                ResultSet resultSet = statement.executeQuery(seleccionar);

                if (resultSet.next()) {
                    System.out.println("Ingrese el nuevo numero de cliente: ");
                    int nuevoCliente = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Ingrese la nueva fecha de llegada: ");
                    String nuevaFechaLlegada = scanner.nextLine();

                    System.out.println("Ingrese la nueva fecha de salida: ");
                    String nuevaFechaSalida = scanner.nextLine();

                    String updateQuery = "UPDATE Reservas SET NUM_CLIENTE = " + nuevoCliente + ", FECHA_LLEGADA = '"
                            + nuevaFechaLlegada + "', FECHA_SALIDA = '" + nuevaFechaSalida + "' WHERE NUM_HABITACION = " + numReserva;

                    statement.executeUpdate(updateQuery);
                } else {
                    System.out.println("No se encontro una reserva con el numero ingresado");
                }

                statement.close();
            }

            static void eliminarReserva(Connection connection, Scanner scanner) throws SQLException {
                Statement statement = connection.createStatement();
                System.out.println("Ingrese el número de reserva que desea eliminar: ");
                int numReserva = scanner.nextInt();
                scanner.nextLine();

                String seleccionar = "SELECT NUM_HABITACION FROM Reservas WHERE NUM_HABITACION = " + numReserva;
                ResultSet resultSet = statement.executeQuery(seleccionar);

                if (resultSet.next()) {
                    String eliminarReserva = "DELETE FROM Reservas WHERE NUM_HABITACION = " + numReserva;
                    statement.executeUpdate(eliminarReserva);
                } else {
                    System.out.println("No se encontro una reserva con el numero ingresado");
                }

                statement.close();
            }

            static void insertarReserva(Statement s) throws SQLException {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Ingrese el número de habitación: ");
                int numHabitacion = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Ingrese el numero de cliente: ");
                int numCliente = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Ingrese la fecha de llegada: ");
                String fechaLlegada = scanner.nextLine();

                System.out.println("Ingrese la fecha de salida: ");
                String fechaSalida = scanner.nextLine();

                String insert = "INSERT INTO Reservas (NUM_HABITACION, NUM_CLIENTE, FECHA_LLEGADA, FECHA_SALIDA) VALUES ("
                        + numHabitacion + ", " + numCliente + ", '" + fechaLlegada + "', '" + fechaSalida + "')";
                s.executeUpdate(insert);

             
            }

}
