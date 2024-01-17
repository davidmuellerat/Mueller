import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        while(true) {
            System.out.println("Was wollen Sie machen?");
            System.out.println("################################################################");
            System.out.println("Kunden anlegen [a], Artikel anlegen [b], Bestellen [c], Bestellung ansehen [d],Lagerbestand aktualisieren [e], Bestellung aktualisieren [f] ,Bestellung löschen [g], Beenden [h]");
            System.out.println("################################################################");

            String input = scanner.next();
            switch(input){
                case "a":
                    kundeAnlegen();
                case "b":
                    artikelAnlegen();
                case "c":
                    bestellungErstellen();
                case "d":
                    bestellungAnsehen();
                case "e":
                    lagerBestandAktualisieren();
                case "f":
                    aktualisiereBestellung();
                case "g":
                    loescheBestellung();
                case "h":
                    zeigeLager();
                case "i":
                    System.exit(0);
            }
        }
    }


    private static void aktualisiereBestellung() throws SQLException, ClassNotFoundException {
        System.out.println("Bestellung ID für Update:");
        int ordernumber = scanner.nextInt();
        System.out.println("Neue Anzahl:");
        int neueAnzahl = scanner.nextInt();
        Bestellung.updateOrder(ordernumber, neueAnzahl);
        main(new String[]{"0"});
    }

    private static void loescheBestellung() throws SQLException, ClassNotFoundException {
        System.out.println("Bestellung ID für Löschung:");
        int ordernumber = scanner.nextInt();
        Bestellung.deleteOrder(ordernumber);
        main(new String[]{"0"});
    }

    private static void lagerBestandAktualisieren() throws SQLException, ClassNotFoundException {
        Artikel.show();
        System.out.println("Artikel ID wählen:");
        int aid = scanner.nextInt();
        System.out.println("Neuer Lagerbestand:");
        int neuerBestand = scanner.nextInt();
        Artikel.aktualisiereLager(aid, neuerBestand);
        main(new String[]{"0"});
    }
    private static void bestellungAnsehen() throws SQLException, ClassNotFoundException {
        Kunde.show();
        System.out.println("Kunden ID: ");
        Bestellung.show(scanner.nextInt());
        main(new String[]{"0"});
    }

    private static void zeigeLager() throws SQLException {
        System.out.println("Aritkel ID: ");
        int aid = scanner.nextInt();
        Artikel.showInventory(aid);
    }
    private static void bestellungErstellen() throws SQLException, ClassNotFoundException {
        Kunde.show();
        System.out.println("Kunde ID wählen:");
        int kid = scanner.nextInt();
        Artikel.show();
        System.out.println("Artikel ID wählen:");
        int aid = scanner.nextInt();
        System.out.println("Anzahl:");
        int anzahl = scanner.nextInt();
        if(Artikel.checkInventory(aid, anzahl)) {
            Bestellung bestellung = new Bestellung(kid, aid, anzahl);
            Artikel.aktualisiereLager(aid, -anzahl);
        } else {
            System.out.println("Fehler: Nicht genügend Lagerbestand vorhanden!");
            main(new String[]{"0"});
        }

        main(new String[]{"0"});
    }


    private static void artikelAnlegen() throws SQLException, ClassNotFoundException {
        System.out.println("Bezeichnung:");
        String bez = scanner.next();
        System.out.println("Preis:");
        Double d = scanner.nextDouble();
        Artikel artikel = new Artikel(bez, d);
        main(new String[]{"0"});
    }

    private static void kundeAnlegen() throws SQLException, ClassNotFoundException {
        System.out.println("Name:");
        String name = scanner.next();
        System.out.println("Email:");
        String email = scanner.next();
        Kunde kunde = new Kunde(name, email);
        main(new String[]{"0"});
    }
}