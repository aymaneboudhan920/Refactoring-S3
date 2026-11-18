package universite_paris8.iut.aboudhan.saes2javafx.controller;

import javafx.scene.layout.Pane;
import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Configuration;
import universite_paris8.iut.aboudhan.saes2javafx.vue.bouton.HelpVue;
import universite_paris8.iut.aboudhan.saes2javafx.vue.bouton.FenetreVue;

public class ControllerAide extends ControllerFenetre {

    private final Configuration configJeu;
    private HelpVue vueAide;

    public ControllerAide(Controller controllerPrincipal, Pane conteneur, Configuration configJeu) {
        super(controllerPrincipal, conteneur, true);
        this.configJeu = configJeu;
    }

    @Override
    protected FenetreVue creerVue() {
        vueAide = new HelpVue(
                configJeu.getTexteTutorielCourant(),
                configJeu.estPremierePage(),
                configJeu.estDernierePage(),
                () -> {
                    configJeu.pagePrecedente();
                    rafraichirPage();
                },
                () -> {
                    configJeu.pageSuivante();
                    rafraichirPage();
                },
                this::fermer
        );
        return vueAide;
    }

    @Override
    protected void surFermeture() {
        configJeu.reinitialiserTutoriel();
    }

    private void rafraichirPage() {
        vueAide.rafraichirPage(configJeu.getTexteTutorielCourant(), configJeu.estPremierePage(), configJeu.estDernierePage());
    }
}
