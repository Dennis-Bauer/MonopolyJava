package de.sandwich.Enums;

public enum CommunityCards {

    //Extra
    FREE_JAIL("Du kommst aus dem Gefängnis frei!", -1),
    TO_START("Rücke vor bis auf Los.", -2),
    GO_TO_JAIL("Gehe in das Gefängins!", -3),
    BIRTHDAY("Es ist dein Geburtstag. Ziehe von jedem Spieler (+1000) ein.", -4),
    HOUSES_MONEY("Du wirst zu Strassenausbesserungsarbeiteb herangezogen. Zahle für deine Häuse und Hotels (+800) je Haus und (+2300) je Hotel and die Bank", -5),

    //Normal
    PAY_HOSPITAL("Zahle an das Krankenhaus (-2000).", -2000),
    STORAGE_GET_MONEY("Aus Lagerverkäufen erhälst du (+5000).", 5000),
    SCHOOL_MONEY("Zahle Schulgeld. (-3000)", -3000),
    SHARE_MONEY("Du erhältst auf Vorzugs-Aktien 7% Dividende. (+900)", 900),
    PENSION_MONEY("Deine Jahresrente wird fällig. (+2000)", 2000),
    BANK_MISTAK("Bank-Irrtum zu deinem Gunsten. (+4000)", 4000),
    COMPOTION_WON("Du hast in einem Kreuzworträtsel-Wettbewerb gewonnen. (+2000)", 2000),
    DOCTOR_COST("Artzt-Kosten. (-1000)", -1000),
    TAX_REPAYMENT("Einkommensteuer-Rückzahlung. (+400)", 400),
    HERITAGE("Du erbst. (+2000)", 2000),
    BEAUTIFUL("Du hast den 2. Preis in einer Schönheitskonkurrenz gewonnen. (+200)", 200);


    private final String MESSAGE;
    private final int MONEY_TRANSFER;

    CommunityCards(String m, int moneyTransfer) {
        this.MESSAGE = m;

        switch (moneyTransfer) {
            case -1 -> {
                //Komm aus dem Gefängins
            }
            case -2 -> {
                //Geh zu los
            }
            case -3 -> {
                //Ins gefängnis
            }
            case -4 -> {
                //Jeder Spieler muss dem Activen Spieler 1000 bekommen
            }
            case -5 -> {
                //Zahle je Haus 800 und je hotel 2300
            }
        }
        this.MONEY_TRANSFER = moneyTransfer;
    }

    public int getMoneyTransfer() {
        if (!(MONEY_TRANSFER <= -1 && MONEY_TRANSFER >= -5))
            return MONEY_TRANSFER;
        else throw new NullPointerException("The program tried to access money transfer on a card that don't have any.");
    }

    public void getAction() {
        
    }

}
