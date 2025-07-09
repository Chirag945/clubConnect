package clubConnect.util;
import java.sql.*;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/clubConnect";
        String user = "root";
        String password = "root";

        return DriverManager.getConnection(url, user, password);
    }
}

