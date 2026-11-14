package universite_paris8.iut.aboudhan.saes2javafx.modele.tour;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.microbe.Microbe;

import java.util.List;

public class TourChimiste extends Tour {
    public static final int prixAchat = 50;
    public static final int PORTEE = 102;

    public TourChimiste(double x, double y) {
        super(x, y, PORTEE, 15, 0.33, prixAchat, "/.../tour_chimiste.png",
                new AttaqueProjectile(5.0, "CHIMISTE"));
    }
}