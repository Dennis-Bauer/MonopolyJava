package sandwich.de.monopoly;

import sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities;
import sandwich.de.monopoly.Enums.Figuren;
import sandwich.de.monopoly.Exceptions.PlayerNotFoundExceptions;

import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPut;
import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPutLine;

public class Player {

    private int bankAccount;
    private final String name;
    private int fieldPostion;
    private final int orderNumber;
    private final Figuren figur;

    //Game Variables
    private boolean isInJail = false;
    private int inJailRemain = 0;


    public Player(String name, Figuren f, int orderNumber) {
        this.name = name;
        this.figur = f;
        this.orderNumber = orderNumber;

        //Start account
        bankAccount = 2000;
        fieldPostion = 0;
    }

    public String getName() {
        return name;
    }

    public int getBankAccount() {
        return bankAccount;
    }

    public Figuren getFigur() {
        return figur;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getFieldPostion() {
        return fieldPostion;
    }

    public int getInJailRemain() {
        return inJailRemain;
    }

    public boolean isInJail() {
        return isInJail;
    }

    public void addBankAccount(int transferNumber) {
        bankAccount = bankAccount + transferNumber;
        try { Main.getGameOperator().getDisplayControllerOne().updateDisplay(); } catch (PlayerNotFoundExceptions ignored) {}
    }

    public void setInJail(boolean inJail) {
        isInJail = inJail;
        if (inJail) {
            inJailRemain = 3;
            fieldPostion = 10;
        }
    }

    public void removeOnInJailRemain() {
        this.inJailRemain--;
    }

    public void moveFieldPostion(int postions) {
        fieldPostion = fieldPostion + postions;
        if (fieldPostion >= 40) {
            fieldPostion = fieldPostion - 40;

            addBankAccount(200);

            if (Main.CONSOLE_OUT_PUT) {
                consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Der Spieler nummer " + orderNumber + " ist über los gegangen und hat");
                consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, " 200€ Plus gemacht!");
                consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
            }
        }

    }

    @Override
    public String toString() {
        return "Name: " + name + ", Figure: " + figur.toString().toLowerCase() + ", FieldPosition: " + fieldPostion + ", Kontostand: " + bankAccount;
    }

}
