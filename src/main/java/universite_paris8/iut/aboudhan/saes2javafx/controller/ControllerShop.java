
package universite_paris8.iut.aboudhan.saes2javafx.controller;

import javafx.scene.layout.Pane;
import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.potion.*;
import universite_paris8.iut.aboudhan.saes2javafx.modele.tour.*;
import universite_paris8.iut.aboudhan.saes2javafx.vue.bouton.ShopVue;
import universite_paris8.iut.aboudhan.saes2javafx.vue.bouton.FenetreVue;

public class ControllerShop extends ControllerFenetre {

    private final Environnement env;
    private final ControllerPotion potionController;
    private final ControllerTour tourController;

    public ControllerShop(Environnement env, Pane conteneurPrincipal, Controller controllerPrincipal,
                          ControllerPotion potionController, ControllerTour tourController) {
        super(controllerPrincipal, conteneurPrincipal, true);
        this.env = env;
        this.potionController = potionController;
        this.tourController = tourController;
    }

    public void actionBoutonShop() {
        basculer();
    }

    @Override
    protected FenetreVue creerVue() {
        int vagueActu = env.getGestionnaireVagues().getNumVagueActu() + 1;

        return new ShopVue(() -> fermerShop(),
                typeItem -> traiterAchat(typeItem),
                vagueActu,
                env.getArgent(),
                TourScientifique.prixAchat,
                TourChimiste.prixAchat,
                TourScanner.prixAchat,
                TourRayonX.prixAchat,
                PotionSoin.prixAchat,
                PotionRage.prixAchat,
                PotionGel.prixAchat);
    }

    public void traiterAchat(String typeItem) {
        if (typeItem.startsWith("potion_")) {
            potionController.acheterPotion(typeItem);
        } else {
            tourController.acheterTour(typeItem);
        }

        fermerShop();
    }

    public void fermerShop() {
        fermer();
    }
}
 