package universite_paris8.iut.aboudhan.saes2javafx.modele.tour;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.microbe.Microbe;

import java.util.List;

public class TourScanner extends Tour {
    public static int prixAchat = 75;

    public TourScanner(double x, double y) {
        super(x, y, 102, 10, 0.8, prixAchat, "/.../tour_scanner.png", new AttaqueScanner());
    }

    public double getRayonAnimationActuel() {
        return ((AttaqueScanner) getStratAttaque()).getRayonAnimationActuel();
    }
}
