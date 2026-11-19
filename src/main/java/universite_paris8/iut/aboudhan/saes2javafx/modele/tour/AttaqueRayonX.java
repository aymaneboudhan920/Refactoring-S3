package universite_paris8.iut.aboudhan.saes2javafx.modele.tour;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.microbe.Microbe;
import universite_paris8.iut.aboudhan.saes2javafx.modele.microbe.TypeMicrobe;

import java.util.List;

public class AttaqueRayonX implements StratAttaque {
    private static final double DEGATS_MIN = 0.5;
    private static final double DEGATS_MAX = 40.0;
    private static final double PAS_AUGMENTATION = 1.3;

    private Microbe cibleActuelle = null;
    private Projectile rayonActuel = null;
    private double degatsActuels = DEGATS_MIN;

    @Override
    public void attaquer(Environnement env, Tour tour) {
        List<Microbe> microbes = env.getMicrobesActifs();

        if (cibleActuelle != null
                && (cibleActuelle.estMort() || !microbes.contains(cibleActuelle) || !tour.estAPortee(cibleActuelle))) {
            if (rayonActuel != null) rayonActuel.setDetruit(true);
            reinitialiser();
        }

        if (cibleActuelle == null) {
            for (int i = 0; i < microbes.size() && cibleActuelle == null; i++) {
                Microbe m = microbes.get(i);
                if (!m.estMort() && tour.estAPortee(m) && !(m.getType() == TypeMicrobe.INFLUENZA)){
                    cibleActuelle = m;
                    degatsActuels = DEGATS_MIN;
                }
            }
        }

        if (cibleActuelle != null && tour.peutAttaquer()) {
            if (rayonActuel == null || rayonActuel.estDetruit()) {
                rayonActuel = new Projectile(tour.getX(), tour.getY(), 0.0,
                        cibleActuelle, TypeTour.RAYON_X, degatsActuels, tour);
                env.ajouterProjectile(rayonActuel);
            }
            cibleActuelle.perdreVie(degatsActuels, tour);
            degatsActuels = Math.min(degatsActuels * PAS_AUGMENTATION, DEGATS_MAX);
            tour.recharger();
        }
    }

    @Override public void mettreAJour(Tour tour, double t) { }

    @Override
    public void reinitialiser() {
        if (rayonActuel != null) rayonActuel.setDetruit(true);
        this.degatsActuels = DEGATS_MIN;
        this.cibleActuelle = null;
        this.rayonActuel = null;
    }

    public Projectile getRayonActuel() { return rayonActuel; }
    public Microbe getCibleActuelle() { return cibleActuelle; }
}
