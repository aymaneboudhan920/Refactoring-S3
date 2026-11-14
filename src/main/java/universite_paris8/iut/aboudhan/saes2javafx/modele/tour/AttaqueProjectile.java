package universite_paris8.iut.aboudhan.saes2javafx.modele.tour;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.microbe.Microbe;

import java.util.List;

public class AttaqueProjectile implements StratAttaque {
    private final double vitesseProjectile;
    private final String typeProjectile;

    public AttaqueProjectile(double vitesseProjectile, String typeProjectile) {
        this.vitesseProjectile = vitesseProjectile;
        this.typeProjectile = typeProjectile;
    }

    @Override
    public void attaquer(Environnement env, Tour tour) {
        if (!tour.peutAttaquer()) return;

        for (Microbe m : env.getMicrobesActifs()) {
            if (!m.estMort() && !"INFLUENZA".equals(m.getType()) && tour.estAPortee(m)) {
                env.ajouterProjectile(tour.initProjectile(m, vitesseProjectile, typeProjectile));
                tour.recharger();
                return;
            }
        }
    }

    @Override public void mettreAJour(Tour tour, double t) { }
    @Override public void reinitialiser() { }
}
