package universite_paris8.iut.aboudhan.saes2javafx.modele.tour;

public enum TypeTour {
    SCIENTIFIQUE("scientifique"),
    CHIMISTE("chimiste"),
    SCANNER("scanner"),
    RAYON_X("rayon_x");

    private final String identifiant;

    TypeTour(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public static TypeTour depuisIdentifiant(String id) {
        if (id == null) {
            return null;
        }
        for (TypeTour t : TypeTour.values()) {
            if (t.identifiant.equalsIgnoreCase(id)) {
                return t;
            }
        }
        return null;
    }
}