package universite_paris8.iut.aboudhan.saes2javafx.modele.potion;
import modele.microbe.Microbe;

public class EffetGel implements EffetPotionStrategy {
    private double facteurRalentissement;
    private int duree;

    public EffetGel(double facteurRalentissement, int duree) {
        this.facteurRalentissement = facteurRalentissement;
        this.duree = duree;
    }

    @Override
    public void appliquerEffet(Microbe microbe) {
        microbe.ralentir(facteurRalentissement, duree);
        System.out.println("Le microbe est gelé !");
    }
}
