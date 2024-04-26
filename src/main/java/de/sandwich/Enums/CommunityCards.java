package de.sandwich.Enums;

import static de.sandwich.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;

public enum CommunityCards {

    //Extra
    FREE_JAIL(buildLongText("Du bekommst eine", "Gefängnis frei Karte. Behalte", "sie und nutze sie", "beim nächsten mal."), -1),
    TO_START("Rücke vor bis auf Los.", -2),
    GO_TO_JAIL("Du wurdest festgenommen!", -3),
    BIRTHDAY(buildLongText("Du hast heute Gebursttag.", "Bekomme von jedem Spieler", "1000€!"), -4), //Fixed number (1000)
    HOUSES_MONEY(buildLongText("Deine Straßen werden Renoviert.", "Zahle für jedes Haus 800€", "und für jedes Hotel 2300€"), -5), //Fixed number (800,2300)

    //Normal
    PAY_PENIS(buildLongText("Du musst für eine Penis", "verlängerung zahlen!"), -2000),
    CARDS_GET_MONEY(buildLongText("Du verkaufst deine", "Minecraft Karten."), 5000),
    PUFF_MONEY(buildLongText("Du musst deine Puf-Schulden", "zahlen!"), -3000),
    SHARE_MONEY(buildLongText("Du erhältst auf",  "Apple-aktien 7% Dividende."), 900),
    CLUB_MONEY(buildLongText("Deine Jahres abo beim", "Golf-Club wird fällig."), -2000),
    BANK_HEIST(buildLongText("Du machst an einem", "Bankraub mit."), 4000),
    COMPOTION_WON(buildLongText("Du hast in einem", "Tanenbaum-weitwurf-Wettbewerb", "gewonnen."), 2000),
    FRIEND_COST("Collin-Aldi-Kosten.", -1000),
    TAX_REPAYMENT("Du begehst Steuerhinterziehung.", 400),
    HERITAGE("Mumy died.", 2000),
    IQ(buildLongText("Du hast den 20. Preis in", "einem IQ Wettbewerb gewonnen."), 200);

    private final String MESSAGE;
    private final int MONEY_TRANSFER;

    CommunityCards(String m, int moneyTransfer) {
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
