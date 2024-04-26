package de.sandwich.Enums;

import static de.sandwich.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;

public enum ChanceCards {


    //Extra
    FREE_JAIL(buildLongText("Du bekommst eine", "Gefängnis frei Karte. Behalte", "sie und nutze sie", "beim nächsten mal."), -1),
    GO_TO_JAIL("Du wurdest festgenommen!", -2),
    GO_3_BACK("Gehe 3 Felder zurück", -3),
    GO_TO_START("Rücke bis auf Los vor.", -4),
    GO_TO_NEXT_STATION("Rücke vor bis zum nächsten Bahnhof. Hat noch kein Spieler einen Besitzanspruch auf diesen Bahnhof, so kann er ihn von der Bank kaufen.", -5),
    HOUSES_RENOVATE("Du lässt alle deine Häuser renoviren. Zahle an die Bank (-500) je Haus und (-2000) je Hotel!", -6),
    TO_SOUTH_STATION("Mache einen Ausflug nach dem Süd-Bahnhof!", -101),
    GO_TO_STREET_1("Rücke vor bis zu NAME_VON_FELT.", -102),
    GO_TO_STREET_2("Rücke vor bis zu NAME_VON_FELT.", -103),
    GO_TO_STREET_3("Rücke vor bis zu NAME_VON_FELT.", -104),
    GO_TO_STREET_4("Rücke vor bis zu NAME_VON_FELT.", -105),

    //Normal
    PAY_FINE("Zahle eine Strafe wegen zu langem Penis", -200),
    EMO_PAY("Strafe für zu emo haftes benehmen. (-300)", -300),
    YOU_GOT_CHOSED("Du hast gestern jedem ein Nackfotot geschickt. Zahle jedem Spieler!", -1000),
    BANK_MONEY_1("Deine Onlyfans Karriere leuft sehr gut.", 1000),
    BANK_MONEY_2("Du hast jemanden für Geld in seinem Auto gespert", 3000);

    private final String MESSAGE;
    private final int MONEY_TRANSFER;

    ChanceCards(String m, int moneyTransfer) {
        this.MESSAGE = m;
        this.MONEY_TRANSFER = moneyTransfer;
    }

    public int getMoneyTransfer() {
        return MONEY_TRANSFER;
    }

    public String getMessage() {
        return MESSAGE;
    }

}
