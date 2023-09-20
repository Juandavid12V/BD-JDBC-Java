


import java.awt.*;
import java.sql.*;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class buscarGUI extends JApplet {

private static final long serialVersionUID = 1L;

String dbUrl =
"jdbc:postgresql://localhost/gente";

String usuario = "postgres";

String contrasena = "postgres";

Statement s;

JTextField buscarPor = new JTextField (20) ;

JLabel conclusion = new JLabel(" " );

JTextArea resultados = new JTextArea(40, 20);


public void init() {

buscarPor.getDocument().addDocumentListener(new BuscarL( ));

JPanel p = new JPanel() ;

p.add(new Label ("Apellido a buscar: " ) ) ;

p.add(buscarPor) ;

p.add(conclusion) ;

Container cp = getContentPane ( ) ;

cp.add(p, BorderLayout . NORTH) ;

cp.add(resultados, BorderLayout.CENTER);

try {

// Cargar el controlador (se registra automáticamente)

Class.forName("org.postgresql.Driver");

Connection c = DriverManager.getConnection(dbUrl, usuario, contrasena);

s= c.createStatement ( ) ;

} catch(Exception e) {

resultados.setText(e.toString());

}//catch

}//init


/************ clase BuscarL , incluida en BuscarGUI************/

class BuscarL implements DocumentListener {

public void changedUpdate(DocumentEvent e){ }

public void insertUpdate(DocumentEvent e){

valorTextoCambiado();

}//insertUpdate

public void removeUpdate(DocumentEvent e){

valorTextoCambiado();

}//removeUpdate

}//BuscarL|


public void valorTextoCambiado() {

ResultSet r;

if(buscarPor.getText().length()== 0) {

conclusion.setText(" ") ;

resultados.setText(" ") ;

return;

}

try {

//Finalización de nombres :

r = s.executeQuery (

"SELECT APELLIDO FROM gente " +

"WHERE (APELLIDO Like '" +buscarPor.getText( )+ "')ORDER BY APELLIDO");



if(r.next( ))

conclusion.setText(r.getString("Apellido")) ;

r =s.executeQuery("SELECT NOMBRE, APELLIDO, CORREO "+

"FROM gente " +

"WHERE " +

"(APELLIDO= '" + conclusion.getText( )+ "')" +

" AND (CORREO Is Not Null) "

+"ORDER BY NOMBRE") ;

} catch(Exception e) {

resultados.setText(buscarPor.getText()+ "\n");

resultados.append(e.toString());

return;

}



resultados.setText ("") ;

try{

while(r.next ( )){

resultados.append(

r.getString ("NOMBRE") + ", "

+ r.getString ("APELLIDO") +

": " + r.getString ("CORREO") + "\n") ;

}//while

}catch(Exception e) {

resultados.setText(e.toString());

}//catch

}//valorTextoCambiado



public static void main(String [] args){

new buscarGUI();

}//main

}//BuscarGUI