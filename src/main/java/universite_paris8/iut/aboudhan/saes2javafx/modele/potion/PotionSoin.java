package universite_paris8.iut.aboudhan.saes2javafx.modele.potion;

public class PotionSoin extends Potion {
    public static final int prixAchat = 150;

    public PotionSoin() {
        super("Potion de Soin", prixAchat, new SoinStrategy());
    }
}
