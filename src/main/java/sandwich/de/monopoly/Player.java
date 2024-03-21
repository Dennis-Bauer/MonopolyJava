package sandwich.de.monopoly;

import sandwich.de.monopoly.Enums.Figuren;

public class Player {

    private int kontoStand;
    private String name;
    private int fieldPostion;
    private final int orderNumber;
    Figuren figur;

    public Player(String name, Figuren f, int orderNumber) {
        this.name = name;
        this.figur = f;
        this.orderNumber = orderNumber;

        kontoStand = Main.START_BANK_ACCOUNT;
        fieldPostion = 20;
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
        fieldPostion = fieldPostion + postions;
        if (fieldPostion >= 40)
            fieldPostion = fieldPostion - 40;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Figure: " + figur.toString().toLowerCase() + ", FieldPosition: " + fieldPostion + ", Kontostand: " + kontoStand;
    }

}
