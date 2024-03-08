package sandwich.de.monopoly;

import sandwich.de.monopoly.Enums.Figuren;

public class Spieler {

    private int kontoStand;
    private String name;
    private int fieldPostion;
    Figuren figur;

    public Spieler(String name, Figuren f) {
        this.name = name;
        this.figur = f;

        kontoStand = Main.START_BANK_ACCOUNT;
        fieldPostion = 1;
    }

    public String getName() {
        return name;
    }

    public int getKontoStand() {
        return kontoStand;
    }

    public Figuren getFigur() {
        return figur;
    }


    @Override
    public String toString() {
        return "Name: " + name + ", Figure: " + figur.toString().toLowerCase() + ", FieldPosition: " + fieldPostion + ", Kontostand: " + kontoStand;
    }

}
