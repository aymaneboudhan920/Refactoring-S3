package universite_paris8.iut.aboudhan.saes2javafx.modele.microbe;

public enum TypeMicrobe {
    RHINOVIRUS("RHINOVIRUS"),
    NOROVIRUS("NOROVIRUS"),
    STREPTOCOQUE("STREPTOCOQUE"),
    INFLUENZA("INFLUENZA"),
    VARICELLE("VARICELLE"),
    COVID("COVID"),
    VIH("VIH"),
    TUBERCULOSE("TUBERCULOSE"),
    PESTE("PESTE"),
    RAGE("RAGE"),
    RAGE_ENRAGE("RAGE_ENRAGE"),
    VARIOLE("VARIOLE");

    private final String nom;

    TypeMicrobe(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public static TypeMicrobe depuisNom(String nom) {
        if (nom == null) {
            return null;
        }
        for (TypeMicrobe tm : TypeMicrobe.values()) {
            if (tm.nom.equalsIgnoreCase(nom)) {
                return tm;
            }
        }
        return null;


    }
}