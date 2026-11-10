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

        if (estCaseConstruisible(caseX, caseY)) {
            return placerTour(caseX, caseY);
        }

        return null;
    }

    private boolean estCaseConstruisible(int caseX, int caseY) {
        int[][] grille = env.getGrille();
        boolean dansLesLimites = caseY >= 0 && caseY < grille.length && caseX >= 0 && caseX < grille[0].length;
        return dansLesLimites && grille[caseY][caseX] == 0;
    }

    private Tour placerTour(int caseX, int caseY) {
        Tour tourAPlacer = trouverTourParIndex(indexInventaireActu);

        if (tourAPlacer != null) {
            env.enregistrerTourPosee(tourAPlacer, caseX, caseY, indexInventaireActu);
            annulerPlacement();
        }
        return tourAPlacer;
    }

    public Tour trouverTourPosee(int index) {
        Tour tour = trouverTourParIndex(index);
        if (tour != null && env.getToursPosees().contains(tour)) {
            return tour;
        }
        return null;
    }

    private Tour trouverTourParIndex(int index) {
        for (Map.Entry<Tour, Integer> entry : env.getTourVersIndexInventaire().entrySet()) {
            if (entry.getValue() == index) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void annulerPlacement() {
        modePlacementTour = false;
        tourCliquee = null;
        indexInventaireActu = -1;
    }

    public void reinitialiser() {
        this.modePlacementTour = false;
        this.tourCliquee = null;
        this.indexInventaireActu = -1;
    }

    public boolean estModePlacementTour() { return modePlacementTour; }
    public int getIndexInventaireActu() { return indexInventaireActu; }
}
