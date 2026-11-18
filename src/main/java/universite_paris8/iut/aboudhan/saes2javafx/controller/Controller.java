
package universite_paris8.iut.aboudhan.saes2javafx.controller;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.*;

import javafx.fxml.Initializable;
import javafx.util.Duration;

import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Configuration;
import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.microbe.Microbe;
import universite_paris8.iut.aboudhan.saes2javafx.modele.microbe.TypeMicrobe;
import universite_paris8.iut.aboudhan.saes2javafx.modele.microbe.Vague;
import universite_paris8.iut.aboudhan.saes2javafx.modele.tour.*;
import universite_paris8.iut.aboudhan.saes2javafx.vue.bouton.*;
import universite_paris8.iut.aboudhan.saes2javafx.vue.ecran.DefaiteVue;
import universite_paris8.iut.aboudhan.saes2javafx.vue.ecran.TerrainVue;
import universite_paris8.iut.aboudhan.saes2javafx.vue.ecran.VagueGagneeVue;
import universite_paris8.iut.aboudhan.saes2javafx.vue.ecran.VictoireVue;
import universite_paris8.iut.aboudhan.saes2javafx.vue.microbe.GestionnaireEffets;
import universite_paris8.iut.aboudhan.saes2javafx.vue.microbe.MicrobeVue;
import universite_paris8.iut.aboudhan.saes2javafx.vue.tour.ProjectileVue;
import universite_paris8.iut.aboudhan.saes2javafx.vue.tour.TourVue;

public class Controller implements Initializable {
    @FXML public Pane conteneurPrincipal;
    @FXML public TilePane grilleJeu;

    @FXML public Button boutonStart;
    @FXML private Button boutonShop;
    @FXML private Button boutonTuto;
    @FXML private Button boutonParametres;
    @FXML private Button boutonInfo;

    @FXML private Label labelArgent;
    @FXML private Label labelInfectes;
    @FXML private Label labelVague;

    @FXML private Button btnPotionSoin;
    @FXML private Button btnPotionRage;
    @FXML private Button btnPotionGel;

    @FXML private Label labelPotionSoin;
    @FXML private Label labelPotionRage;
    @FXML private Label labelPotionGel;
    @FXML private Button btnPlusShop;

    @FXML private GridPane grilleInventaire;

    @FXML private StackPane conteneurMenuPrincipal;
    @FXML private BorderPane conteneurJeu;
    @FXML private VBox pageAccueil;
    @FXML private TextField champPseudo;
    @FXML private Label labelPseudoJoueur;

    public final Environnement env = new Environnement();
    public boolean jeuDemarre = false;

    private final java.util.Map<Microbe, MicrobeVue> vuesMicrobes = new java.util.HashMap<>();
    public final java.util.Map<Tour, TourVue> vuesTours = new java.util.HashMap<>();
    private final java.util.Map<Projectile, ProjectileVue> vuesProjectiles = new java.util.HashMap<>();

    private AnimationTimer gameLoop;
    private Timeline timeline;

    private GestionnaireEffets gestionnaireEffets;

    private Configuration configJeu;

    private ControllerShop controllerShop;
    private ControllerPotion controllerPotion;
    private ControllerTour controllerTour;
    private ControllerInfo controllerInfo;
    private ControllerAide controllerAide;
    private ControllerParametres controllerParametres;
    private ControllerParametres controllerParametresMenu;
    private ControllerCatalogue controllerCatalogue;
    private ControllerRegles controllerRegles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> conteneurMenuPrincipal.requestFocus());
        this.configJeu = Configuration.getInstance();
        this.gestionnaireEffets = new GestionnaireEffets(conteneurPrincipal);

        TerrainVue terrainVue = new TerrainVue(env.getGrille(), env.getTailleTuile());
        terrainVue.dessinerTerrain(grilleJeu);

        env.getGestionnaireVagues().initialiserVagues(env);

        configurerBindings();
        updateCompteurs();
        mettreAJourLabelVague();
        creerGameLoop();
        creerTimeline();

        this.controllerPotion = new ControllerPotion(env, this);
        this.controllerPotion.setBoutons(btnPotionSoin, btnPotionRage, btnPotionGel);

        this.controllerTour = new ControllerTour(env, conteneurPrincipal, grilleJeu, grilleInventaire, this);
        this.controllerTour.initialiser();

        this.controllerShop = new ControllerShop(env, conteneurPrincipal, this, controllerPotion, controllerTour);

        this.controllerInfo = new ControllerInfo(this, conteneurPrincipal);
        this.controllerAide = new ControllerAide(this, conteneurPrincipal, configJeu);
        this.controllerParametres = new ControllerParametres(this, conteneurPrincipal, configJeu, true);
        this.controllerParametresMenu = new ControllerParametres(this, conteneurMenuPrincipal, configJeu, false);
        this.controllerCatalogue = new ControllerCatalogue(this, conteneurPrincipal);
        this.controllerRegles = new ControllerRegles(this, (Pane) conteneurMenuPrincipal.getParent());

        pageAccueil.setVisible(true);
        conteneurJeu.setVisible(false);

    }

    private void configurerBindings() {
        labelArgent.textProperty().bind(env.argentProperty().asString());
        labelInfectes.textProperty().bind(env.gensInfectesProperty().asString());
        labelPotionSoin.textProperty().bind(env.nbPotionSoinProperty().asString());
        labelPotionRage.textProperty().bind(env.nbPotionRageProperty().asString());
        labelPotionGel.textProperty().bind(env.nbPotionGelProperty().asString());
    }

    public void verrouillerInterface(boolean verrouiller) {
        if (boutonStart != null) boutonStart.setDisable(verrouiller || jeuDemarre);
        if (boutonShop != null) boutonShop.setDisable(verrouiller);
        if (boutonTuto != null) boutonTuto.setDisable(verrouiller);
        if (boutonParametres != null) boutonParametres.setDisable(verrouiller);
        if (boutonInfo != null) boutonInfo.setDisable(verrouiller);

        if (btnPotionSoin != null) btnPotionSoin.setDisable(verrouiller);
        if (btnPotionRage != null) btnPotionRage.setDisable(verrouiller);
        if (btnPotionGel != null) btnPotionGel.setDisable(verrouiller);
        if (btnPlusShop != null) btnPlusShop.setDisable(verrouiller);

        if (controllerTour != null) controllerTour.verrouiller(verrouiller);
    }

    public void updateCompteurs() {
        int infectes = env.getGensInfectes();
        labelInfectes.getStyleClass().removeAll("compteur-danger-faible", "compteur-danger-fort", "compteur-defaite");
        if (infectes >= 20 && infectes < 50) labelInfectes.getStyleClass().add("compteur-danger-faible");
        if (infectes >= 50 && infectes < 70) labelInfectes.getStyleClass().add("compteur-danger-fort");
        if (infectes >= 70) labelInfectes.getStyleClass().add("compteur-defaite");
    }

    @FXML
    private void actionBoutonStart() {
        if (!jeuDemarre && boutonStart != null) {
            jeuDemarre = true;

            ScaleTransition st = new ScaleTransition(Duration.millis(100), boutonStart);
            st.setToX(0.95);
            st.setToY(0.95);
            st.setAutoReverse(true);
            st.setCycleCount(2);

            st.setOnFinished(e -> {
                boutonStart.setDisable(true);
                if (configJeu != null) {
                    configJeu.changerDeMusique("musiqueJeu.wav");
                }
                creerTimeline();
                if (timeline != null) {
                    gameLoop.start();
                    timeline.play();
                } else {
                    jeuDemarre = false;
                    boutonStart.setDisable(false);
                }
            });
            st.play();
        }
    }

    public void mettreEnPause() {
        if (jeuDemarre) {
            gameLoop.stop();
            timeline.pause();
        }
    }

    public void reprendreJeu() {
        if (jeuDemarre) {
            gameLoop.start();
            timeline.play();
        }
    }

    public void bloquerJeu() {
        mettreEnPause();
        verrouillerInterface(true);
    }

    public void debloquerJeu() {
        verrouillerInterface(false);
        reprendreJeu();
    }

    @FXML
    private void actionBoutonShop() {
        controllerShop.actionBoutonShop();
    }

    private void mettreAJourLabelVague() {
        int numActu = env.getGestionnaireVagues().getNumVagueActu() + 1;
        labelVague.setText("VAGUE " + numActu);
    }

    private void creerTimeline() {
        Vague vagueActuelle = env.getGestionnaireVagues().getVagueActuelle();
        if (vagueActuelle == null) return;

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(vagueActuelle.getTempsIntervalle()), event -> {
                    if (env.isMicrobesGeles()) {
                        return;
                    }

                    List<Microbe> fileAttente = vagueActuelle.getFileAttenteMicrobes();

                    if (!fileAttente.isEmpty()) {
                        Microbe prochainMicrobe = fileAttente.remove(0);
                        env.getMicrobesActifs().add(prochainMicrobe);

                        MicrobeVue vue = new MicrobeVue(
                                prochainMicrobe.getType(),
                                prochainMicrobe.getX(),
                                prochainMicrobe.getY(),
                                prochainMicrobe.getRatioPV()
                        );

                        vue.getBarreDeVie().progressProperty().bind(
                                prochainMicrobe.pvProperty().divide(prochainMicrobe.pvMax)
                        );

                        vue.translateXProperty().bind(prochainMicrobe.xProperty());
                        vue.translateYProperty().bind(prochainMicrobe.yProperty().subtract(5));

                        vuesMicrobes.put(prochainMicrobe, vue);
                        conteneurPrincipal.getChildren().add(vue);
                    } else {
                        timeline.stop();
                    }
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    private void creerGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (env.verifierDefaite()) {
                    stopperJeuDefaite();
                    return;
                }

                if (verifierFinVague()) {
                    gererFinVague();
                    return;
                }

                boolean ennemiSorti = env.updateMicrobes();
                if (ennemiSorti) {
                    updateCompteurs();
                }

                mettreAJourToursEtProjectiles(0.016);
                gererCycleEtEclairsMicrobes();
            }
        };
    }

    private void stopperJeuDefaite() {
        gameLoop.stop();
        if (timeline != null) timeline.stop();
        updateCompteurs();
        verrouillerInterface(true);
        afficherEcranDefaite();
    }

    private boolean verifierFinVague() {
        Vague vagueActuelle = env.getGestionnaireVagues().getVagueActuelle();
        return vagueActuelle != null && vagueActuelle.getFileAttenteMicrobes().isEmpty()
                && env.getMicrobesActifs().isEmpty() && jeuDemarre;
    }

    private void gererFinVague() {
        jeuDemarre = false;
        gameLoop.stop();
        cacherRadarsScanners();

        Vague vagueActuelle = env.getGestionnaireVagues().getVagueActuelle();
        env.ajouterArgent(vagueActuelle.getBonus());
        updateCompteurs();

        int numVagueTerminee = env.getGestionnaireVagues().getNumVagueActu() + 1;

        if (env.getGestionnaireVagues().estDerniereVague()) {
            verrouillerInterface(true);
            if (configJeu != null) configJeu.changerDeMusique("musiqueVictoireFinale.wav");
            afficherEcranVictoire();
        } else {
            if (boutonStart != null) boutonStart.setDisable(true);
            VagueGagneeVue ecranInterVague = new VagueGagneeVue(conteneurPrincipal, grilleJeu, numVagueTerminee,
                    () -> {
                        env.getGestionnaireVagues().AugmenterVague();
                        mettreAJourLabelVague();
                        verrouillerInterface(false);
                        if (boutonStart != null) boutonStart.setDisable(false);
                    });

            verrouillerInterface(true);
            ecranInterVague.afficherSur(conteneurPrincipal);
            if (configJeu != null) configJeu.changerDeMusique("musiqueInterVague.wav");
        }
    }

    private void mettreAJourToursEtProjectiles(double deltaTps) {
        List<Tour> toursEnJeu = new ArrayList<>(vuesTours.keySet());
        for (Tour tour : toursEnJeu) {
            tour.mettreAJour(env, deltaTps);
            if (tour instanceof TourScanner) {
                TourVue vueTour = vuesTours.get(tour);
                if (vueTour != null) vueTour.rafraichirOndeScanner((TourScanner) tour);
            }
        }

        env.mettreAJourProjectiles();
        rafraichirProjectiles();
    }

    private void gererCycleEtEclairsMicrobes() {
        boolean unMicrobeEstMort = false;

        for (Microbe m : env.getMicrobesActifs()) {
            if (m.estMort()) {
                env.ajouterArgent(m.getRecompense());
                unMicrobeEstMort = true;
            }
            MicrobeVue imageVue = vuesMicrobes.get(m);
            if (imageVue != null && m.getType() == TypeMicrobe.RAGE_ENRAGE) {
                imageVue.changerImage(TypeMicrobe.RAGE_ENRAGE);
            }
            if (m.getType() == TypeMicrobe.VIH && m.doitAfficherEclair()) {
                for (int i = 0; i < m.getTourAReset().size(); i++) {
                    Tour tourCible = m.getTourAReset().get(i);
                    if (tourCible != null) {
                        this.gestionnaireEffets.afficherEclairFlash(m.getX(), m.getY(), tourCible.getX(), tourCible.getY());
                    }
                }
                m.resetEclair();
            }
        }

        if (unMicrobeEstMort) {
            updateCompteurs();
        }

        env.getMicrobesActifs().removeIf(Microbe::estMort);

        vuesMicrobes.keySet().removeIf(m -> {
            if (!env.getMicrobesActifs().contains(m)) {
                MicrobeVue imageVue = vuesMicrobes.get(m);
                if (imageVue != null) conteneurPrincipal.getChildren().remove(imageVue);
                return true;
            }
            return false;
        });
    }

    private void cacherRadarsScanners() {
        for (Map.Entry<Tour, TourVue> entree : vuesTours.entrySet()) {
            if (entree.getKey() instanceof TourScanner scanner) {
                TourVue vue = entree.getValue();

                scanner.reinitialiserAttaque();
                vue.rafraichirOndeScanner(scanner);
            }
        }
    }

    private void rafraichirProjectiles() {
        for (Projectile p : env.getProjectilesActifs()) {
            if (!vuesProjectiles.containsKey(p)) {
                double tourX = 0;
                double tourY = 0;

                List<Tour> listeTours = new ArrayList<>(vuesTours.keySet());
                boolean trouve = false;

                for (int i = 0; i < listeTours.size() && !trouve; i++) {
                    Tour t = listeTours.get(i);
                    if (p.getType().equals("RAYON_X") && t.getX() == p.getX() && t.getY() == p.getY()) {
                        tourX = t.getX();
                        tourY = t.getY();
                        trouve = true;
                    }
                }

                ProjectileVue pVue = new ProjectileVue(p, tourX, tourY, env);
                vuesProjectiles.put(p, pVue);
                conteneurPrincipal.getChildren().add(pVue);
            } else {
                vuesProjectiles.get(p).rafraichirVue(p.getX(), p.getY());
            }
        }

        List<Projectile> projectilesAEnlever = new ArrayList<>();
        for (Projectile p : vuesProjectiles.keySet()) {
            ProjectileVue vue = vuesProjectiles.get(p);
            if (vue.doitEtreRetire()) {
                projectilesAEnlever.add(p);
            }
        }

        for (Projectile p : projectilesAEnlever) {
            ProjectileVue vueRetiree = vuesProjectiles.remove(p);
            if (vueRetiree != null) {
                conteneurPrincipal.getChildren().remove(vueRetiree);
            }
        }
    }

    public void spawnMicrobe(Microbe m){
        MicrobeVue vue2 = new MicrobeVue(m.getType(), m.getX(), m.getY(), m.getRatioPV());
        vue2.getBarreDeVie().progressProperty().bind( m.pvProperty().divide(m.pvMax));
        vuesMicrobes.put(m, vue2);
        conteneurPrincipal.getChildren().add(vue2);
    }

    private void afficherEcranDefaite() {
        DefaiteVue ecranDefaite = new DefaiteVue(conteneurPrincipal, grilleJeu, () -> {
            env.reinitialiser();
            reinitialiserJeuVisuel();
        });
        ecranDefaite.afficherSur(conteneurPrincipal);
    }

    private void afficherEcranVictoire() {
        VictoireVue ecranVictoire = new VictoireVue(conteneurPrincipal, grilleJeu, () -> {
            env.reinitialiser();
            reinitialiserJeuVisuel();
            mettreAJourLabelVague();
        });
        ecranVictoire.afficherSur(conteneurPrincipal);
    }

    private void reinitialiserJeuVisuel() {
        for (MicrobeVue vueM : vuesMicrobes.values()) {
            conteneurPrincipal.getChildren().remove(vueM);
        }
        vuesMicrobes.clear();

        for (TourVue vueT : vuesTours.values()) {
            conteneurPrincipal.getChildren().remove(vueT);
        }
        vuesTours.clear();

        conteneurPrincipal.getChildren().clear();
        conteneurPrincipal.getChildren().add(grilleJeu);

        TerrainVue terrainVue = new TerrainVue(env.getGrille(), env.getTailleTuile());
        terrainVue.dessinerTerrain(grilleJeu);

        jeuDemarre = false;
        verrouillerInterface(false);

        if (controllerTour != null) controllerTour.reinitialiser();

        if (configJeu != null) {
            configJeu.changerDeMusique("musiqueMenu.wav");
        }

        updateCompteurs();
        creerGameLoop();
    }

    @FXML
    private void actionBoutonInfo() {
        controllerInfo.ouvrir();
    }

    @FXML
    private void actionBoutonHelp() {
        controllerAide.ouvrir();
    }

    @FXML
    private void actionBoutonParametres() {
        controllerParametres.ouvrir();
    }

    @FXML
    private void AfficherParametresMenu() {
        controllerParametresMenu.ouvrir();
    }

    @FXML
    private void actionUtiliserSoin() { controllerPotion.utiliserSoin(); }

    @FXML
    private void actionUtiliserRage() { controllerPotion.utiliserRage(); }

    @FXML
    private void actionUtiliserGel() { controllerPotion.utiliserGel(); }

    @FXML
    private void LancerJeu() {
        String pseudoSaisi = champPseudo.getText().trim();
        if (!pseudoSaisi.isEmpty()) {
            labelPseudoJoueur.setText(pseudoSaisi);
        } else {
            labelPseudoJoueur.setText("Joueur Anonyme");
        }

        conteneurMenuPrincipal.setVisible(false);
        conteneurJeu.setVisible(true);
    }

    @FXML
    private void AfficherReglesMenu() {
        controllerRegles.ouvrir();
    }

    @FXML
    private void CatalogueAction() {
        controllerCatalogue.basculer();
    }
}
 