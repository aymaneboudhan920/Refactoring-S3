package universite_paris8.iut.aboudhan.saes2javafx.modele.tour;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.microbe.Microbe;

import java.util.List;

public class TourScientifique extends Tour {
    public static int prixAchat = 25;

    public TourScientifique(double x, double y) {
        super(x, y, 136, 10, 1.0, prixAchat, "/.../tour_scientifique.png",
                new AttaqueProjectile(8.0, "SCIENTIFIQUE"));
    }
}
