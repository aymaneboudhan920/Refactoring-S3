package universite_paris8.iut.aboudhan.saes2javafx.modele.tour;

public class TourScientifique extends Tour {
    public static int prixAchat = 25;

    public TourScientifique(double x, double y) {
        super(x, y, 136, 10, 1.0, prixAchat, "/universite_paris8/iut/aboudhan/saes2javafx/vue/tour_scientifique.png",
                new AttaqueProjectile(8.0, TypeTour.SCIENTIFIQUE));
    }
}
