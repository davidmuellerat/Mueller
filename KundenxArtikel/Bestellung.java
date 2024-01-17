import java.sql.*;

import static jdk.internal.net.http.common.Utils.close;

public class Bestellung {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private int aid;
    private int kid;
    private int anzahl;

    public Bestellung(int aid, int kid, int anz) throws SQLException, ClassNotFoundException {
        this.aid = aid;
        this.kid = kid;
        this.anzahl = anz;
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
           /* System.out.println("Password:");
            String password = scanner.nextLine();*/
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/kundenxartikel?", "root", "27dave07");
            statement = connect.createStatement();
            String query = "INSERT INTO bestellungen(kid, aid, anzahl) VALUES ("+this.kid+", "+this.aid+","+ this.anzahl+")";
            statement.executeUpdate(query);
          /*  resultSet = statement
                    .executeQuery("select * from oddOrEven");
            writeResultSet(resultSet);*/

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void show(int i) {
        Statement statement = null;
        Connection connect = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/kundenxartikel?", "root", "27dave07");
            statement = connect.createStatement();
            String a1 = "SELECT * FROM bestellungen where kid ="+i;
            ResultSet resultSet = statement.executeQuery(a1);
            System.out.println("Bestellungen:");
            while (resultSet.next())
            {
                int id = resultSet.getInt("OrderNumber");
                int kid = resultSet.getInt("kid");
                int aid = resultSet.getInt("aid");
                System.out.println("orderNumber: " + id + ", kid: " + kid + ", aid: "+ aid);
            }
        } catch (NumberFormatException | SQLException | ClassNotFoundException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }
}
