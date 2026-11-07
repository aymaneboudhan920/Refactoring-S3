package universite_paris8.iut.aboudhan.saes2javafx.modele.potion;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;

public class StrategySoin implements EffetPotionStrategy {
    private static final int NB_SOIGNEE = 10;

    @Override
    public void appliquerEffet(Environnement env) {
        int infectesActuels = env.getGensInfectes();
        env.setGensInfectes(Math.max(0, infectesActuels - NB_SOIGNEE));
    }
}
