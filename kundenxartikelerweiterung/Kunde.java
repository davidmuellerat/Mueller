import java.sql.*;
import java.util.Random;

public class Kunde {
    private static Connection connect = null;
    private static Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private String name;
    private String email;
    public Kunde(String name, String email) throws SQLException, ClassNotFoundException {
        this.name = name;
        this.email = email;
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
           /* System.out.println("Password:");
            String password = scanner.nextLine();*/
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/kundenxartikel?", "root", "27dave07");
            System.out.println(connect);
            statement = connect.createStatement();
            String query = "INSERT INTO kunden(name, email) VALUES (?,?)";
            PreparedStatement preparedStmt = connect.prepareStatement(query);
            preparedStmt.setString(1, this.name);
            preparedStmt.setString(2, this.email);
            preparedStmt.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
        connect.close();
    }

    public static void show() throws SQLException {
         Statement statement = null;
         Connection connect = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/kundenxartikel?", "root", "27dave07");
            statement = connect.createStatement();
            String a1 = "SELECT * FROM kunden";
            ResultSet resultSet = statement.executeQuery(a1);
            System.out.println("Kunden-Tabelle:");
            while (resultSet.next())
            {
                int id = resultSet.getInt("ID");
                String kundenName = resultSet.getString("name");
                String kundenEmail = resultSet.getString("email");
                System.out.println("KundenID: " + id + ", Name: " + kundenName + ", Email: "+ kundenEmail);
            }
    } catch (NumberFormatException | SQLException | ClassNotFoundException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
        finally {
            close();
        }
        connect.close();

    }
    private static void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

}
