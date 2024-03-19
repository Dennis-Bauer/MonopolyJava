package sandwich.de.monopoly;

import sandwich.de.monopoly.Enums.Figuren;

public class Player {

    private int kontoStand;
    private String name;
    private int fieldPostion;
    Figuren figur;

    public Player(String name, Figuren f) {
        this.name = name;
        this.figur = f;

        kontoStand = Main.START_BANK_ACCOUNT;
        fieldPostion = 0;
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

    public int getFieldPostion() {
        return fieldPostion;
    }

    public void moveFieldPostion(int postions) {
        System.out.println("Übergebende Menge wie viel der Spieler sich bewegen soll: " + postions);
        System.out.println("Welche Postion er bekommen soll: " +  (fieldPostion + postions));
        System.out.println("Seine jetztige Postion: " + fieldPostion);
        fieldPostion = fieldPostion + postions;
        if (((fieldPostion - postions) + postions) >= 40)
            fieldPostion = fieldPostion - 40;
        System.out.println("Seine neue Postion: " + fieldPostion);
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Figure: " + figur.toString().toLowerCase() + ", FieldPosition: " + fieldPostion + ", Kontostand: " + kontoStand;
    }

}
