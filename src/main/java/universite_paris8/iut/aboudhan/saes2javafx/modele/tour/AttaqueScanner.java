package universite_paris8.iut.aboudhan.saes2javafx.modele.tour;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.microbe.Microbe;

import java.util.List;

public class AttaqueScanner implements StratAttaque{
    @Override
    public void attaquer(Environnement env, Tour tour) {
        if (!tour.peutAttaquer() || tour.getRayonAnimationActuel() < tour.getPortee()) {
            return;
        }

        List<Microbe> microbesActifs = env.getMicrobesActifs();
        for (int i = 0; i < microbesActifs.size(); i++) {
            Microbe m = microbesActifs.get(i);
            if (!m.estMort() && tour.estAPortee(m)) {
                m.perdreVie(tour.getDegats(), tour);
            }
        }

        tour.recharger();
        tour.reinitialiserAttaque();
    }
}
