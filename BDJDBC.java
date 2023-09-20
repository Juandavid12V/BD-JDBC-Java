import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

class BDJDBC{
    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection("jdbc:odbc:wombat", "login", "password");

            System.out.println("Conexión a la base de datos exitosa.");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT APELLIDO, NOMBRE, CORREO FROM gente");

            while (rs.next()) {
                int x = rs.getInt("APELLIDO");
                String s = rs.getString("NOMBRE");
                float f = rs.getFloat("CORREO");

                // Do something with x, s, and f
            }

            // Don't forget to close resources like ResultSet, Statement, and Connection when done.
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
