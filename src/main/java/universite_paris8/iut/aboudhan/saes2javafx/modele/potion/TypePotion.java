package universite_paris8.iut.aboudhan.saes2javafx.modele.potion;

import java.util.function.Supplier;

public enum TypePotion {
    SOIN("potion_soin", PotionSoin.prixAchat, PotionSoin::new),
    RAGE("potion_rage", PotionRage.prixAchat, PotionRage::new),
    GEL("potion_gel", PotionGel.prixAchat, PotionGel::new);

    private final String identifiant;
    private final int prix;
    private final Supplier<Potion> fabrique;

    TypePotion(String identifiant, int prix) {
        this.identifiant = identifiant;
        this.prix = prix;
    }

    public String getIdentifiant() { return identifiant; }
    public int getPrix() { return prix; }

    public static TypePotion depuisIdentifiant(String id) {
        for (TypePotion tp : values()) {
            if (tp.identifiant.equals(id)) return tp;
        }
        return null;
    }

    public Potion creerPotion() {
        return fabrique.get();
    }

}
