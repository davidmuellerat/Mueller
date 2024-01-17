import java.sql.*;

public class Bestellung {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private int aid;
    private int kid;
    private int anzahl;

    public Bestellung(int kid, int aid, int anz) throws SQLException, ClassNotFoundException {
        this.aid = aid;
        this.kid = kid;
        this.anzahl = anz;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/kundenxartikel?", "root", "27dave07");
            statement = connect.createStatement();
            String query = "INSERT INTO bestellungen(kid, aid, anzahl) VALUES (?,?,?)";
            PreparedStatement preparedStmt = connect.prepareStatement(query);
            preparedStmt.setInt(1, this.kid);
            preparedStmt.setInt(2, this.aid);
            preparedStmt.setInt(3,this.anzahl);
            preparedStmt.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        connect.close();
    }
    public static void show(int i) throws SQLException {
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
        connect.close();
    }

    public static void updateOrder(int ordernumber, int neueAnzahl) throws SQLException, ClassNotFoundException {
        Connection connect = null;
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager
                .getConnection("jdbc:mysql://localhost/kundenxartikel?", "root", "27dave07");
        String sql = "UPDATE bestellungen SET anzahl = ? WHERE ordernumber = ?";
        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setInt(1, neueAnzahl);
            pstmt.setInt(2, ordernumber);
            pstmt.executeUpdate();
        }
        connect.close();
    }

    public static void deleteOrder(int ordernumber) throws SQLException, ClassNotFoundException {
        Connection connect = null;
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager
                .getConnection("jdbc:mysql://localhost/kundenxartikel?", "root", "27dave07");

        String sql = "DELETE FROM bestellungen WHERE ordernumber = ?";
        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setInt(1, ordernumber);
            pstmt.executeUpdate();
        }
        connect.close();
    }
}
