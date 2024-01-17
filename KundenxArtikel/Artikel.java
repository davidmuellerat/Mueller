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
            // Setup the connection with the DB
           /* System.out.println("Password:");
            String password = scanner.nextLine();*/
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

    }

    public static void show(){
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