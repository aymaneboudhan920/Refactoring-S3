package universite_paris8.iut.aboudhan.saes2javafx.modele.potion;

public class PotionRage extends Potion {
    public static final int prixAchat = 125;

    public PotionRage() {
        super("Potion de Rage", prixAchat, new RageStrategy());
    }
}
