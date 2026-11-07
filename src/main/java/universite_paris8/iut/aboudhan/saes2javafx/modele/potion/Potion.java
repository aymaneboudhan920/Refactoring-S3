package universite_paris8.iut.aboudhan.saes2javafx.modele.potion;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;

public abstract class Potion {
    private final String nom;
    private final int prixAchat;
    protected EffetPotionStrategy effetStrategy;

    public Potion(String nom, int prixAchat, EffetPotionStrategy effetStrategy) {
        this.nom = nom;
        this.prixAchat = prixAchat;
        this.effetStrategy = effetStrategy;
    }

    public void appliquerEffet(Environnement env) {
        if (effetStrategy != null) {
            effetStrategy.appliquerEffet(env);
        }
    }

    public void setEffetStrategy(EffetPotionStrategy effetStrategy) {
        this.effetStrategy = effetStrategy;
    }

    public String getNom() {
        return nom;
    }

    public int getPrixAchat() {
        return prixAchat;
    }
}
