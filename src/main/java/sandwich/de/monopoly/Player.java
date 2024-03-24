package sandwich.de.monopoly;

import sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities;
import sandwich.de.monopoly.Enums.Figuren;
import sandwich.de.monopoly.Exceptions.PlayerNotFoundExceptions;
import sandwich.de.monopoly.GUI.Game.DisplayController.GameDisplayControllerOne;

import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPut;
import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPutLine;

public class Player {

    private final int START_BANK_ACCOUNT = 2000;
    private int bankAccount;
    private String name;
    private int fieldPostion;
    private final int orderNumber;
    Figuren figur;

    public Player(String name, Figuren f, int orderNumber) {
        this.name = name;
        this.figur = f;
        this.orderNumber = orderNumber;

        bankAccount = START_BANK_ACCOUNT;
        fieldPostion = 0;
    }

    public String getName() {
        return name;
    }

    public int getBankAccount() {
        return bankAccount;
    }

    public void addBankAccount(int transferNumber) {
        bankAccount = bankAccount + transferNumber;
        try { GameDisplayControllerOne.updateDisplay(); } catch (PlayerNotFoundExceptions ignored) {}
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
