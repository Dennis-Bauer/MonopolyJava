package sandwich.de.monopoly;

public class Strasse {

    private String name;
    private int kaufpreis;
    private int position;
    private int mietkosten;
    private boolean istGekauft;
    private Spieler besitzer;

    public Strasse(String name, int kaufpreis, int position, int mietkosten, boolean istGekauft){
        this.name = name;
        this.kaufpreis = kaufpreis;
        this.position = position;
        this.mietkosten = mietkosten;
        this.istGekauft = istGekauft;
    }

    public void kaufen (Spieler Spieler){
        istGekauft = true;
        besitzer = Spieler;
        // Geld abziehen und grundstueck spieler zuweisen
    }
    public void zahlenMiete(Spieler zahlenderSpieler){
        // Zahlender Spieler Zahlt miete ( ZahlenderSpieler pay mietkosten Spieler )
        //if zahlenderSpieler.aenderKonto(mietkosten);
    }
    public String getName(){ return this.name;}
    public int getKaufpreis(){ return this.kaufpreis;}
    public int getPosition(){ return this.position;}
    public int getMietkosten(){ return this.mietkosten;}
    public boolean getIstGekauft(){ return this.istGekauft;}
    public Spieler getBesitzer(){ return besitzer;}
}
