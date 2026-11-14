package universite_paris8.iut.aboudhan.saes2javafx.modele.tour;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;

public interface StratAttaque {

    public void attaquer(Environnement env, Tour tour);
    void mettreAJour(Tour tour, double t);   // appelé à chaque tick
    void reinitialiser();
}
