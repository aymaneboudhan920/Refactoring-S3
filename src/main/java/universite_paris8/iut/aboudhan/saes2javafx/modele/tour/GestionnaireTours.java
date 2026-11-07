package universite_paris8.iut.aboudhan.saes2javafx.modele.tour;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;

import java.util.Map;

public class GestionnaireTours {
    private final Environnement env;

    private boolean modePlacementTour = false;
    private TypeTour tourCliquee = null;
    private int indexInventaireActu = -1;

    public GestionnaireTours(Environnement env) {
        this.env = env;
    }

    public boolean gererClicInventaire(int index, String typeTourStr) {
        TypeTour typeTour = TypeTour.depuisIdentifiant(typeTourStr);

        if (typeTour == null) {
            return false;
        }

        Tour tourPosee = trouverTourPosee(index);

        if (tourPosee != null) {
            return true;
        }
        else {
            modePlacementTour = true;
            tourCliquee = typeTour;
            indexInventaireActu = index;
            return false;
        }
    }

    public Tour gererClicTerrain(double xSurTerrain, double ySurTerrain) {
        if (!modePlacementTour || tourCliquee == null) {
            return null;
        }

        int tailleTuile = env.getTailleTuile();
        int caseX = (int) (xSurTerrain / tailleTuile);
        int caseY = (int) (ySurTerrain / tailleTuile);
        int[][] grille = env.getGrille();

        // Vérification des limites de la grille et de la disponibilité de la case
        if (caseY >= 0 && caseY < grille.length && caseX >= 0 && caseX < grille[0].length) {
            if (grille[caseY][caseX] == 0) {
                return placerTour(caseX, caseY);
            }
        }
        return null;
    }

    private Tour placerTour(int caseX, int caseY) {
        Tour tourAPlacer = null;

        for (Map.Entry<Tour, Integer> association : env.getTourVersIndexInventaire().entrySet()) {
            if (association.getValue() == indexInventaireActu) {
                tourAPlacer = association.getKey();
            }
        }

        if (tourAPlacer != null) {
            env.enregistrerTourPosee(tourAPlacer, caseX, caseY, indexInventaireActu);

            modePlacementTour = false;
            tourCliquee = null;
        }

        return tourAPlacer;
    }

    public void annulerPlacement() {
        modePlacementTour = false;
        tourCliquee = null;
        indexInventaireActu = -1;
    }

    public Tour trouverTourPosee(int index) {
        for (Map.Entry<Tour, Integer> association : env.getTourVersIndexInventaire().entrySet()) {
            if (association.getValue() == index && env.getToursPosees().contains(association.getKey())) {
                return association.getKey();
            }
        }
        return null;
    }

    public void reinitialiser() {
        this.modePlacementTour = false;
        this.tourCliquee = null;
        this.indexInventaireActu = -1;
    }

    public boolean estModePlacementTour() { return modePlacementTour; }
    public int getIndexInventaireActu() { return indexInventaireActu; }
}
