package universite_paris8.iut.aboudhan.saes2javafx.controller;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.tour.Inventaire;
import universite_paris8.iut.aboudhan.saes2javafx.modele.tour.*;
import universite_paris8.iut.aboudhan.saes2javafx.vue.tour.InventaireVue;

public class ControllerTour extends ControllerAchat {

    private final Inventaire inventaireModele;
    private final InventaireVue inventaireVue;
    private final Controller controllerPrincipal;

    public ControllerTour(Environnement env, Inventaire inventaireModele, InventaireVue inventaireVue, Controller controllerPrincipal) {

        super(env);
        this.inventaireModele = inventaireModele;
        this.inventaireVue = inventaireVue;
        this.controllerPrincipal = controllerPrincipal;
    }

    public boolean acheterTour(String typeItem) {
        return acheter(typeItem);
    }

    @Override
    protected int getPrix(String typeItem) {
        switch (typeItem) {
            case "scientifique":
                return TourScientifique.prixAchat;
            case "chimiste":
                return TourChimiste.prixAchat;
            case "scanner":
                return TourScanner.prixAchat;
            case "rayon_x":
                return TourRayonX.prixAchat;
            default:
                return -1;
        }
    }

    @Override
    protected boolean peutAcheter(String typeItem) {
        return inventaireModele.getPremiereCaseLibre() != -1;
    }

    @Override
    protected void appliquerAchat(String typeItem) {
        int caseLibre = inventaireModele.getPremiereCaseLibre();
        Tour tour = creerTourDepuisType(typeItem);

        inventaireModele.setTourCase(caseLibre, typeItem);
        inventaireVue.installerTour(caseLibre, typeItem);
        env.getTourVersIndexInventaire().put(tour, caseLibre);
        controllerPrincipal.updateCompteurs();
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