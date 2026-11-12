package universite_paris8.iut.aboudhan.saes2javafx.modele.tour;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.microbe.Microbe;

public abstract class Tour {
    private double x, y, degats, vitesseTir;
    private final int portee;
    private int prix, niveau;
    private String nomImage;
    private double tempsRechargeRestant = 0.0;
    private double multiplicateurVitesse = 1.0;
    private double tempsEtourdissementRestant = 0;
    private StratAttaque stratAttaque;

    public Tour(double x, double y, int portee, double degats, double vitesseTir, int prix, String nomImage, StratAttaque stratAttaque) {
        this.x = x;
        this.y = y;
        this.portee = portee;
        this.degats = degats;
        this.vitesseTir = vitesseTir;
        this.prix = prix;
        this.nomImage = nomImage;
        this.niveau = 1;
        this.stratAttaque = stratAttaque;
    }

    public void attaquer(Environnement env){
        this.stratAttaque.attaquer(env, this);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getPortee() { return portee; }
    public double getDegats() { return degats; }
    public double getVitesseTir() { return vitesseTir; }
    public int getPrix() { return prix; }
    public String getNomImage() { return nomImage; }
    public int getNiveau() { return niveau; }
    public boolean estEtourdie(){ return tempsEtourdissementRestant > 0; }
    public double getTempsEtourdissementRestant() {
        return this.tempsEtourdissementRestant;
    }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setDegats(double degats) { this.degats = degats; }
    public void incrementerNiveau() { this.niveau++; }

    public void setMultiplicateurVitesse(double coeff) { this.multiplicateurVitesse = coeff; }

    public void mettreAJourRecharge(double t) {
        if (this.estEtourdie()) {
            tempsEtourdissementRestant -= t;
            return;
        }
        if (this.tempsRechargeRestant > 0) {
            this.tempsRechargeRestant -= t;
            if (this.tempsRechargeRestant < 0) {
                this.tempsRechargeRestant = 0;
            }
        }
    }

    public void etourdir(double duree) {
        this.tempsEtourdissementRestant = duree;
//        this.reinitialiserAttaque();
    }

    public boolean peutAttaquer() {
        return this.tempsRechargeRestant <= 0 && this.tempsEtourdissementRestant <= 0;
    }

    public void recharger() {
        this.tempsRechargeRestant = (1.0 / (this.vitesseTir * this.multiplicateurVitesse));
    }

    public int calculerPrixAmelioration() {
        return (int) (this.prix * Math.pow(1.5, this.niveau));
    }

    public int calculerValeurVente() {
        return (int) ((this.prix / 2) * Math.pow(1.5, this.niveau - 1));
    }

    public boolean peutEtreAmelioree() {
        return this.niveau < 5;
    }

    public void ameliorer() {
        this.incrementerNiveau();
        this.setDegats(this.getDegats() * 1.5);
        this.vitesseTir = this.vitesseTir * 1.1;
    }

//    public abstract void reinitialiserAttaque();

    public Projectile initProjectile (Microbe cible, double vitess){
        return new Projectile(this.getX(), this.getY(), vitess, cible, "CHIMISTE", this.getDegats(), this);
    }
}
