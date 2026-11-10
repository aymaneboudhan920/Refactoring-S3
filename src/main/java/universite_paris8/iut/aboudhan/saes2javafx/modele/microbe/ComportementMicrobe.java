package universite_paris8.iut.aboudhan.saes2javafx.modele.microbe;
import universite_paris8.iut.aboudhan.saes2javafx.modele.tour.Tour;

public interface ComportementMicrobe {

    default void mettreAJourVitesse(Microbe microbe) {
    }

    default double vitesseDeReprise(Microbe microbe) {
        return microbe.vitesseDeBase;
    }

    default void apresDegats(Microbe microbe, Tour tour) {
    }

    static ComportementMicrobe pour(String type) {
        if (type == null) {
            return new ComportementNormal();
        }
        return switch (type) {
            case "VARICELLE" -> new ComportementVaricelle();
            case "VIH"       -> new ComportementVIH();
            case "RAGE"      -> new ComportementRage();
            default          -> new ComportementNormal();
        };
    }
}