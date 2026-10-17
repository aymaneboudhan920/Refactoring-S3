
package universite_paris8.iut.aboudhan.saes2javafx.controller;

import javafx.scene.layout.Pane;
import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.potion.*;
import universite_paris8.iut.aboudhan.saes2javafx.modele.tour.*;
import universite_paris8.iut.aboudhan.saes2javafx.vue.bouton.ShopVue;

public class ControllerShop {

    private final Environnement env;
    private final Pane conteneurPrincipal;
    private final Controller controllerPrincipal;
    private final ControllerPotion potionController;
    private final ControllerTour tourController;

    private ShopVue shopActuel;

    public ControllerShop(Environnement env, Pane conteneurPrincipal, Controller controllerPrincipal, ControllerPotion potionController, ControllerTour tourController) {

        this.env = env;
        this.conteneurPrincipal = conteneurPrincipal;
        this.controllerPrincipal = controllerPrincipal;
        this.potionController = potionController;
        this.tourController = tourController;
    }

    public void actionBoutonShop() {
        if (shopActuel != null) {
            fermerShop();
            return;
        }

        controllerPrincipal.mettreEnPause();
        controllerPrincipal.verrouillerInterface(true);

        int vagueActu =
                env.getGestionnaireVagues().getNumVagueActu() + 1;

        shopActuel = new ShopVue(() -> fermerShop(),
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

        shopActuel.afficherSur(conteneurPrincipal);
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
        if (shopActuel == null) {
            return;
        }

        shopActuel.cacher(conteneurPrincipal);
        shopActuel = null;

        controllerPrincipal.verrouillerInterface(false);
        controllerPrincipal.reprendreJeu();
    }
}
