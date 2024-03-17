package sandwich.de.monopoly.Enums;

import javafx.scene.image.Image;
import sandwich.de.monopoly.DennisUtilitiesPackage.Utilities;

public enum Figuren {

    AFFE(Utilities.creatImage("/sandwich/de/monopoly/figuren/affe.png")),
    BURGER(Utilities.creatImage("/sandwich/de/monopoly/figuren/burger.png")),
    DOENER(Utilities.creatImage("/sandwich/de/monopoly/figuren/doener.png")),
    PINGUIN(Utilities.creatImage("/sandwich/de/monopoly/figuren/pinguin.png")),
    SANDWICH(Utilities.creatImage("/sandwich/de/monopoly/figuren/sandwich.png"));

    private final Image figureImage;

    Figuren (Image i) {
        this.figureImage = i;
    }

    public Image getFigureImage() {
        return figureImage;
    }

}
