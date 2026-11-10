package universite_paris8.iut.aboudhan.saes2javafx.modele.microbe;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Waypoint;
import universite_paris8.iut.aboudhan.saes2javafx.modele.tour.Tour;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.ArrayList;
import java.util.List;

public class Microbe {
    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();
    public double vitesseDeBase;
    private double vitesseActu;
    private final DoubleProperty pv = new SimpleDoubleProperty();
    public double pvMax;
    private final int recompense;
    public int infection;
    private Waypoint waypointCible;
    private String type;
    private boolean estGele = false;
    private boolean estRalenti = false;
    private boolean doitAfficherEclair = false;
    private final List<Tour> tourAReset = new ArrayList<>();

    private ComportementMicrobe comportement;

    public Microbe(double v, double pm, int r, int i, String type, Waypoint waypointDepart){
        this.vitesseDeBase = v;
        this.vitesseActu = v;
        this.pv.set(pm);
        this.pvMax = pm;
        this.recompense = r;
        this.infection = i;
        this.type = type;
        this.comportement = ComportementMicrobe.pour(type);

        if (waypointDepart != null) {
            this.x.set(waypointDepart.getX());
            this.y.set(waypointDepart.getY());
            this.waypointCible = waypointDepart.obtenirProchainWaypoint();
        } else {
            this.x.set(0);
            this.y.set(0);
            this.waypointCible = null;
        }
    }

    public void deplacer(){
        this.comportement.mettreAJourVitesse(this);
        if (this.estGele || this.vitesseActu == 0 || this.waypointCible == null) return;

        double diffX = this.waypointCible.getX() - this.getX();
        double diffY = this.waypointCible.getY() - this.getY();
        double distance = Math.sqrt(diffX * diffX + diffY * diffY);

        if (distance > this.vitesseActu && distance > 0){
            this.setX(this.getX() + (diffX / distance) * this.vitesseActu);
            this.setY(this.getY() + (diffY / distance) * this.vitesseActu);
        } else {
            this.setX(this.waypointCible.getX());
            this.setY(this.waypointCible.getY());
            this.waypointCible = waypointCible.obtenirProchainWaypoint();

            if (waypointCible != null){
                double distanceSaut = Math.sqrt(
                        Math.pow(waypointCible.getX() - this.getX(), 2) +
                                Math.pow(waypointCible.getY() - this.getY(), 2)
                );
                if (distanceSaut > 70) {
                    this.setX(waypointCible.getX());
                    this.setY(waypointCible.getY());
                }
            }
        }
    }

    public void appliquerRalentissement(boolean ralenti) {
        if (this.estGele) return;
        this.estRalenti = ralenti;
        this.vitesseActu = ralenti ? this.vitesseDeBase / 2 : this.vitesseDeBase;
    }

    public double getRatioPV(){ return pv.get() / pvMax; }
    public boolean estMort(){ return this.pv.get() <= 0; }
    public DoubleProperty pvProperty() { return this.pv; }
    public int getRecompense() { return recompense; }
    public int getInfection() { return infection; }
    public DoubleProperty xProperty() { return this.x; }
    public DoubleProperty yProperty() { return this.y; }
    public String getType() { return this.type; }
    public Waypoint getWaypointCible() { return this.waypointCible; }
    public double getVitesseActu() { return vitesseActu; }

    public void setVitesseActu(double valeur) {
        this.vitesseActu = valeur;
        if (valeur == 0) this.estGele = true;
    }

    public double getX() { return x.get(); }
    public void setX(double x) { this.x.set(x); }

    public double getY() { return y.get(); }
    public void setY(double y) { this.y.set(y); }

    public void reinitialiserVitesse() {
        this.estGele = false;
        this.vitesseActu = this.comportement.vitesseDeReprise(this);
    }

    public void perdreVie(double degats, Tour tour){
        this.pv.set(Math.max(0, this.pv.get() - degats));
        this.comportement.apresDegats(this, tour);
    }

    public boolean doitAfficherEclair() { return this.doitAfficherEclair; }
    public List<Tour> getTourAReset() { return this.tourAReset; }

    public void resetEclair() {
        this.doitAfficherEclair = false;
        this.tourAReset.clear();
    }

    public boolean estEnrage() { return this.getRatioPV() <= 0.25; }

    boolean estGele() { return this.estGele; }

    boolean estRalenti() { return this.estRalenti; }

    void fixerVitesseActu(double valeur) { this.vitesseActu = valeur; }

    void marquerEclair(Tour tour) {
        this.doitAfficherEclair = true;
        this.tourAReset.add(tour);
    }

    void devenirEnrage() {
        this.type = "RAGE_ENRAGE";
        this.vitesseDeBase = 2.0;
        this.vitesseActu = 2.0;
        this.comportement = new ComportementNormal();
    }
}