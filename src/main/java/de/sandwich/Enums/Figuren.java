package de.sandwich.Enums;

import javafx.scene.image.Image;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.creatImage;;

public enum Figuren {

    AFFE(creatImage("/de/sandwich/monopoly/figuren/affe.png")),
    BURGER(creatImage("/de/sandwich/monopoly/figuren/burger.png")),
    DOENER(creatImage("/de/sandwich/monopoly/figuren/doener.png")),
    PINGUIN(creatImage("/de/sandwich/monopoly/figuren/pinguin.png")),
    SANDWICH(creatImage("/de/sandwich/monopoly/figuren/sandwich.png")),
    ENTE(creatImage("/de/sandwich/monopoly/figuren/ente.png")),
    MAUS(creatImage("/de/sandwich/monopoly/figuren/maus.png"));

    private final Image figureImage;

    Figuren(Image i) {
        this.figureImage = i;
    }

    public Image getFigureImage() {
        return figureImage;
    }

}
