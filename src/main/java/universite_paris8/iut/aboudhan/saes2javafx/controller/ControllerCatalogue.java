package universite_paris8.iut.aboudhan.saes2javafx.controller;

import javafx.scene.layout.Pane;
import universite_paris8.iut.aboudhan.saes2javafx.modele.potion.PotionGel;
import universite_paris8.iut.aboudhan.saes2javafx.modele.potion.PotionRage;
import universite_paris8.iut.aboudhan.saes2javafx.modele.potion.PotionSoin;
import universite_paris8.iut.aboudhan.saes2javafx.modele.tour.*;
import universite_paris8.iut.aboudhan.saes2javafx.vue.bouton.CatalogueVue;
import universite_paris8.iut.aboudhan.saes2javafx.vue.bouton.FenetreVue;

import java.util.Map;

public class ControllerCatalogue extends ControllerFenetre {

    public ControllerCatalogue(Controller controllerPrincipal, Pane conteneur) {
        super(controllerPrincipal, conteneur, true);
    }

    @Override
    protected FenetreVue creerVue() {
        Map<String, Integer> prixEquipements = Map.of(
                "scientifique", TourScientifique.prixAchat,
                "chimiste", TourChimiste.prixAchat,
                "scanner", TourScanner.prixAchat,
                "rayon_x", TourRayonX.prixAchat,
                "potion_soin", PotionSoin.prixAchat,
                "potion_rage", PotionRage.prixAchat,
                "potion_gel", PotionGel.prixAchat
        );

        return new CatalogueVue(prixEquipements, this::fermer);
    }
}
