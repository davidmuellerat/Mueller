import java.sql.*;
import java.util.Random;

public class SQLiteJDBC {

    public static void main(String args[]) {
        Connection c = null;
        Statement stmt = null;
        final String url = "jdbc:sqlite:/Users/davidmueller/Documents/sqlite-tools-osx-x86-3430100/test.db";
        Random rdm = new Random();
        int evenCount = 0;
        int oddCount = 0;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);
            System.out.println("Opened the database successfully");
            stmt = c.createStatement();
            String dropTableSQL = "DROP TABLE if exists uebung;";
            stmt.executeUpdate(dropTableSQL);
            String createTableSQL = "CREATE TABLE uebung" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " value1 INT," +
                    " value2 INT);";
            stmt.executeUpdate(createTableSQL);
            for (int i = 0; i < 20; i++) {
                int value1 = rdm.nextInt(10) + 1;
                int value2 = value1 % 2;
                String insertDataSQL = "INSERT INTO uebung (value1, value2) VALUES (" + value1 + ", " + value2 + ");";
                stmt.executeUpdate(insertDataSQL);
                if (value2 == 0) {
                    evenCount++;
                } else {
                    oddCount++;
                }
            }

            String selectSQL = "SELECT * from uebung";
            ResultSet rs = stmt.executeQuery(selectSQL);
            System.out.println("ID\tValue1\tValue2");
            while (rs.next()) {
                int id = rs.getInt("ID");
                int value1 = rs.getInt("value1");
                int value2 = rs.getInt("value2");
                System.out.println(id + "\t" + value1 + "\t" + value2);
            }
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created and data inserted successfully");
        System.out.println("Number of even values: " + evenCount);
        System.out.println("Number of odd values: " + oddCount);
    }
}
