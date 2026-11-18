package universite_paris8.iut.aboudhan.saes2javafx.modele.tour;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.microbe.Microbe;
import universite_paris8.iut.aboudhan.saes2javafx.modele.microbe.TypeMicrobe;

public class AttaqueProjectile implements StratAttaque {
    private final double vitesseProjectile;
    private final TypeTour typeTour;

    public AttaqueProjectile(double vitesseProjectile, TypeTour typeTour) {
        this.vitesseProjectile = vitesseProjectile;
        this.typeTour = typeTour;
    }

    @Override
    public void attaquer(Environnement env, Tour tour) {
        if (!tour.peutAttaquer()) return;

        for (Microbe m : env.getMicrobesActifs()) {
            if (!m.estMort() && !(m.getType() == TypeMicrobe.INFLUENZA) && tour.estAPortee(m)) {
                env.ajouterProjectile(tour.initProjectile(m, vitesseProjectile, typeTour));
                tour.recharger();
                return;
            }
        }
    }

    @Override public void mettreAJour(Tour tour, double t) { }
    @Override public void reinitialiser() { }
}
