package universite_paris8.iut.aboudhan.saes2javafx.controller;

import javafx.scene.layout.Pane;
import universite_paris8.iut.aboudhan.saes2javafx.vue.bouton.FenetreVue;
import universite_paris8.iut.aboudhan.saes2javafx.vue.bouton.ReglesVue;

public class ControllerRegles extends ControllerFenetre {

    public ControllerRegles(Controller controllerPrincipal, Pane racine) {
        super(controllerPrincipal, racine, false);
    }

    @Override
    protected FenetreVue creerVue() {
        return new ReglesVue(this::fermer);
    }
}
