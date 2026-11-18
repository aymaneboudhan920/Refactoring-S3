package universite_paris8.iut.aboudhan.saes2javafx.modele.microbe;
import universite_paris8.iut.aboudhan.saes2javafx.modele.tour.Tour;

public class ComportementRage implements ComportementMicrobe {

    @Override
    public void apresDegats(Microbe microbe, Tour tour) {
        if (microbe.estEnrage()) {
            microbe.devenirEnrage();
        }
    }
}