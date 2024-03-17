package sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sandwich.de.monopoly.Main;

import java.util.Objects;
import java.util.Stack;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;

/**
 *  <h2>Utilities for Java and JavaFx to create graphical programs</h2>
 *  <p>
 *  These utilities are intended to simplify programming with Java/JavaFX
 *  and keep the code clean.
 *  <p>
 *  <h3>Infos for this specific version:</h3>
 *  <p>
 *  Nop, you don't have to know anything to use this.
 *  <p>
 * @author bauer
 * @version 1.0
 * @since 16.03.2024
 */

public class JavaFXUtilities {

    /**
     * This method places a control in the middle of a pane
     * @param child The object that should be placed in the middle.
     * @param pane The pane in which the object is located.
     */
    public static void centeringChildInPane(Control child, Pane pane) {
        child.layoutXProperty().bind(pane.widthProperty().subtract(child.widthProperty()).divide(2));
    }

    public static StackPane buildPlus(String id, double lineWidth, double lineHeight, double rotate, double borderWidth, Color borderColor, Color plusColor, double lineX, double lineY) {
        StackPane plus = new StackPane();
        plus.setId(id);

        Rectangle lineOne = buildRectangle(id + "_LineOne", lineWidth, lineHeight, plusColor, true, borderColor, borderWidth);
        lineOne.setRotate(rotate);
        lineOne.setX(lineX);
        lineOne.setY(lineY);

        Rectangle lineTwo = buildRectangle(id + "_LineTwo", lineWidth, lineHeight, plusColor, true, borderColor, borderWidth);
        lineTwo.setRotate(90 + rotate);
        lineTwo.setX(lineX);
        lineTwo.setY(lineY);

        return new StackPane(lineOne, lineTwo);
    }

    //Achtung änderung: Man sollte auch mit geben was für ein Typ das Object hat mit <>
    public static Object getNodeOutAPane(String id, Pane root) {

        Stack<Pane> panes = new Stack<>();
        panes.push(root);

        while (!panes.isEmpty()) {
            Pane p = panes.pop();
            for (Object c: p.getChildren().toArray()) {
                if (c instanceof Node) {
                    if(((Node) c).getId() != null) {
                        if (((Node) c).getId().equals(id))
                            return c;
                    }
                    if (c instanceof Pane) {
                        panes.push((Pane) c);
                    }
                }
            }
        }

        return null;
    }

    //Java FX ImageViews (sourcePath start's add the Resources direction. Example: /pictures/menu/heading.png)
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

    //Java FX Images (sourcePath start's add the Resources direction. Example: /pictures/menu/heading.png)

    public static Image creatImage(String sourcePath) {
        try {
            return new Image(Objects.requireNonNull(Main.class.getResourceAsStream(sourcePath)));
        } catch (Exception e) {
            System.out.println("\033[1;31mSource aus dem SourcePath \"" + sourcePath + "\" konnte nicht gefunden werden!\033[0m");
        }
        return null;
    }

    /**
     *  JavaFX Animations:
     *  Alle möglichen animation, einfach zu erstellen
     *  einfache Methoden!
     *  <p>Animation Typs:</p>
     *  <p>TranslateTransition: Ändert die Position eines Nodes.</p>
     *  <p>ScaleTransition: Ändert die Größe eines Nodes.</p>
     *  <p>RotateTransition: Ändert die Rotation eines Nodes.</p>
     *  <p>FadeTransition: Ändert die Transparenz eines Nodes.</p>
     *  <p>PathTransition: Bewegt einen Node entlang eines Pfads.</p>
     *  <p>FillTransition: Ändert die Füllfarbe eines Shapes.</p>
     *  <p>StrokeTransition: Ändert die Randfarbe eines Shapes.</p>
     *  <p>ParallelTransition: Ermöglicht das Ausführen mehrerer Transitionen gleichzeitig.</p>
     *  <p>SequentialTransition: Ermöglicht das Ausführen von Transitionen nacheinander.</p>
     */


    public static TranslateTransition moveAnimation(Node object, Duration length, double toY, double toX, int cycleCount) {
        TranslateTransition transition = new TranslateTransition(length, object);

        transition.setToY(toY);
        transition.setToX(toX);
        transition.setCycleCount(cycleCount);

        return transition;
    }
    public static ScaleTransition scaleAnimation(Node object, Duration length, double byY, double byX, int cycleCount) {
        ScaleTransition transition = new ScaleTransition(length, object);

        transition.setByY(byY);
        transition.setByX(byX);


        return transition;
    }

}
