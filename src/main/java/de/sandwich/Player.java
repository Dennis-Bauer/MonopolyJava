package de.sandwich;

import de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities;
import de.sandwich.Enums.Figuren;
import de.sandwich.Exceptions.NumberIsToBigLowExceptions;
import de.sandwich.Exceptions.PlayerNotFoundExceptions;

import static de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPut;
import static de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPutLine;

public class Player {

    private int bankAccount;
    private final String name;
    private int fieldPostion;
    private final int orderNumber;
    private final Figuren figur;

    //Game Variables
    private boolean isInJail = false;
    private int freeJailCards = 0;
    private int inJailRemain = 0;


    public Player(String name, @SuppressWarnings("exports") Figuren f, int orderNumber) {
        this.name = name;
        this.figur = f;
        this.orderNumber = orderNumber;

        //Start account
        bankAccount = 30000; //Standard
        fieldPostion = 0;
    }

    public void transferMoneyToBankAccount(int transferNumber) {
        bankAccount = bankAccount + transferNumber;
        try { Main.getGameOperator().getDisplayControllerOne().updateDisplay(); } catch (PlayerNotFoundExceptions ignored) {}
    }

    public String getName() {
        return name;
    }

    public int getBankAccount() {
        return bankAccount;
    }

    @SuppressWarnings("exports")
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

    public int getFreeJailCardNumber() {
        return freeJailCards;
    }

    public void useFreeJailCard() {
        if (freeJailCards > 0) {
            freeJailCards--;
            removePlayerFromJail();
        } else throw new NumberIsToBigLowExceptions(false, "UsFreeJailCard, not enough Cards!");
    }
 
    public boolean isInJail() {
        return isInJail;
    }

    public void addFreeJailCard() {
        //Denk dran, das sollte nie passieren. Es sollte bei einer gebung immer erst überpüft werden, ob er zu viele hat. Wenn ja, sollte er garkeine bekommen
        if (freeJailCards < 4) {
            freeJailCards++;
        } else throw new NumberIsToBigLowExceptions(true, "addFreeJailCard");
    }

    public void setInJail(boolean inJail) {
        isInJail = inJail;
        if (inJail) {
            try { Main.getGameOperator().getBoard().setPlayerInJail(this); } catch (PlayerNotFoundExceptions e) { e.printStackTrace(); }

            inJailRemain = 0;
            fieldPostion = 10;
        }
    }

    public void removeOnInJailRemain() {
        this.inJailRemain--;
    }

    public void removePlayerFromJail() {
        try { Main.getGameOperator().getBoard().removePlayerFromJail(this); } catch (PlayerNotFoundExceptions e) { e.printStackTrace(); }

        inJailRemain = 0;
        isInJail = false;
    }

    public void moveFieldPostion(int postions) {
        fieldPostion = fieldPostion + postions;
        if (fieldPostion >= 40) {
            fieldPostion = fieldPostion - 40;

            transferMoneyToBankAccount(200);

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
