package universite_paris8.iut.aboudhan.saes2javafx.modele.tour;

public class TourChimiste extends Tour {
    public static final int prixAchat = 50;
    public static final int PORTEE = 102;

    public TourChimiste(double x, double y) {
        super(x, y, PORTEE, 15, 0.33, prixAchat, "/universite_paris8/iut/aboudhan/saes2javafx/vue/tour_chimiste.png",
                new AttaqueProjectile(5.0, TypeTour.CHIMISTE));
    }
}