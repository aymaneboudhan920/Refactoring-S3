package universite_paris8.iut.aboudhan.saes2javafx.controller;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.potion.*;


public class ControllerPotion extends ControllerAchat {

    public ControllerPotion(Environnement env) {
        super(env);
    }

    public boolean acheterPotion(String typeItem) {
        return acheter(typeItem);
    }

    @Override
    protected int getPrix(String typeItem) {
        return switch (typeItem) {
            case "potion_soin" -> PotionSoin.prixAchat;
            case "potion_rage" -> PotionRage.prixAchat;
            case "potion_gel"  -> PotionGel.prixAchat;
            default -> -1;
        };
    }

    @Override
    protected void appliquerAchat(String typeItem) {
        switch (typeItem) {
            case "potion_soin" ->
                    env.setNbPotionSoin(env.getNbPotionSoin() + 1);

            case "potion_rage" ->
                    env.setNbPotionRage(env.getNbPotionRage() + 1);

            case "potion_gel" ->
                    env.setNbPotionGel(env.getNbPotionGel() + 1);
        }
    }
}