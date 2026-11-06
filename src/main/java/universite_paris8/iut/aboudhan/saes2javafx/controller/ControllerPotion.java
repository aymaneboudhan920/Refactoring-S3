package universite_paris8.iut.aboudhan.saes2javafx.controller;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.potion.*;


public class ControllerPotion {

    private final Environnement env;

    public ControllerPotion(Environnement env) {
        this.env = env;
    }

    public boolean acheterPotion(String typeItem) {
        int prix = switch (typeItem) {
            case "potion_soin" -> PotionSoin.prixAchat;
            case "potion_rage" -> PotionRage.prixAchat;
            case "potion_gel"  -> PotionGel.prixAchat;
            default -> -1;
        };

        if (prix < 0 || env.getArgent() < prix) {
            return false;
        }

        env.reduireArgent(prix);

        switch (typeItem) {
            case "potion_soin" ->
                    env.setNbPotionSoin(env.getNbPotionSoin() + 1);

            case "potion_rage" ->
                    env.setNbPotionRage(env.getNbPotionRage() + 1);

            case "potion_gel" ->
                    env.setNbPotionGel(env.getNbPotionGel() + 1);
        }

        return true;
    }
}

