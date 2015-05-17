package controlador;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adrian
 */
import java.sql.*;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
public class ControlDB {
    private Connection conexion;
    private Statement sentencia;
    private ResultSet resultado;
    private final String db = "tpv";
    private String server = "localhost";
    private final String url = "jdbc:mysql://"+server+"/"+db;
    private final String user = "root";
    private final String pass = "";
    public ControlDB(){
    }
    public void cargarDriver(){
        try{
            Class.forName("org.gjt.mm.mysql.Driver");
        }
        catch(ClassNotFoundException evt){
            System.out.println("No se pudo cargar Driver JDBC-ODBC");
        }
        System.out.println("Si se pudo cargar correctamente Driver JDBC-ODBC");
    }
    public void cargarDriver(String driver){
        try{
            Class.forName(driver);
        }
        catch(ClassNotFoundException evt){
            System.out.println("No se pudo cargar Driver JDBC");
        }
        System.out.println("Si se pudo cargar correctamente Driver JDBC");
    }
    public void conectar(){/*de esta forma es un metodo mas generico para cualquier base d*/
        conexion=null;
        try{
            
            conexion=DriverManager.getConnection("jdbc:mysql://"+server+"/"+db,user,pass);
            System.out.println("Se pudo conectar correctamente con la Base De Datos: " + db);
        }
        catch(SQLException e){
            JOptionPane op = new JOptionPane("No se pudo conectar a la base de datos", JOptionPane.ERROR_MESSAGE);
            JDialog dialog = op.createDialog("Error");
            dialog.setAlwaysOnTop(true);
            dialog.setModal(true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        }
        
        //return conexion; /*Devuelve el Objeto conexion con el que realizare el resto de tareas*/
    }
   
    public ResultSet ejecutarSelect(String sent){
        resultado=null;
        try{
            sentencia=conexion.createStatement();
            resultado=sentencia.executeQuery(sent);
        }
        catch(SQLException e){return null;}

        return resultado;
    }
    public int ejecutarDelete(String sent){
        int r=0;
        try{
            sentencia=conexion.createStatement();
            r=sentencia.executeUpdate(sent);
        }
        catch(SQLException e){return -1;}
        
        return r;
    }
    public int ejecutarUpdate(String sent){
        int r=0;
        try{
            sentencia=conexion.createStatement();
            r=sentencia.executeUpdate(sent);
        }
        catch(SQLException e){return -1;}
        return r;
    }
    public int ejecutarInsert(String sent){
        int r=0;
        try{
            sentencia=conexion.createStatement();
            r=sentencia.executeUpdate(sent);
        }
        catch(SQLException e){return -1;}

        return r;
    }

    public void setServer(String server) {
        this.server = server;
    }
    
    public String getServer() {
        return server;
    }
    
    public Connection getConexion() {
        return conexion;
    }
}
