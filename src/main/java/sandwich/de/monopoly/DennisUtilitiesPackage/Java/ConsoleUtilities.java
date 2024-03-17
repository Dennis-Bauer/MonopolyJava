package sandwich.de.monopoly.DennisUtilitiesPackage.Java;

/**
 *  <h2>Utilities for Java and JavaFx to create graphical programs</h2>
 *  <p>
 *  These utilities are intended to simplify programming with Java/JavaFX
 *  and keep the code clean. In this specific version the method to write
 *  to the console is updated. You can choose your console text color and
 *  text style to make the beauty of your console text fuller than ever.
 *  <p>
 *  <h3>Infos for this specific version:</h3>
 *  <p>
 *  Nop, you don't have to know anything to use this.
 *  <p>
 * @author bauer
 * @version 1.0
 * @since 16.03.2024
 */

public class ConsoleUtilities {


    public static final String RESET = "\033[0m";  // Text Reset

    public enum colors { //Color Values
        BLACK, RED, GREEN, YELLOW, BLUE, PURPLE, CYAN, WHITE
    }
    public enum textStyle { //Style Values
        REGULAR, BOLD, UNDERLINE, BACKGROUND, HIGH_INTENSITY, BOLD_HIGH_INTENSITY, HIGH_INTENSITY_BACKGROUNDS
    }



    /**
     * This method outputs modified text to the console and outputs the text in a text line
     * @param color This input sets the Color of the text.
     * @param style This input sets the Style of the text.
     * @param text This is the text that will be output.
     */
    public static void consoleOutPutLine(colors color, textStyle style, String text) {
        System.out.println(getTextCode(color, style) + text + RESET);
    }

    /**
     * This method outputs modified text to the console and outputs the text
     * @param color This input sets the Color of the text.
     * @param style This input sets the Style of the text.
     * @param text This is the text that will be output.
     */
    public static void consoleOutPut(colors color, textStyle style, String text) {
        System.out.print(getTextCode(color, style) + text + RESET);
    }

    /**
     * This method generates the text code that sets the color and style.
     * @param color This input says what color the text code should contain.
     * @param style This input says what style the text code should contain.
     * @return Returns the text code.
     */
    private static String getTextCode(colors color, textStyle style) {
        String colorCode = "\033[";
        switch (style) {
            case REGULAR, UNDERLINE, BOLD -> {
                switch (style) {
                    case REGULAR -> colorCode = colorCode + "0;";
                    case BOLD -> colorCode = colorCode + "1;";
                    case UNDERLINE -> colorCode = colorCode + "4;";
                }

                switch (color) {
                    case BLACK ->   colorCode = colorCode + "30m";
                    case RED ->     colorCode = colorCode + "31m";
                    case GREEN ->   colorCode = colorCode + "32m";
                    case YELLOW ->  colorCode = colorCode + "33m";
                    case BLUE ->    colorCode = colorCode + "34m";
                    case PURPLE ->  colorCode = colorCode + "35m";
                    case CYAN ->    colorCode = colorCode + "36m";
                    case WHITE ->   colorCode = colorCode + "37m";
                }

            }
            case BACKGROUND -> {
                switch (color) {
                    case BLACK ->   colorCode = colorCode + "40m";
                    case RED ->     colorCode = colorCode + "41m";
                    case GREEN ->   colorCode = colorCode + "42m";
                    case YELLOW ->  colorCode = colorCode + "43m";
                    case BLUE ->    colorCode = colorCode + "44m";
                    case PURPLE ->  colorCode = colorCode + "45m";
                    case CYAN ->    colorCode = colorCode + "46m";
                    case WHITE ->   colorCode = colorCode + "47m";
                }
            }
            case HIGH_INTENSITY, BOLD_HIGH_INTENSITY -> {
                switch (style) {
                    case HIGH_INTENSITY -> colorCode = colorCode + "0;";
                    case BOLD_HIGH_INTENSITY -> colorCode = colorCode + "1;";
                }
                switch (color) {
                    case BLACK ->   colorCode = colorCode + "90m";
                    case RED ->     colorCode = colorCode + "91m";
                    case GREEN ->   colorCode = colorCode + "92m";
                    case YELLOW ->  colorCode = colorCode + "93m";
                    case BLUE ->    colorCode = colorCode + "94m";
                    case PURPLE ->  colorCode = colorCode + "95m";
                    case CYAN ->    colorCode = colorCode + "96m";
                    case WHITE ->   colorCode = colorCode + "97m";
                }
            }
            case HIGH_INTENSITY_BACKGROUNDS -> {
                switch (color) {
                    case BLACK ->   colorCode = colorCode + "0;100m";
                    case RED ->     colorCode = colorCode + "0;101m";
                    case GREEN ->   colorCode = colorCode + "0;102m";
                    case YELLOW ->  colorCode = colorCode + "0;103m";
                    case BLUE ->    colorCode = colorCode + "0;104m";
                    case PURPLE ->  colorCode = colorCode + "0;105m";
                    case CYAN ->    colorCode = colorCode + "0;106m";
                    case WHITE ->   colorCode = colorCode + "0;107m";
                }
            }
        }
        return colorCode;
    }


}
