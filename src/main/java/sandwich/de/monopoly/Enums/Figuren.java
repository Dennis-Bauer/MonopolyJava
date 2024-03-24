package sandwich.de.monopoly.Enums;

import javafx.scene.image.Image;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.creatImage;


public enum Figuren {

    AFFE(creatImage("/sandwich/de/monopoly/figuren/affe.png")),
    BURGER(creatImage("/sandwich/de/monopoly/figuren/burger.png")),
    DOENER(creatImage("/sandwich/de/monopoly/figuren/doener.png")),
    PINGUIN(creatImage("/sandwich/de/monopoly/figuren/pinguin.png")),
    SANDWICH(creatImage("/sandwich/de/monopoly/figuren/sandwich.png"));

    private final Image figureImage;

    Figuren(Image i) {
        this.figureImage = i;
    }

    public Image getFigureImage() {
        return figureImage;
    }

}
