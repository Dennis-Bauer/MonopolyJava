package de.sandwich.Enums;

import static de.sandwich.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;

public enum ChanceCards {


    //Extra
    FREE_JAIL(buildLongText("Du bekommst eine", "Gefängnis Freikarte. Behalte", "sie und nutze sie", "beim nächsten mal."), -1),
    GO_TO_JAIL("Du wurdest festgenommen!", -2),
    GO_3_BACK("Gehe 3 Felder zurück", -3),
    GO_TO_START("Rücke bis auf Los vor.", -4),
    GO_TO_NEXT_STATION(buildLongText("Rücke vor bis zum nächsten", "Bahnhof."), -5),
    HOUSES_RENOVATE(buildLongText("Du lässt alle deine Häuser", "renoviren. Zahle an die Bank", " 500 je Haus und", "2000 je Hotel!"), -6),
    TO_SOUTH_STATION(buildLongText("Mache einen Ausflug zu", "dem Süd-Bahnhof!"), -7),
    NUDES(buildLongText("Du hast gestern jedem", "ein Nackfotot geschickt.", "Zahle jedem Spieler 1000", "als Entschuldigung!"), -8),
    GO_TO_STREET_1("39", -101),
    GO_TO_STREET_2("11", -102),
    GO_TO_STREET_3("1", -103),
    GO_TO_STREET_4("24", -104),

    //Normal
    PAY_FINE(buildLongText("Zahle eine Strafe wegen zu", "langem Penis"), -200),
    EMO_PAY(buildLongText("Strafe für zu emo", "haftes benehmen."), -300),
    BANK_MONEY_1(buildLongText("Deine onlyfans", "Karriere leuft sehr gut."), 1000),
    BANK_MONEY_2(buildLongText("Du hast jemanden für", "Geld in seinem Auto gespert."), 3000);

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
