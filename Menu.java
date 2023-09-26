import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        String dbUrl = "jdbc:postgresql://localhost:5432/HOTEL";
        String usuario = "postgres";
        String contrasena = "123456789";

        try {
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager.getConnection(dbUrl, usuario, contrasena);
            Statement s = c.createStatement();

            Scanner sc = new Scanner(System.in);

            int opcion;
            while (true) {
                System.out.println("1. Habitaciones");
                System.out.println("2. Clientes");
                System.out.println("3. Reservas");
                System.out.println("4. Salir");
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1:
                        Habitaciones.main(args);
                        break;
                    case 2:
                        Clientes.main(args);
                        break;
                    case 3:
                        Reservas.main(args);
                        break;
                    case 4:
                        s.close();
                        System.out.println("Saliendo del programa.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, ingrese otra opción");
                }
            }
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error.");
        }
    }
}
