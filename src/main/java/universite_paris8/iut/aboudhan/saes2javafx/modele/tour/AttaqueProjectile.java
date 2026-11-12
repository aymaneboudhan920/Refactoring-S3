package universite_paris8.iut.aboudhan.saes2javafx.modele.tour;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.microbe.Microbe;

import java.util.List;

public class AttaqueProjectile implements StratAttaque{

    public AttaqueProjectile() {
    }

    @Override
    public void attaquer(Environnement env, Tour tour) {
        List<Microbe> microbesActifs = env.getMicrobesActifs();
        Microbe cible = null;

        for (int i = 0; i < microbesActifs.size() && cible == null; i++) {
            Microbe m = microbesActifs.get(i);
            if (!m.estMort() && !"INFLUENZA".equals(m.getType())) {
                double diffX = m.getX() - tour.getX();
                double diffY = m.getY() - tour.getY();
                double distance = Math.sqrt(diffX * diffX + diffY * diffY);

                if (distance <= tour.getPortee()) {
                    cible = m;
                }
            }
        }

        if (cible != null && tour.peutAttaquer()) {
//            Projectile potion = new Projectile(this.getX(), this.getY(), 5.0, cible, "CHIMISTE", this.getDegats(), this);
            env.ajouterProjectile(tour.initProjectile(cible, 5.0));
            tour.recharger();
        }
    }
}
