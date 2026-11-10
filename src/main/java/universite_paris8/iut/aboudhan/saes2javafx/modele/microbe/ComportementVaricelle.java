package universite_paris8.iut.aboudhan.saes2javafx.modele.microbe;

import java.util.Random;

public class ComportementVaricelle implements ComportementMicrobe {

    private static final int ETAT_NORMAL = 0;
    private static final int ETAT_SOMMEIL = 1;
    private static final int ETAT_ACCELERATION = 2;

    private static final double PAS_DE_TEMPS = 0.012;
    private static final double DUREE_SOMMEIL = 2;
    private static final double DUREE_ACCELERATION = 3.0;
    private static final double VITESSE_ACCELEREE = 2.0;

    private final Random random = new Random();
    private double chronoCycle = 0;
    private double tempsProchainSommeil = tirerTempsProchainSommeil();
    private int etat = ETAT_NORMAL;

    private double tirerTempsProchainSommeil() {
        return 2 + (random.nextDouble() * 3);
    }

    @Override
    public void mettreAJourVitesse(Microbe microbe) {
        if (microbe.estGele()) return;

        chronoCycle += PAS_DE_TEMPS;

        switch (etat) {
            case ETAT_NORMAL -> {
                if (chronoCycle >= tempsProchainSommeil) {
                    etat = ETAT_SOMMEIL;
                    chronoCycle = 0;
                    microbe.fixerVitesseActu(0);
                } else {
                    microbe.fixerVitesseActu(microbe.vitesseDeBase);
                }
            }
            case ETAT_SOMMEIL -> {
                if (chronoCycle >= DUREE_SOMMEIL) {
                    etat = ETAT_ACCELERATION;
                    chronoCycle = 0;
                    microbe.fixerVitesseActu(VITESSE_ACCELEREE);
                } else {
                    microbe.fixerVitesseActu(0);
                }
            }
            case ETAT_ACCELERATION -> {
                if (chronoCycle >= DUREE_ACCELERATION) {
                    etat = ETAT_NORMAL;
                    chronoCycle = 0;
                    tempsProchainSommeil = tirerTempsProchainSommeil();
                    microbe.fixerVitesseActu(microbe.vitesseDeBase);
                } else {
                    microbe.fixerVitesseActu(VITESSE_ACCELEREE);
                }
            }
        }

        if (microbe.estRalenti() && microbe.getVitesseActu() > 0) {
            microbe.fixerVitesseActu(microbe.getVitesseActu() / 2.0);
        }
    }

    @Override
    public double vitesseDeReprise(Microbe microbe) {
        return switch (etat) {
            case ETAT_ACCELERATION -> VITESSE_ACCELEREE;
            case ETAT_SOMMEIL      -> 0;
            default                -> microbe.vitesseDeBase;
        };
    }
}