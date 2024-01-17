import java.sql.*;

public class Artikel {
    private static Connection connect = null;
    private static Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private String bezeichnung;
    private double preis;

    public Artikel(String bez, double p) throws SQLException, ClassNotFoundException {
        this.bezeichnung = bez;
        this.preis = p;
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/kundenxartikel?", "root", "27dave07");
            String query = "INSERT INTO artikel(bezeichnung, preis) VALUES (?,"+this.preis+")";
            PreparedStatement preparedStmt = connect.prepareStatement(query);
            preparedStmt.setString(1, this.bezeichnung);
            preparedStmt.executeUpdate();
          /*  resultSet = statement
                    .executeQuery("select * from oddOrEven");
            writeResultSet(resultSet);*/

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
        connect.close();

    }

    public static boolean checkInventory(int artikelId, int anzahl) throws SQLException, ClassNotFoundException {
        String sql = "SELECT lagerbestand FROM lager WHERE artikel_id = ?";
        int lagerbestand = 0;
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager
                .getConnection("jdbc:mysql://localhost/kundenxartikel?", "root", "27dave07");

        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setInt(1, artikelId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    lagerbestand = rs.getInt("lagerbestand");
                }
            }
        }

        return lagerbestand >= anzahl;
    }

    public static void aktualisiereLager(int artikelId, int neueMenge) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager
                .getConnection("jdbc:mysql://localhost/kundenxartikel?", "root", "27dave07");
        String sql = "UPDATE lager SET lagerbestand = lagerbestand + ? WHERE artikel_id = ?";
        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setInt(1, neueMenge);
            pstmt.setInt(2, artikelId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        connect.close();
    }

    public static void show() throws SQLException {
        Statement statement = null;
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/kundenxartikel?", "root", "27dave07");
            statement = connect.createStatement();
            String a2 = "SELECT * FROM artikel";
            ResultSet resultSet2 = statement.executeQuery(a2);
            System.out.println("Artikel-Tabelle:");
            while (resultSet2.next())
            {
                int idartikel = resultSet2.getInt("ID");
                String artikelBezeichnung = resultSet2.getString("bezeichnung");
                double artikelpreis = resultSet2.getDouble("preis");
                System.out.println("ArtikelID: " + idartikel+ ", Bezeichnung: " + artikelBezeichnung + ", preis: "+ artikelpreis);
            }
        }catch (NumberFormatException | SQLException e) {
            System.out.println("Fehler: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
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

    public static void showInventory(int aid) throws SQLException {
        Connection connect = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/kundenxartikel?", "root", "27dave07");
            statement = connect.createStatement();
            String a1 = "SELECT * FROM bestellungen where aid ="+aid;
            String a2 = "SELECT * FROM artikel where id ="+aid;
            ResultSet resultSet = statement.executeQuery(a1);
            System.out.println("Lager:");
            ResultSet rs1 = statement.executeQuery(a2);
            while (resultSet.next())
            {
                System.out.println("Artikel: " + rs1.getString(0) + ", bestand: "+resultSet.getInt(0));
            }
        } catch (NumberFormatException | SQLException | ClassNotFoundException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
        connect.close();
    }
}