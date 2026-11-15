package universite_paris8.iut.aboudhan.saes2javafx.modele.potion;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;

public abstract class Potion {
    private final String nom;
    private final int prixAchat;
    protected EffetPotionStrategie effetStrategie;

    public Potion(String nom, int prixAchat, EffetPotionStrategie effetStrategie) {
        this.nom = nom;
        this.prixAchat = prixAchat;
        this.effetStrategie = effetStrategie;
    }

    public void appliquerEffet(Environnement env) {
        if (effetStrategie != null) {
            effetStrategie.appliquerEffet(env);
        }
    }

    public void setEffetStrategy(EffetPotionStrategie effetStrategy) {
        this.effetStrategie = effetStrategie;
    }

    public String getNom() {
        return nom;
    }

    public int getPrixAchat() {
        return prixAchat;
    }
}
