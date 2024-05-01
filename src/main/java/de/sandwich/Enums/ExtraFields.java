package de.sandwich.Enums;

import static de.sandwich.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;

public enum ExtraFields {

    SPOTIFY_PREMIUM(buildLongText("Es ist ende des Monats, dein", "Spotify premium Account", "muss wieder gezahlt werden!")),
    HESSLER_SCHULDEN(buildLongText("Frau Hessler braucht immer", "noch das Geld vom klettern", "und sie hat dich ausgesucht", "zu zahlen!"));

    private final String MESSAGE;

    ExtraFields(String message) {
        this.MESSAGE = message;
    }

    public String getMessage() {
        return MESSAGE;
    }
}
