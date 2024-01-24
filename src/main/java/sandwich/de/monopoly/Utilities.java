package sandwich.de.monopoly;

import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.util.List;

public class Utilities {

    //Color Output
    public static final String RESET = "\033[0m";  // Text Reset
    public enum colors {
        BLACK, RED, GREEN, YELLOW, BLUE, PURPLE, CYAN, WHITE
    }
    public enum textStyle {
        REGULAR, BOLD, UNDERLINE, BACKGROUND, HIGH_INTENSITY, BOLD_HIGH_INTENSITY, HIGH_INTENSITY_BACKGROUNDS
    }

    //JavaFX nodes
    public static Button buildButton(String text, String id, double x, double y, double width, double height, double fontSize) {
        Button b = new Button();

        b.setText(text);
        b.setId(id);
        b.setLayoutX(x);
        b.setLayoutY(y);
        b.setPrefWidth(width);
        b.setPrefHeight(height);
        b.setFont(new Font(fontSize));

        return b;
    }

    public static Label buildLabel(String text, String id, double x, double y, double fontSize, TextAlignment textAlignment, Color textColor) {
        Label l = new Label();

        l.setText(text);
        l.setId(id);
        l.setLayoutX(x);
        l.setLayoutY(y);
        l.setTextFill(textColor);
        l.setFont(new Font(fontSize));
        l.setTextAlignment(textAlignment);

        return l;
    }

    public static TextField buildTextField(String promptText, String id, double x, double y, double width, double height, double fontSize) {
        TextField t = buildTextField(promptText, id, x, y, width, height);
        t.setFont(new Font(fontSize));
        return t;
    }

    public static TextField buildTextField(String promptText, String id, double x, double y, double width, double height) {
        TextField t = new TextField();

        t.setPromptText(promptText);
        t.setId(id);
        t.setLayoutX(x);
        t.setLayoutY(y);
        t.setPrefWidth(width);
        t.setPrefHeight(height);
        t.setFont(new Font((height / 10) * 3));

        return t;
    }

    public static ChoiceBox<String> buildChoiceBox(List<String> choices, String id, double x, double y, double width, double height) {
        ChoiceBox<String> cb = new ChoiceBox<>();

        cb.getItems().addAll(choices);
        cb.setId(id);
        cb.setLayoutX(x);
        cb.setLayoutY(y);
        cb.setPrefWidth(width);
        cb.setPrefHeight(height);

        return cb;
    }

    public static ComboBox<String> buildComboBox(List<String> choices, String id, double x, double y, double width, double height) {
        ComboBox<String> cb = new ComboBox<>();

        cb.getItems().addAll(choices);
        cb.setId(id);
        cb.setLayoutX(x);
        cb.setLayoutY(y);
        cb.setPrefWidth(width);
        cb.setPrefHeight(height);

        return cb;
    }

    public static ColorPicker buildColorPicker(String id, double x, double y, double width, double height, Color startColor) {
        ColorPicker cp = new ColorPicker(startColor);

        cp.setId(id);
        cp.setLayoutX(x);
        cp.setLayoutY(y);
        cp.setPrefWidth(width);
        cp.setPrefHeight(height);

        return cp;
    }

    //Java Utilities
    public static String buildLongText(List<String> strings) {
        StringBuilder s = new StringBuilder();
        for (String string : strings) {
            s.append(string);
            s.append("\n");
        }
        return s.toString();
    }

    public static String colorToRGB(Color color) {
        return String.format("rgb(%d, %d, %d)",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255)
        );
    }

    //Java save Data's in files
    public static void saveData(String fileName, Object data) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(data);
            consoleOutPutLine(colors.GREEN, textStyle.REGULAR, "Data saved successfully");
        } catch (IOException e) {
            consoleOutPutLine(colors.RED, textStyle.REGULAR, "Error in Utilities: Saving Object data not possible (ACHTUNG! Kann eine Fehlermeldung sein!)");
        }
    }

    public static Object getData(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            return inputStream.readObject();
        } catch (NotSerializableException ignore) {
            return null; //CHANGE
        } catch (IOException | ClassNotFoundException e) {
            consoleOutPutLine(colors.RED, textStyle.REGULAR, "Error in Utilities: Reading Object data not possible");
            e.printStackTrace();
            return null;
        }
    }

    public static Boolean existTheFile(String fileName) {
        File f = new File(fileName);
        return f.exists();
    }

    public static void deleteFile(String fileName) {
        File f = new File(fileName);
        if(f.exists()) {
            if(!f.delete()) {
                consoleOutPutLine(colors.RED, textStyle.REGULAR, "Error in Utilities: File deletion failed");
            }
        } else {
            consoleOutPutLine(colors.GREEN, textStyle.REGULAR, "The file that was supposed to be deleted no longer exists");
        }
    }

    public static int countFilesInFolder(String folderPath) {
        File folder = new File(folderPath);

        if (!folder.isDirectory()) {
            consoleOutPutLine(colors.RED, textStyle.REGULAR, "Error in Utilities: A specified path is not a directory");
            return 0;
        }

        File[] files = folder.listFiles();

        if (files == null) {
            System.out.println("Error in Utilities: No files found in directory.");
            return 0;
        }

        int count = 0;
        for (File file : files)
            if (file.isFile())
                count++;
        return count;
    }

    public static String[] getFilesNameInFolder(String folderPath) {
        File folder = new File(folderPath);

        if (!folder.isDirectory()) {
            consoleOutPutLine(colors.RED, textStyle.REGULAR, "Error in Utilities: A specified path is not a directory");
            return new String[0];
        }

        File[] files = folder.listFiles();

        if (files == null) {
            System.out.println("Error in Utilities: No files found in directory.");
            return new String[0];
        }

        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getName();
        }
        return names;
    }

    //Color Output
    public static void consoleOutPutLine(colors color, textStyle style, String text) {
        System.out.println(getColorCode(color, style) + text + RESET);
    }

    public static void consoleOutPut(colors color, textStyle style, String text) {
        System.out.print(getColorCode(color, style) + text + RESET);
    }

    private static String getColorCode(colors color, textStyle style) {
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

