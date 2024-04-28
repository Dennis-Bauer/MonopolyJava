package de.sandwich.Enums;

import javafx.scene.image.Image;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.creatImage;;

public enum Figuren {

    PAUL(creatImage("/de/sandwich/monopoly/figuren/paul.png")),
    BURGER(creatImage("/de/sandwich/monopoly/figuren/burger.png")),
    DOENER(creatImage("/de/sandwich/monopoly/figuren/doener.png")),
    PINGUIN(creatImage("/de/sandwich/monopoly/figuren/pinguin.png")),
    SANDWICH(creatImage("/de/sandwich/monopoly/figuren/sandwich.png")),
    ENTE(creatImage("/de/sandwich/monopoly/figuren/ente.png")),
    MAUS(creatImage("/de/sandwich/monopoly/figuren/maus.png")),
    AMONGUS(creatImage("/de/sandwich/monopoly/figuren/amongus.png")),
    OCTOPUS(creatImage("/de/sandwich/monopoly/figuren/octopus.png")),
    FROG(creatImage("/de/sandwich/monopoly/figuren/frog.png"));

    private final Image figureImage;

    Figuren(Image i) {
        this.figureImage = i;
    }

    public Image getFigureImage() {
        return figureImage;
    }

}
