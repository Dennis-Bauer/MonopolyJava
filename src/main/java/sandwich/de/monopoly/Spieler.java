package sandwich.de.monopoly;

import sandwich.de.monopoly.Enums.Figuren;

public class Spieler {

    private double konto;
    private int reihenfolge;
    private String name;
    private Figuren figur;

    public Spieler(double konto,int reihenfolge,String name,Figuren figur) {
        this.konto = konto;
        this.reihenfolge = reihenfolge;
        this.name = name;
        this.figur = figur;
    }

    public void aenderKonto(double x) {
        konto = konto + x;
    }

    public double getKonto() {
        return this.konto;
    }

    public int getReihenfolge() {
        return this.reihenfolge;
    }
    public String getName() {
        return this.name;
    }
    public Figuren getFigur() {
        return this.figur;
    }
}
