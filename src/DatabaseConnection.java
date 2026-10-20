import java.sql.*;

public class DatabaseConnection {
    private static final String URL ="jdbc:mysql://localhost:3306/Blood_bank_management_system";
    private static final String USER = "root";
    private static final String PASSWORD = "ishit@299";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            System.out.println("Connected to Blood_bank_management_system!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}