package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3309/easycode?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false";
    private String dbUser = "root";
    private String dbPassword = "123456";
    
    public User checkLogin(String correo, String password) throws SQLException,
            ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
        String sql = "SELECT * FROM usuarios WHERE correo = ? and password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, correo);
        statement.setString(2, password);
 
        ResultSet result = statement.executeQuery();
 
        User user = null;
 
        if (result.next()) {
            user = new User();
            user.setNombre(result.getString("nombre"));
            user.setApellidos(result.getString("apellidos"));
            user.setCorreo(correo);
        }
 
        connection.close();
 
        return user;
    }
    
    public User registrarUsuario(String nombre, String apellidos, String correo, String password) throws 
            ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
        String sql = "INSERT INTO usuarios(nombre, apellidos, correo, password) values (?, ?, ?, MD5(?))";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, nombre);
        statement.setString(2, apellidos);
        statement.setString(3, correo);
        statement.setString(4, password);
 
        int insert = statement.executeUpdate();
        User user = null;
        
        if (insert > 0) {
            user = new User();
            user.setNombre(nombre);
            user.setApellidos(apellidos);
            user.setCorreo(correo);
        } 
 
        connection.close();
 
        return user;
    }
}
