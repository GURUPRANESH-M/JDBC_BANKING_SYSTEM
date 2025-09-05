import java.sql.*;
public class DB {
    private static final String url = "jdbc:mysql://localhost:3306/bank";
    private static final String name = "root";
    private static final String pwd = "root";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url,name,pwd);
    }


}
