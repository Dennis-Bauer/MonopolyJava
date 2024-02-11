package sandwich.de.monopoly;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.util.List;
import java.util.Objects;

//Don't forget: Update changes in Cloud
public class Utilities {

    //Color Output
    public static final String RESET = "\033[0m";  // Text Reset
    public enum colors {
        BLACK, RED, GREEN, YELLOW, BLUE, PURPLE, CYAN, WHITE
    }
    public enum textStyle {
        REGULAR, BOLD, UNDERLINE, BACKGROUND, HIGH_INTENSITY, BOLD_HIGH_INTENSITY, HIGH_INTENSITY_BACKGROUNDS
    }

    //JavaFX Shapes
    public static Rectangle buildRectangle(String id, double width, double height, Paint fill, boolean isVisible, Paint borderFill, double borderWidth) {
        Rectangle r = new Rectangle(width, height, fill);
        r.setId(id);
        r.setVisible(isVisible);
        r.setStroke(borderFill);
        r.setStrokeWidth(borderWidth);
        return r;
    }

    public static Rectangle buildRectangle(String id, double width, double height, Paint fill, boolean isVisible, Paint borderFill, double borderWidth, double x, double y) {
        Rectangle r = buildRectangle(id, width, height, fill, isVisible, borderFill, borderWidth);
        r.setX(x);
        r.setY(y);
        return r;
    }

    public static Circle buildCircle(String id, double radius, Paint fill, boolean isVisible, Paint borderFill, double borderWidth) {
        Circle c = new Circle(radius, fill);
        c.setId(id);
        c.setVisible(isVisible);
        c.setStroke(borderFill);
        c.setStrokeWidth(borderWidth);
        return c;
    }

    public static Circle buildCircle(String id, double radius, Paint fill, boolean isVisible, Paint borderFill, double borderWidth, double x, double y) {
        Circle c = buildCircle(id, radius, fill, isVisible, borderFill, borderWidth);
        c.setCenterX(x);
        c.setCenterY(y);
        return c;
    }

    public static Polygon buildTriangle(String id, Point2D p1, Point2D p2, Point2D p3, Paint fill, Paint borderFill) {
        Polygon triangle = new Polygon();

        triangle.setId(id);
        triangle.getPoints().addAll(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY());
        triangle.setFill(fill);
        triangle.setStroke(borderFill);

        return triangle;
    }

    public static Polygon buildTriangle(String id, Point2D p1, Point2D p2, Point2D p3, Paint fill, Paint borderFill, double x, double y) {
        Polygon triangle = buildTriangle(id, p1, p2, p3, fill, borderFill);

        triangle.setLayoutX(x);
        triangle.setLayoutY(y);

        return triangle;
    }

    //JavaFX nodes
    public static Button buildButton(String id ,String text, double width, double height, Font font) {
        Button b = new Button();

        b.setText(text);
        b.setId(id);
        b.setPrefWidth(width);
        b.setPrefHeight(height);
        b.setFont(font);

        return b;
    }

    public static Button buildButton(String id ,String text, double width, double height, Font font, double x, double y) {
        Button b = buildButton(id, text, width, height, font);
        b.setLayoutX(x);
        b.setLayoutY(y);
        return b;
    }


    public static Label buildLabel(String id ,String text, Font font, TextAlignment textAlignment, Color textColor) {
        Label l = new Label();

        l.setText(text);
        l.setId(id);
        l.setTextFill(textColor);
        l.setFont(font);
        l.setTextAlignment(textAlignment);

        return l;
    }

    public static Label buildLabel(String id ,String text, Font font, TextAlignment textAlignment, Color textColor, double x, double y) {
        Label l = buildLabel(id, text, font, textAlignment, textColor);

        l.setLayoutX(x);
        l.setLayoutY(y);

        return l;
    } // ERKLÄRUNG HINZUFÜGEN (Nicht realtiv vom Fenster, heißt 0, 0 ist nicht oben Links)


    public static TextField buildTextField(String id ,String promptText, double width, double height) {
        TextField t = new TextField();

        t.setPromptText(promptText);
        t.setId(id);
        t.setPrefWidth(width);
        t.setPrefHeight(height);
        t.setFont(new Font((height / 10) * 3));

        return t;
    }

    public static TextField buildTextField(String id ,String promptText, double width, double height, Font font) {
        TextField t = buildTextField(promptText, id, width, height);
        t.setFont(font);
        return t;
    }

    public static TextField buildTextField(String id ,String promptText, double width, double height, double x, double y) {
        TextField t = buildTextField(promptText, id, width, height);
        t.setScaleX(x);
        t.setScaleY(y);
        return t;
    }

    public static TextField buildTextField(String id ,String promptText, double width, double height, Font font, double x, double y) {
        TextField t = buildTextField(promptText, id, width, height, x, y);
        t.setFont(font);
        return t;
    }


    public static ChoiceBox<String> buildChoiceBox(String id, List<String> choices, double width, double height) {
        ChoiceBox<String> cb = new ChoiceBox<>();

        cb.getItems().addAll(choices);
        cb.setId(id);
        cb.setPrefWidth(width);
        cb.setPrefHeight(height);

        return cb;
    }

    public static ChoiceBox<String> buildChoiceBox(String id, List<String> choices, double width, double height, double x, double y) {
        ChoiceBox<String> cb = buildChoiceBox(id, choices, width, height);

        cb.setLayoutX(x);
        cb.setLayoutY(y);

        return cb;
    }


    public static ComboBox<String> buildComboBox(String id, List<String> choices, double width, double height) {
        ComboBox<String> cb = new ComboBox<>();

        cb.getItems().addAll(choices);
        cb.setId(id);
        cb.setPrefWidth(width);
        cb.setPrefHeight(height);

        return cb;
    }

    public static ComboBox<String> buildComboBox(String id, List<String> choices, double width, double height, double x, double y) {
        ComboBox<String> cb = buildComboBox(id, choices, width, height);

        cb.setLayoutX(x);
        cb.setLayoutY(y);

        return cb;
    }


    public static ColorPicker buildColorPicker(String id, double width, double height, Color startColor) {
        ColorPicker cp = new ColorPicker(startColor);

        cp.setId(id);
        cp.setPrefWidth(width);
        cp.setPrefHeight(height);

        return cp;
    }

    public static ColorPicker buildColorPicker(String id, double width, double height, Color startColor, double x, double y) {
        ColorPicker cp = buildColorPicker(id, width, height, startColor);

        cp.setLayoutX(x);
        cp.setLayoutY(y);

        return cp;
    }

    //Java FX Utilities
    public static void centeringChildInPane(Control child, Pane pane) {
        child.layoutXProperty().bind(pane.widthProperty().subtract(child.widthProperty()).divide(2));
    } //(KEIN OFFICIALESE KOMMENTAR) Achtung, klappt nicht bei Formen oder anderes was nicht der Control Klasse erbt, vielleicht ändern

    //Java FX Images (sourcePath start's add the Resources direction. Example: /pictures/menu/heading.png)
    public static ImageView createImageView(String id, Image image, double postionX, double postionY) {
        if (image != null) {
            ImageView iv = new ImageView(image);
            iv.setId(id);
            iv.setX(postionX);
            iv.setY(postionY);
            iv.toBack();
            return iv;
        } else return null;
    }

    public static ImageView createImageView(String id ,String sourcePath, double postionX, double postionY) {
        if(Main.class.getResourceAsStream(sourcePath) != null)
            return createImageView(id, new Image(Objects.requireNonNull(Main.class.getResourceAsStream(sourcePath))), postionX, postionY);
        else return null;
    }

    public static ImageView createImageView(String id ,Image image, double imageWidth, double imageHeight, double postionX, double postionY) {
        ImageView iv = createImageView(id ,image, postionX, postionY);
        if (iv != null) {
            iv.setFitWidth(imageWidth);
            iv.setFitHeight(imageHeight);
        }
        return iv;
    }

    public static ImageView createImageView(String id ,String sourcePath, double imageWidth, double imageHeight, double postionX, double postionY) {
        ImageView iv = createImageView(id ,sourcePath, postionX, postionY);
        if (iv != null) {
            iv.setFitWidth(imageWidth);
            iv.setFitHeight(imageHeight);
        }
        return iv;
    }

    //Java Utilities
    public static String buildLongText(String... text) {
        StringBuilder s = new StringBuilder();
        String[] strings = text;
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

