package universite_paris8.iut.aboudhan.saes2javafx.modele.microbe;
import universite_paris8.iut.aboudhan.saes2javafx.modele.tour.Tour;

public class ComportementVIH implements ComportementMicrobe {
    private static final double DUREE_ETOURDISSEMENT = 0.1;

    @Override
    public void apresDegats(Microbe microbe, Tour tour) {
        if (tour != null) {
            tour.etourdir(DUREE_ETOURDISSEMENT);
            microbe.marquerEclair(tour);
        }
    }
}