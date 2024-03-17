package sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX;

import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.List;

/**
 *  <h2>Utilities for Java and JavaFx to create graphical programs</h2>
 *  <p>
 *  These utilities are intended to simplify programming with Java/JavaFX
 *  and keep the code clean. In this specific version, the constructor of
 *  various JavaFX objects is improved, which simplifies the creation of
 *  these objects in many ways. I highly recommend always using the first
 *  input value ID as it makes your program clearer to understand.
 *  <p>
 *  <h3>Infos for this specific version:</h3>
 *  <p>
 *  X and y values are always the upper left point of an object. So you
 *  always have to remember that the top left point is the point you want
 *  the postion.
 *  The x and y values are not related to the window, but to the pane in which
 *  they are. So if you set an object to 0/0 it is not always at the top left
 *  of the window but at the top left of the pane. It is also important that
 *  these X and y values do not work in every pane. For example in an HBox,
 *  VBox or Stackpane the values do NOT work!
 *  <p>
 * @author bauer
 * @version 1.0
 * @since 16.03.2024
 */

public class JavaFXConstructorUtilities {

    /**
     * This method creates a rectangle with the values passed in the input.
     * @param id This input sets the id value of the rectangle.
     * @param width This input sets the width of the rectangle.
     * @param height This input sets the height of the rectangle.
     * @param fill This input sets the color of the rectangle.
     * @param isVisible This input makes the rectangle Visible or not.
     * @param borderFill This input sets the border color, if you don`t want a border you can set it to null.
     * @param borderWidth This input sets the border width, if you don`t want a border you can set it to 0.
     * @return Returns the finished rectangle.
     */
    public static Rectangle buildRectangle(String id, double width, double height, Color fill, boolean isVisible, Color borderFill, double borderWidth) {
        Rectangle r = new Rectangle(width, height, fill);
        r.setId(id);
        r.setVisible(isVisible);
        r.setStroke(borderFill);
        r.setStrokeWidth(borderWidth);
        return r;
    }

    /**
     * This method creates the same rectangle as in the first method but with additional values passed in the input.
     * @param x This input sets the X Position of the rectangle.
     * @param y This input sets the X Position of the rectangle.
     * @return Returns the finished rectangle.
     */
    public static Rectangle buildRectangle(String id, double width, double height, Color fill, boolean isVisible, Color borderFill, double borderWidth, double x, double y) {
        Rectangle r = buildRectangle(id, width, height, fill, isVisible, borderFill, borderWidth);
        r.setX(x);
        r.setY(y);
        return r;
    }

    /**
     * This method creates a Circle with the values passed in the input.
     * @param id This input sets the id value of the circle.
     * @param radius This input sets the radius of the circle.
     * @param fill This input sets the color of the circle.
     * @param isVisible This input makes the circle Visible or not.
     * @param borderFill This input sets the border color, if you don`t want a border you can set it to null.
     * @param borderWidth This input sets the border width, if you don`t want a border you can set it to 0.
     * @return Returns the finished circle.
     */
    public static Circle buildCircle(String id, double radius, Color fill, boolean isVisible, Color borderFill, double borderWidth) {
        Circle c = new Circle(radius, fill);
        c.setId(id);
        c.setVisible(isVisible);
        c.setStroke(borderFill);
        c.setStrokeWidth(borderWidth);
        return c;
    }

    /**
     * This method creates the same circle as in the first method but with additional values passed in the input.
     * Attention, the X and Y position of the circle is not like the other shapes at the top left, it is in the center!
     * @param centerX This input sets the center x Position of the circle.
     * @param centerY This input sets the center y Position of the circle.
     * @return Returns the finished circle.
     */
    public static Circle buildCircle(String id, double radius, Color fill, boolean isVisible, Color borderFill, double borderWidth, double centerX, double centerY) {
        Circle c = buildCircle(id, radius, fill, isVisible, borderFill, borderWidth);
        c.setCenterX(centerX);
        c.setCenterY(centerY);
        return c;
    }

    /**
     * This method creates a triangle as a Polygon object. This works with the help
     * of 3 points that are specified in the input. These points are the 3 corners
     * of the rectangle and a line is drawn from each point to each point and then
     * filled in with the fill value.
     * @param id This input sets the id value of the Polygon.
     * @param p1 This is the first point with an x and y value.
     * @param p2 This is the second point with an x and y value.
     * @param p3 This is the third point with an x and y value.
     * @param fill This input sets the color of the triangle.
     * @param borderFill This input sets the border color, if you don`t want a border you can set it to null.
     * @return Returns the finished triangle.
     */
    public static Polygon buildTriangle(String id, Point2D p1, Point2D p2, Point2D p3, Color fill, Color borderFill) {
        Polygon triangle = new Polygon();

        triangle.setId(id);
        triangle.getPoints().addAll(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY());
        triangle.setFill(fill);
        triangle.setStroke(borderFill);

        return triangle;
    }

    /**
     * This method creates the same triangle as in the first method but with additional values passed in the input.
     * @param x This input sets the x Position of the polygon.
     * @param y This input sets the y Position of the polygon.
     * @return Returns the finished triangle.
     */
    public static Polygon buildTriangle(String id, Point2D p1, Point2D p2, Point2D p3, Color fill, Color borderFill, double x, double y) {
        Polygon triangle = buildTriangle(id, p1, p2, p3, fill, borderFill);

        triangle.setLayoutX(x);
        triangle.setLayoutY(y);

        return triangle;
    }

    /**
     * This method creates a Line object. This works with the help
     * of 2 points that are specified in the input. These points are the start and end
     * of the line and then a line is drawn from each point to each point.
     * @param id This input sets the id value of the line.
     * @param startPoint This is the first point with an x and y value.
     * @param endPoint This is the second point with an x and y value.
     * @param lineWidth This is the width of the line.
     * @param fill This input sets the color of the line.
     * @return Returns the finished line.
     */
    public static Line buildLine(String id, Point2D startPoint, Point2D endPoint, double lineWidth, Color fill) {
        Line line = new Line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());

        line.setId(id);
        line.setStrokeWidth(lineWidth);
        line.setStroke(fill);

        return line;
    }


    /**
     * This method creates a button that can be used as an input.
     * @param id This input sets the id value of the button.
     * @param text This sets the text that is displayed on the button
     * @param width This input sets the width of the button.
     * @param height This input sets the height of the button.
     * @param font This sets the texts font of the button text.
     * @return Returns the finished button.
     */
    public static Button buildButton(String id , String text, double width, double height, Font font) {
        Button b = new Button();

        b.setText(text);
        b.setId(id);
        b.setPrefWidth(width);
        b.setPrefHeight(height);
        b.setFont(font);

        return b;
    }

    /**
     * This method creates the same button as in the first method but with additional values passed in the input.
     * @param x This input sets the x Position of the button.
     * @param y This input sets the y Position of the button.
     * @return Returns the finished button.
     */
    public static Button buildButton(String id ,String text, double width, double height, Font font, double x, double y) {
        Button b = buildButton(id, text, width, height, font);
        b.setLayoutX(x);
        b.setLayoutY(y);
        return b;
    }

    /**
     * This method creates a label.
     * @param id This input sets the id value of the label.
     * @param text This sets the label displayed text.
     * @param font This sets the texts font of the label text.
     * @param textAlignment As soon as the text has several lines,
     *                      this can help if you want the text to
     *                      be centered or pointed in a different
     *                      direction. (You can create texts with
     *                      several lines only using one label with
     *                      one String input using another method from
     *                      my utility package: JavaUtilities.buildLongText(String... text))
     * @param textColor This sets the color from the label text.
     * @return Returns the finished label.
     */
    public static Label buildLabel(String id , String text, Font font, TextAlignment textAlignment, Color textColor) {
        Label l = new Label();

        l.setText(text);
        l.setId(id);
        l.setTextFill(textColor);
        l.setFont(font);
        l.setTextAlignment(textAlignment);

        return l;
    }

    /**
     * This method creates the same label as in the first method but with additional values passed in the input.
     * @param x This input sets the x Position of the label.
     * @param y This input sets the y Position of the label.
     * @return Returns the finished label.
     */
    public static Label buildLabel(String id ,String text, Font font, TextAlignment textAlignment, Color textColor, double x, double y) {
        Label l = buildLabel(id, text, font, textAlignment, textColor);

        l.setLayoutX(x);
        l.setLayoutY(y);

        return l;
    }

    /**
     * This method creates a text field that can be used as an input.
     * @param id This input sets the id value of the text field.
     * @param promptText This input sets the text field prompt text. This is
     *                   the text that displayed when you don't use the
     *                   text field.
     * @param width This input sets the width of the text field.
     * @param height This input sets the height of the text field.
     * @return Returns the finished text field.
     */
    public static TextField buildTextField(String id , String promptText, double width, double height) {
        TextField t = new TextField();

        t.setPromptText(promptText);
        t.setId(id);
        t.setPrefWidth(width);
        t.setPrefHeight(height);
        t.setFont(new Font((height / 10) * 3));

        return t;
    }

    /**
     * This method creates the same text field as in the first method but with additional values passed in the input.
     * @param font This input sets the font value of the prompt text and of the input text.
     * @return Returns the finished text field.
     */
    public static TextField buildTextField(String id ,String promptText, double width, double height, Font font) {
        TextField t = buildTextField(id, promptText, width, height);
        t.setFont(font);
        return t;
    }

    /**
     * This method creates the same text field as in the first method but with additional values passed in the input.
     * @param x This input sets the x Position of the text field.
     * @param y This input sets the y Position of the text field.
     * @return Returns the finished text field.
     */
    public static TextField buildTextField(String id ,String promptText, double width, double height, double x, double y) {
        TextField t = buildTextField(id, promptText, width, height);
        t.setScaleX(x);
        t.setScaleY(y);
        return t;
    }

    /**
     * This method creates the same text field as in the first method but with additional values passed in the input.
     * @param font This input sets the font value of the prompt text and of the input text.
     * @param x This input sets the x Position of the text field.
     * @param y This input sets the y Position of the text field.
     * @return Returns the finished text field.
     */
    public static TextField buildTextField(String id ,String promptText, double width, double height, Font font, double x, double y) {
        TextField t = buildTextField(id, promptText, width, height, x, y);
        t.setFont(font);
        return t;
    }

    /**
     * This method creates a choice box that can be used as an input.
     * @param id This input sets the id value of the choice box.
     * @param choices This input sets the choices that can be chosen from
     *                the user.
     * @param width This input sets the width of the choice box.
     * @param height This input sets the height of the choice box.
     * @return Returns the finished choice box.
     */
    public static ChoiceBox<String> buildChoiceBox(String id, List<String> choices, double width, double height) {
        ChoiceBox<String> cb = new ChoiceBox<>();

        cb.getItems().addAll(choices);
        cb.setId(id);
        cb.setPrefWidth(width);
        cb.setPrefHeight(height);

        return cb;
    }

    /**
     * This method creates the same choice box as in the first method but with additional values passed in the input.
     * @param x This input sets the x Position of the choice box.
     * @param y This input sets the y Position of the choice box.
     * @return Returns the finished choice box.
     */
    public static ChoiceBox<String> buildChoiceBox(String id, List<String> choices, double width, double height, double x, double y) {
        ChoiceBox<String> cb = buildChoiceBox(id, choices, width, height);

        cb.setLayoutX(x);
        cb.setLayoutY(y);

        return cb;
    }

    /**
     * This method creates a combo box that can be used as an input.
     * @param id This input sets the id value of the combo box.
     * @param choices This input sets the choices that can be chosen from
     *                the user.
     * @param width This input sets the width of the combo box.
     * @param height This input sets the height of the combo box.
     * @return Returns the finished combo box.
     */
    public static ComboBox<String> buildComboBox(String id, List<String> choices, double width, double height) {
        ComboBox<String> cb = new ComboBox<>();

        cb.getItems().addAll(choices);
        cb.setId(id);
        cb.setPrefWidth(width);
        cb.setPrefHeight(height);

        return cb;
    }

    /**
     * This method creates the same combo box as in the first method but with additional values passed in the input.
     * @param x This input sets the x Position of the combo box.
     * @param y This input sets the y Position of the combo box.
     * @return Returns the finished combo box.
     */
    public static ComboBox<String> buildComboBox(String id, List<String> choices, double width, double height, double x, double y) {
        ComboBox<String> cb = buildComboBox(id, choices, width, height);

        cb.setLayoutX(x);
        cb.setLayoutY(y);

        return cb;
    }

    /**
     * This method creates a color picker that can be used as an input.
     * @param id This input sets the id value of the color picker.
     * @param width This input sets the width of the color picker.
     * @param height This input sets the height of the color picker.
     * @param startColor This sets the first color that is chosen.
     * @return Returns the finished color picker.
     */
    public static ColorPicker buildColorPicker(String id, double width, double height, Color startColor) {
        ColorPicker cp = new ColorPicker(startColor);

        cp.setId(id);
        cp.setPrefWidth(width);
        cp.setPrefHeight(height);

        return cp;
    }

    /**
     * This method creates the same color picker as in the first method but with additional values passed in the input.
     * @param x This input sets the x Position of the color picker.
     * @param y This input sets the y Position of the color picker.
     * @return Returns the finished color picker.
     */
    public static ColorPicker buildColorPicker(String id, double width, double height, Color startColor, double x, double y) {
        ColorPicker cp = buildColorPicker(id, width, height, startColor);

        cp.setLayoutX(x);
        cp.setLayoutY(y);

        return cp;
    }

}
