package universite_paris8.iut.aboudhan.saes2javafx.modele.tour;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.microbe.Microbe;

import java.util.List;

public class TourRayonX extends Tour {
    public static final int prixAchat = 100;

    public TourRayonX(double x, double y) {
        super(x, y, 102, 2, 10, prixAchat, "/universite_paris8/iut/aboudhan/saes2javafx/vue/tour_rayon_x.png", new AttaqueRayonX());
    }

    public Projectile getRayonActuel() { return ((AttaqueRayonX) getStratAttaque()).getRayonActuel(); }
    public Microbe getCibleActuelle() { return ((AttaqueRayonX) getStratAttaque()).getCibleActuelle(); }
}
