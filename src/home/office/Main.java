package home.office;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Harcos mainCharacter;
    static List<Harcos> harcosok = new ArrayList();

    public static void init() {
        harcosok.add(new Harcos("Plakton", 1));
        harcosok.add(new Harcos("SpongeBob", 1));
        harcosok.add(new Harcos("Garry", 1));
        try {
            File myObj = new File("src/home/office/harcosok.csv");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] temp = myReader.nextLine().split(";");
                harcosok.add(new Harcos(temp[0], Integer.parseInt(temp[1])));
            }
            myReader.close();
            System.out.println("Sikeres fájlbeolvasás.");
        } catch (FileNotFoundException e) {
            System.out.println("Nincs meg a fájl");
        }
        System.out.println("Add meg a játékosneved");
        String nev = scanner.next();
        boolean nemok = true;
        int szam = 0;
        while (nemok) {
            System.out.println("Harcos kategóriák:\n" +
                    "1: Alap Életerő = 15, Alap Sebzés = 3\n" +
                    "2: Alap Életerő = 12, Alap Sebzés = 4\n" +
                    "3: Alap Életerő = 8, Alap Sebzés = 5\n\n" +
                    "Add meg a harcos kategóriád:");
            try {
                szam = Integer.parseInt(scanner.next());
                if (szam > 3 || szam < 1) {
                    throw new Exception();
                }
                nemok = false;
            } catch (Exception e) {
                System.out.println("Hiba (" + e + ")");
            }
        }
        mainCharacter = new Harcos(nev, szam);
    }

    public static int valaszBeKero() {
        boolean nincsvalasz = true;
        int ki = 0;
        while (nincsvalasz) {
            System.out.println(mainCharacter + "\n");
            System.out.println("Menü:\n\ta) Megküzdeni egy harcossal\n\tb) Gyógyulni\n\tc) Kilépni");
            String valasz = (scanner.next()).strip();
            nincsvalasz = false;
            switch (valasz) {
                case "a":
                    ki = 1;
                    break;
                case "b":
                    ki = 2;
                    break;
                case "c":
                    ki = 3;
                    break;
                default:
                    nincsvalasz = true;
                    break;
            }
        }
        return ki;
    }

    public static void harc() {
        int szam = 0;
        boolean nemok = true;
        while (nemok) {
            try {
                for (int i = 0; i < harcosok.size(); i++) {
                    System.out.println((i + 1) + ": " + harcosok.get(i));
                }
                System.out.println("Válaszd ki ellenfeled sorszámát:");
                szam = Integer.parseInt(scanner.next());
                if (szam > harcosok.size() - 1 || szam < 0) {
                    throw new Exception();
                }
                nemok = false;
            } catch (Exception e) {
                System.out.println("Hiba (" + e + ")");
            }
        }
        mainCharacter.megkuzd(harcosok.get(szam - 1));
    }

    public static void jatek() {
        boolean marad = true;
        int korszamolo = 0;
        while (marad) {
            korszamolo++;
            if (korszamolo == 3) {
                System.out.println("Elérkezett a harmadik kör, itt harc lessz...");
                int index = (int) (Math.random() * (harcosok.size()));
                System.out.println("Ellenfeled: "+ harcosok.get(index));
                mainCharacter.megkuzd(harcosok.get(index));
                for (Harcos katona : harcosok) {
                    katona.gyogyul();
                }
                System.out.println("Minden ellenfeled gyógyult");
                korszamolo = 0;
            } else {
                int valasz = valaszBeKero();
                switch (valasz) {
                    case 1:
                        harc();
                        break;
                    case 2:
                        mainCharacter.gyogyul();
                        System.out.println("Gyógyulás");
                        break;
                    default:
                        marad = false;
                }
            }
        }
        System.out.println("Kilépve, szép játék volt!");
    }

    public static void main(String[] args) {
        init();
        jatek();
    }
}
