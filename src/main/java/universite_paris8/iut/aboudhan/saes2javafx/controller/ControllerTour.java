
package universite_paris8.iut.aboudhan.saes2javafx.controller;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.tour.Inventaire;
import universite_paris8.iut.aboudhan.saes2javafx.modele.tour.*;
import universite_paris8.iut.aboudhan.saes2javafx.vue.tour.InventaireVue;

public class ControllerTour {

    private final Environnement env;
    private final Inventaire inventaireModele;
    private final InventaireVue inventaireVue;
    private final Controller controllerPrincipal;

    public ControllerTour(Environnement env, Inventaire inventaireModele, InventaireVue inventaireVue, Controller controllerPrincipal) {

        this.env = env;
        this.inventaireModele = inventaireModele;
        this.inventaireVue = inventaireVue;
        this.controllerPrincipal = controllerPrincipal;
    }

    public boolean acheterTour(String typeItem) {
        int prix;

        switch (typeItem) {
            case "scientifique":
                prix = TourScientifique.prixAchat;
                break;
            case "chimiste":
                prix = TourChimiste.prixAchat;
                break;
            case "scanner":
                prix = TourScanner.prixAchat;
                break;
            case "rayon_x":
                prix = TourRayonX.prixAchat;
                break;
            default:
                return false;
        }

        if (env.getArgent() < prix) {
            return false;
        }

        int caseLibre = inventaireModele.getPremiereCaseLibre();

        if (caseLibre == -1) {
            return false;
        }

        Tour tour = creerTourDepuisType(typeItem);

        if (tour == null) {
            return false;
        }

        env.reduireArgent(prix);
        inventaireModele.setTourCase(caseLibre, typeItem);
        inventaireVue.installerTour(caseLibre, typeItem);
        env.getTourVersIndexInventaire().put(tour, caseLibre);
        controllerPrincipal.updateCompteurs();
        return true;
    }

    private Tour creerTourDepuisType(String typeItem) {
        switch (typeItem) {
            case "scientifique":
                return new TourScientifique(0, 0);
            case "chimiste":
                return new TourChimiste(0, 0);
            case "scanner":
                return new TourScanner(0, 0);
            case "rayon_x":
                return new TourRayonX(0, 0);
            default:
                return null;
        }
    }
}
