package universite_paris8.iut.aboudhan.saes2javafx.modele.tour;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.microbe.Microbe;

import java.util.List;

public class AttaqueScanner implements StratAttaque {
    private double rayonAnimationActuel = 0;

    @Override
    public void attaquer(Environnement env, Tour tour) {
        if (!tour.peutAttaquer() || rayonAnimationActuel < tour.getPortee()) return;

        for (Microbe m : env.getMicrobesActifs()) {
            if (!m.estMort() && tour.estAPortee(m)) {
                m.perdreVie(tour.getDegats(), tour);
            }
        }
        tour.recharger();
        reinitialiser();
    }

    @Override
    public void mettreAJour(Tour tour, double t) {
        double vitesseExtension = tour.getPortee() * tour.getVitesseTir();
        rayonAnimationActuel = Math.min(rayonAnimationActuel + vitesseExtension * t, tour.getPortee());
    }

    @Override
    public void reinitialiser() { rayonAnimationActuel = 0; }

    public double getRayonAnimationActuel() { return rayonAnimationActuel; }
}