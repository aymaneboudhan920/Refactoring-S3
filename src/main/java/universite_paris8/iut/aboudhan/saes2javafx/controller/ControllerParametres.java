package universite_paris8.iut.aboudhan.saes2javafx.controller;

import javafx.scene.layout.Pane;
import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Configuration;
import universite_paris8.iut.aboudhan.saes2javafx.vue.bouton.ParametreVue;
import universite_paris8.iut.aboudhan.saes2javafx.vue.bouton.FenetreVue;

public class ControllerParametres extends ControllerFenetre {

    private final Configuration configJeu;

    public ControllerParametres(Controller controllerPrincipal, Pane conteneur, Configuration configJeu, boolean bloqueLeJeu) {
        super(controllerPrincipal, conteneur, bloqueLeJeu);
        this.configJeu = configJeu;
    }

    @Override
    protected FenetreVue creerVue() {
        return new ParametreVue(
                configJeu.getVolumeMusique(),
                configJeu.getVolumeBruitages(),
                nouveauVolMusique -> configJeu.setVolumeMusique(nouveauVolMusique),
                nouveauVolBruit -> configJeu.setVolumeBruitages(nouveauVolBruit),
                this::fermer
        );
    }
}
