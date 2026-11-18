package universite_paris8.iut.aboudhan.saes2javafx.controller;

import javafx.scene.layout.Pane;
import universite_paris8.iut.aboudhan.saes2javafx.vue.bouton.FenetreVue;

public abstract class ControllerFenetre {

    protected final Controller controllerPrincipal;
    private final Pane conteneur;
    private final boolean bloqueLeJeu;
    private FenetreVue vueActive = null;

    protected ControllerFenetre(Controller controllerPrincipal, Pane conteneur, boolean bloqueLeJeu) {
        this.controllerPrincipal = controllerPrincipal;
        this.conteneur = conteneur;
        this.bloqueLeJeu = bloqueLeJeu;
    }

    public final void ouvrir() {
        if (estOuverte()) return;

        if (bloqueLeJeu) controllerPrincipal.bloquerJeu();
        vueActive = creerVue();
        vueActive.afficherSur(conteneur);
    }

    public final void fermer() {
        if (!estOuverte()) return;

        vueActive.cacher(conteneur);
        vueActive = null;
        surFermeture();
        if (bloqueLeJeu) controllerPrincipal.debloquerJeu();
    }

    public final void basculer() {
        if (estOuverte()) {
            fermer();
        } else {
            ouvrir();
        }
    }

    public final boolean estOuverte() {
        return vueActive != null;
    }

    protected abstract FenetreVue creerVue();

    protected void surFermeture() {
    }
}
