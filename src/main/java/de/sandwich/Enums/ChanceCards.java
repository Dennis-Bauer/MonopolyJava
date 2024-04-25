package de.sandwich.Enums;

public enum ChanceCards {


    //Extra
    FREE_JAIL("Du kommst aus dem Gefängnis Frei"),
    GO_TO_JAIL("Gehe ins Gefängnis, JETZT!"),
    PAY_FINE("Zahle eine Strafe von -(200) oder nimm eine Gemeinschaftskarte."),
    TO_SOUTH_STATION("Mache einen Ausflug nach dem Süd-Bahnhof. Wenn du über Los kommst, bekommst du +(2000)!"),
    GO_3_BACK("Gehe 3 Felder zurück"),
    GO_TO_START("Rücke bis auf Los vor."),
    TO_FAST("Strafe für zu schnelles Fahren. (-300)"),
    GO_TO_STREET_1("Rücke vor bis zu NAME_VON_FELT."),
    GO_TO_STREET_2("Rücke vor bis zu NAME_VON_FELT."),
    GO_TO_STREET_3("Rücke vor bis zu NAME_VON_FELT."),
    GO_TO_STREET_4("Rücke vor bis zu NAME_VON_FELT."),
    GO_TO_NEXT_STATION("Rücke vor bis zum nächsten Bahnhof. Der Eigentümer erhält das doppelte der normalen Miete. Hat noch kein Spieler einen Besitzanspruch auf diesen Bahnhof, so kann er ihn von der Bank kaufen."),

    //Normal
    YOU_GOT_CHOSED("Du wurdest zum Vorstand gewählt. Zahle jedem Spieler (-1000)!"),
    HOUSES_RENOVATE("Du lässt alle deine Häuser renoviren. Zahle an die Bank (-500) je Haus und (-2000) je Hotel!"),
    BANK_MONEY_1("Die Bank zahlt dir eine Dividende von (+1000)."),
    BANK_MONEY_2("Mite und Anleihezinsen werden fällig. Die Bank zahlt dir (+3000)");

    private final String MESSAGE;

    ChanceCards(String m) {
        this.MESSAGE = m;
    }

}
