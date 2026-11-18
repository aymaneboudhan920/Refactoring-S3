package universite_paris8.iut.aboudhan.saes2javafx.controller;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;


public abstract class ControllerAchat {

    protected final Environnement env;

    protected ControllerAchat(Environnement env) {
        this.env = env;
    }

    public final boolean acheter(String typeItem) {
        int prix = getPrix(typeItem);

        if (prix < 0 || env.getArgent() < prix) {
            return false;
        }
        if (!peutAcheter(typeItem)) {
            return false;
        }

        env.reduireArgent(prix);
        appliquerAchat(typeItem);
        return true;
    }

    protected abstract int getPrix(String typeItem);

    protected boolean peutAcheter(String typeItem) {
        return true;
    }

    protected abstract void appliquerAchat(String typeItem);
}