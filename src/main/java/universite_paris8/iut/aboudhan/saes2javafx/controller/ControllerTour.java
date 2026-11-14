package universite_paris8.iut.aboudhan.saes2javafx.controller;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.tour.*;
import universite_paris8.iut.aboudhan.saes2javafx.vue.tour.InventaireVue;
import universite_paris8.iut.aboudhan.saes2javafx.vue.tour.PanneauActionVue;
import universite_paris8.iut.aboudhan.saes2javafx.vue.tour.TourVue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ControllerTour {

    public static final int NB_CASES_INVENTAIRE = 8;

    private final Environnement env;
    private final Pane conteneurPrincipal;
    private final TilePane grilleJeu;
    private final GridPane grilleInventaire;
    private final Controller controllerPrincipal;

    public Inventaire inventaireModele;
    public InventaireVue inventaireVue;

    public final List<Button> boutonsInventaire = new ArrayList<>();
    private final List<ImageView> imagesInventaire = new ArrayList<>();
    private final List<Label> labelsInventaire = new ArrayList<>();

    private PanneauActionVue panneauActionTour;
    private Tour tourEnInspection = null;
    private int indexTourInspectee = -1;

    private GestionnaireTours gestionnaireTours;

    public ControllerTour(Environnement env, Pane conteneurPrincipal, TilePane grilleJeu,
                          GridPane grilleInventaire, Controller controllerPrincipal) {
        this.env = env;
        this.conteneurPrincipal = conteneurPrincipal;
        this.grilleJeu = grilleJeu;
        this.grilleInventaire = grilleInventaire;
        this.controllerPrincipal = controllerPrincipal;
    }

    public void initialiser() {
        this.inventaireModele = new Inventaire(NB_CASES_INVENTAIRE);
        rassemblerListesInventaire();
        this.inventaireVue = new InventaireVue(boutonsInventaire, imagesInventaire, labelsInventaire);
        this.gestionnaireTours = new GestionnaireTours(this.env);
        configurerPanneauAction();
        configurerEvenementsInventaire();
        configurerEvenementsTerrain();
    }

    private void rassemblerListesInventaire() {
        for (int i = 0; i < NB_CASES_INVENTAIRE; i++) {
            ImageView image = new ImageView();
            image.setFitHeight(50);
            image.setFitWidth(50);
            image.setPickOnBounds(true);
            image.setPreserveRatio(true);
            VBox.setMargin(image, new Insets(5));

            Label label = new Label("VIDE");
            label.getStyleClass().add("vague");
            label.setAlignment(Pos.CENTER);
            label.setContentDisplay(ContentDisplay.CENTER);
            label.setTextAlignment(TextAlignment.CENTER);
            label.setTextFill(Color.WHITE);
            label.setFont(new Font("Impact", 15));

            VBox contenuCase = new VBox(image, label);
            contenuCase.setAlignment(Pos.BOTTOM_CENTER);

            Button caseInventaire = new Button();
            caseInventaire.setMnemonicParsing(false);
            caseInventaire.getStyleClass().add("btn-inventaire-tour");
            caseInventaire.setGraphic(contenuCase);

            grilleInventaire.add(caseInventaire, i, 0);
            GridPane.setHalignment(caseInventaire, HPos.CENTER);
            GridPane.setValignment(caseInventaire, VPos.CENTER);

            boutonsInventaire.add(caseInventaire);
            imagesInventaire.add(image);
            labelsInventaire.add(label);
        }
    }

    private void configurerPanneauAction() {
        this.panneauActionTour = new PanneauActionVue();
        this.panneauActionTour.setVisible(false);
        conteneurPrincipal.getChildren().add(panneauActionTour);

        this.panneauActionTour.getBtnAmeliorer().setOnAction(e -> gererAmelioration());
        this.panneauActionTour.getBtnRanger().setOnAction(e -> {
            if (this.panneauActionTour.getBtnRanger().getText().equals("Ranger")) {
                gererRappel();
            } else if (this.panneauActionTour.getBtnRanger().getText().equals("Poser") && this.indexTourInspectee != -1) {
                String typeTour = inventaireModele.getTourCase(this.indexTourInspectee);
                gestionnaireTours.gererClicInventaire(this.indexTourInspectee, typeTour);
                this.panneauActionTour.setVisible(false);
            }
        });
        this.panneauActionTour.getBtnVendre().setOnAction(e -> gererVente());
        this.panneauActionTour.getBtnFermer().setOnAction(e -> masquerPanneau());
    }

    private void configurerEvenementsInventaire() {
        for (int i = 0; i < boutonsInventaire.size(); i++) {
            final int indexActuel = i;
            Button btn = boutonsInventaire.get(i);
            btn.setDisable(true);

            btn.setOnMouseClicked(event -> {
                if (event.getButton() != MouseButton.PRIMARY) return;

                if (this.indexTourInspectee == indexActuel) {
                    masquerPanneau();
                    return;
                }

                String typeTour = inventaireModele.getTourCase(indexActuel);
                boolean estUneTourExistante = gestionnaireTours.gererClicInventaire(indexActuel, typeTour);

                for (Map.Entry<Tour, Integer> entree : env.getTourVersIndexInventaire().entrySet()) {
                    if (entree.getValue() == indexActuel) {
                        this.tourEnInspection = entree.getKey();
                    }
                }

                if (!estUneTourExistante) {
                    gestionnaireTours.annulerPlacement();
                    if (this.tourEnInspection == null && typeTour != null && !typeTour.isEmpty()) {
                        this.tourEnInspection = creerTourDepuisType(typeTour);
                        if (this.tourEnInspection != null) {
                            env.getTourVersIndexInventaire().put(this.tourEnInspection, indexActuel);
                        }
                    }
                }

                if (this.tourEnInspection != null) {
                    this.indexTourInspectee = indexActuel;
                    afficherEtPositionnerPanneauAction(btn, estUneTourExistante);
                }
            });
        }
    }

    private void configurerEvenementsTerrain() {
        conteneurPrincipal.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                gestionnaireTours.annulerPlacement();
                masquerPanneau();
                return;
            }

            if (gestionnaireTours.estModePlacementTour() && this.tourEnInspection != null) {
                double xSurGrille = event.getX() - grilleJeu.getLayoutX();
                double ySurGrille = event.getY() - grilleJeu.getLayoutY();

                int indexBouton = gestionnaireTours.getIndexInventaireActu();
                Tour nouvelleTour = gestionnaireTours.gererClicTerrain(xSurGrille, ySurGrille);

                if (nouvelleTour != null) {
                    int tailleTuile = env.getTailleTuile();
                    int caseX = (int) (xSurGrille / tailleTuile);
                    int caseY = (int) (ySurGrille / tailleTuile);

                    double pixelX = grilleJeu.getLayoutX() + (caseX * tailleTuile);
                    double pixelY = grilleJeu.getLayoutY() + (caseY * tailleTuile);

                    nouvelleTour.setX(pixelX);
                    nouvelleTour.setY(pixelY);

                    TourVue tourVue = new TourVue(nouvelleTour.getNomImage(), pixelX, pixelY);
                    if (!controllerPrincipal.jeuDemarre) tourVue.cacherRadar();
                    controllerPrincipal.vuesTours.put(nouvelleTour, tourVue);
                    conteneurPrincipal.getChildren().add(tourVue);

                    Button caseInventaire = boutonsInventaire.get(indexBouton);
                    caseInventaire.getStyleClass().remove("case-inventaire-selectionnee");
                    caseInventaire.getStyleClass().add("case-tour-posee");
                    caseInventaire.setDisable(false);

                    if (!controllerPrincipal.jeuDemarre && controllerPrincipal.boutonStart != null) {
                        controllerPrincipal.boutonStart.setDisable(false);
                    }
                }
            }
        });
    }

    public boolean acheterTour(String typeItem) {
        int prix = prixDe(typeItem);
        if (prix < 0 || env.getArgent() < prix) return false;

        int caseLibre = inventaireModele.getPremiereCaseLibre();
        if (caseLibre == -1) return false;

        Tour tour = creerTourDepuisType(typeItem);
        if (tour == null) return false;

        env.reduireArgent(prix);
        inventaireModele.setTourCase(caseLibre, typeItem);
        inventaireVue.installerTour(caseLibre, typeItem);
        env.getTourVersIndexInventaire().put(tour, caseLibre);
        controllerPrincipal.updateCompteurs();
        return true;
    }

    private int prixDe(String typeItem) {
        return switch (typeItem) {
            case "scientifique" -> TourScientifique.prixAchat;
            case "chimiste"     -> TourChimiste.prixAchat;
            case "scanner"      -> TourScanner.prixAchat;
            case "rayon_x"      -> TourRayonX.prixAchat;
            default -> -1;
        };
    }

    private Tour creerTourDepuisType(String typeItem) {
        return switch (typeItem) {
            case "scientifique" -> new TourScientifique(0, 0);
            case "chimiste"     -> new TourChimiste(0, 0);
            case "scanner"      -> new TourScanner(0, 0);
            case "rayon_x"      -> new TourRayonX(0, 0);
            default -> null;
        };
    }

    private void afficherEtPositionnerPanneauAction(Button btn, boolean estUneTourExistante) {
        Point2D coordsScene = btn.localToScene(0, 0);
        Point2D coordsConteneur = conteneurPrincipal.sceneToLocal(coordsScene);

        double caseCenterX = coordsConteneur.getX() + (btn.getWidth() / 2);
        double posY = coordsConteneur.getY();

        this.panneauActionTour.actualiser(this.tourEnInspection, estUneTourExistante, env.getArgent());
        this.panneauActionTour.applyCss();
        this.panneauActionTour.layout();

        double largeurInitiale = this.panneauActionTour.getBoundsInLocal().getWidth();
        double hauteurInitiale = this.panneauActionTour.getHeight();

        double posXInitiale = caseCenterX - (largeurInitiale / 2);
        double posYInitiale = posY - hauteurInitiale - 10;

        double largeurMaxConteneur = conteneurPrincipal.getWidth();
        if (posXInitiale + largeurInitiale > largeurMaxConteneur - env.getTailleTuile()) {
            posXInitiale = largeurMaxConteneur - largeurInitiale - env.getTailleTuile();
        }

        this.panneauActionTour.setLayoutX(posXInitiale);
        this.panneauActionTour.setLayoutY(posYInitiale);

        this.panneauActionTour.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double largeurReelle = newWidth.doubleValue();
            double hauteurReelle = this.panneauActionTour.getHeight();
            double posX = caseCenterX - (largeurReelle / 2);
            double correctedPosY = posY - hauteurReelle - 10;

            if (posX + largeurReelle > largeurMaxConteneur - largeurReelle - env.getTailleTuile()) {
                posX = largeurMaxConteneur - largeurReelle - env.getTailleTuile();
            }
            if (posX < env.getTailleTuile()) {
                posX = env.getTailleTuile();
            }

            this.panneauActionTour.setLayoutX(posX);
            this.panneauActionTour.setLayoutY(correctedPosY);
        });

        for (Button b : boutonsInventaire) {
            b.getStyleClass().remove("case-inventaire-selectionnee");
        }
        btn.getStyleClass().add("case-inventaire-selectionnee");
    }

    private void gererAmelioration() {
        if (this.tourEnInspection == null) return;
        if (env.ameliorerTour(this.tourEnInspection)) {
            if (tourEnInspection.getNiveau() == 5) {
                labelsInventaire.get(indexTourInspectee).setText("LEVEL MAX");
            } else {
                labelsInventaire.get(indexTourInspectee).setText("LEVEL " + tourEnInspection.getNiveau());
            }
            boolean estPosee = controllerPrincipal.vuesTours.containsKey(this.tourEnInspection);
            this.panneauActionTour.actualiser(this.tourEnInspection, estPosee, env.getArgent());
        }
    }

    private void gererRappel() {
        if (this.tourEnInspection == null) return;
        env.rappelerTour(this.tourEnInspection);
        TourVue vueAEnlever = controllerPrincipal.vuesTours.get(this.tourEnInspection);
        if (vueAEnlever != null) {
            conteneurPrincipal.getChildren().remove(vueAEnlever);
            controllerPrincipal.vuesTours.remove(this.tourEnInspection);
        }

        Button btn = boutonsInventaire.get(indexTourInspectee);
        btn.getStyleClass().remove("case-tour-posee");
        btn.getStyleClass().remove("case-inventaire-selectionnee");

        masquerPanneau();
    }

    private void gererVente() {
        if (this.tourEnInspection == null) return;

        TourVue vueAEnlever = controllerPrincipal.vuesTours.get(this.tourEnInspection);
        if (vueAEnlever != null) {
            conteneurPrincipal.getChildren().remove(vueAEnlever);
            controllerPrincipal.vuesTours.remove(this.tourEnInspection);
        }

        imagesInventaire.get(indexTourInspectee).setImage(null);
        labelsInventaire.get(indexTourInspectee).setText("");
        Button btn = boutonsInventaire.get(indexTourInspectee);
        btn.getStyleClass().remove("case-tour-posee");
        btn.getStyleClass().remove("case-inventaire-selectionnee");
        btn.setDisable(true);

        inventaireModele.viderCase(indexTourInspectee);
        env.vendreTour(this.tourEnInspection);
        masquerPanneau();
    }

    public void masquerPanneau() {
        if (this.panneauActionTour == null) return;
        this.panneauActionTour.setVisible(false);
        this.tourEnInspection = null;
        this.indexTourInspectee = -1;
        for (Button b : boutonsInventaire) {
            b.getStyleClass().remove("case-inventaire-selectionnee");
        }
    }

    public void reinitialiser() {
        masquerPanneau();
        gestionnaireTours.reinitialiser();
        for (int i = 0; i < boutonsInventaire.size(); i++) {
            inventaireModele.setTourCase(i, null);
            imagesInventaire.get(i).setImage(null);
            labelsInventaire.get(i).setText("");
            boutonsInventaire.get(i).setDisable(true);
            boutonsInventaire.get(i).getStyleClass().remove("case-inventaire-selectionnee");
            boutonsInventaire.get(i).getStyleClass().remove("case-tour-posee");
        }
    }

    public void verrouiller(boolean verrouiller) {
        for (int i = 0; i < boutonsInventaire.size(); i++) {
            if (verrouiller) {
                boutonsInventaire.get(i).setDisable(true);
            } else {
                String typeTour = inventaireModele.getTourCase(i);
                boutonsInventaire.get(i).setDisable(typeTour == null);
            }
        }
    }
}