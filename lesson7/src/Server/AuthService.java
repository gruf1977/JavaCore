package Server;

import java.sql.*;

public class AuthService {

    private static Connection connection;
    private static Statement stmt;




public static void connect(){
    try {
        Class.forName("org.sqlite.JDBC");
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");
            stmt = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
}
public static String getNickByLoginByPasswd(String login, String passwd ) throws SQLException {
    String qry = String.format("SELECT nickname FROM main where login='%s' and password='%s'", login, passwd);
    ResultSet rs  = stmt.executeQuery(qry);
        if (rs.next()) {
            return rs.getString(1);

        }

    return null;
}
public static  void disconnect(){
    try {
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}