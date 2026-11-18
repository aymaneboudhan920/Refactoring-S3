package universite_paris8.iut.aboudhan.saes2javafx.controller;

import javafx.scene.layout.Pane;
import universite_paris8.iut.aboudhan.saes2javafx.vue.bouton.InfoVue;
import universite_paris8.iut.aboudhan.saes2javafx.vue.bouton.FenetreVue;

public class ControllerInfo extends ControllerFenetre {

    public ControllerInfo(Controller controllerPrincipal, Pane conteneur) {
        super(controllerPrincipal, conteneur, true);
    }

    @Override
    protected FenetreVue creerVue() {
        return new InfoVue(this::fermer);
    }
}
