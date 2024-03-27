package sandwich.de.monopoly.Enums;

import javafx.scene.paint.Color;
public enum ProgramColor {

    /*
    Wenn du beschreibung machst mach mit Css nh
     */


    //Menu
    MENU_THIRD_PLAYER(Color.rgb(126, 217, 87)),
    MENU_SECOND_PLAYER(Color.rgb(217, 217, 217)),
    MENU_FIRST_PLAYER(Color.rgb(92, 225, 230)),

    //Game
    BACKGROUND(Color.rgb(112, 224, 88)),
    DISPLAY_ONE_BACKGROUND(Color.rgb(97, 220, 43)),
    DISPLAY_TWO_BACKGROUND(Color.rgb(56, 182, 255)),
    DISPLAY_MID_BACKGROUND(Color.rgb(13, 155, 35)),
    JAIL_COLOR(Color.rgb(255, 149, 0)),

    //Player display
    STREET_NOT_OWNED(Color.rgb(173,173,173)),
    PLAYER_ONE_BACKGROUND(Color.rgb(33, 203, 85)),
    PLAYER_TWO_BACKGROUND(Color.rgb(255, 49, 49)),
    PLAYER_THREE_BACKGROUND(Color.rgb(255, 97, 0)),
    PLAYER_FOUR_BACKGROUND(Color.rgb(140, 82, 255)),
    PLAYER_FIVE_BACKGROUND(Color.rgb(56, 182, 255)),

    //Action display
    ACTIONS_BACKGROUND(Color.LIME),
    ACTION_BANK_BUTTON(Color.rgb(79, 79, 79)),
    ACTION_BUILD_BUTTON(Color.rgb(38, 64, 77)),
    ACTION_LEAVE_BUTTON(Color.rgb(117, 21, 21)),

    //Dice display
    DICE_BACKGROUND(Color.rgb(93, 150, 212)),
    DICE_ROLE_BUTTON(Color.rgb(93, 150, 212)),

    //Trading display
    TRADING_ARROW(Color.rgb(186, 41, 41)),


    //Bank display
    BANK_PLAYER_BACKGROUND(Color.rgb(78, 138, 186)),

    //Whole program
    ERROR_MESSAGES(Color.rgb(209, 8, 8)),
    CHANCEL_BUTTONS(Color.rgb(209, 8, 8)),
    FINISH_BUTTONS(Color.rgb(94, 222, 9)),
    SYMBOLE_COLOR(Color.rgb(217, 219, 215)),
    TEXT_COLOR(Color.rgb(255, 255, 255)),
    BORDER_COLOR_DARK(Color.rgb(0, 0, 0)),
    BORDER_COLOR_LIGHT(Color.rgb(255, 255, 255)),
    //Muss noch geguckt werden welche Farbe
    NULL_COLOR(Color.rgb(255, 0, 212));

    private final Color color;
    ProgramColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
