package sandwich.de.monopoly.Enums;

import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;

public enum ExtraFields {

    SPOTIFY_PREMIUM(buildLongText("Es ist ende des Monats, dein", "dein Spotify Premium account", "muss wieder gezahlt werden!")),
    HESSLER_SCHULDEN(buildLongText("Frau Hessler braucht immer", "noch das Geld vom klettern", "und sie hat dich aus gesucht", "zu zahlen!")),
    NAME_THREE(buildLongText("Hier würde ein Text stehen,", "wenn ich mir was ausdenken", "könnte!")),
    NAME_FOUR(buildLongText("Hier würde ein Text stehen,", "wenn ich mir was ausdenken", "könnte!"));

    private final String message;

    ExtraFields(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
