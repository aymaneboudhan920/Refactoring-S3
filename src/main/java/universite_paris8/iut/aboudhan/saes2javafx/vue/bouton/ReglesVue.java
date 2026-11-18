package universite_paris8.iut.aboudhan.saes2javafx.vue.bouton;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class ReglesVue implements FenetreVue {
    private final StackPane calqueFond;

    public ReglesVue(Runnable onClose) {
        calqueFond = new StackPane();
        calqueFond.getStyleClass().add("fond-regles-obscur");
        calqueFond.setPrefSize(1020, 680);

        VBox fenetreRegles = new VBox(25);
        fenetreRegles.getStyleClass().add("panneau-regles-terminal");
        fenetreRegles.setMaxSize(600, 500);
        fenetreRegles.setAlignment(Pos.CENTER);
        fenetreRegles.setPadding(new Insets(30));
        Label titre = new Label("PROTOCOLE SANITAIRE OBLIGATOIRE");
        titre.getStyleClass().add("titre-regles-neon");

        Label contenu = new Label(
                "1. OBJECTIF : Protégez la zone en empêchant les microbes d'atteindre la sortie. Éliminez un maximum de microbes pour survivre un maximum de temps et gagner assez d'argent.\n\n" +
                        "2. TOURS DE DÉFENSE : Achetez vos unités (Scientifique, Chimiste...) dans le Shop et installez-les judicieusement sur le terrain. Vous pourrez ensuite les améliorer au fur et à mesure lorsque votre budget vous le permettra !\n\n" +
                        "3. POTIONS DE CRISE : Utilisez votre argent malignement pour utiliser les potions de Soin, de Rage ou de Gel intelligemment dans les moments les plus critiques. NE LES GASPILLEZ PAS !\n\n" +
                        "4. PROGRESSION : Soyez toujours prêt, les microbes tenteront tout pour s'échapper et chaque vague de microbes est plus redoutable que la précédente !"
        );
        contenu.getStyleClass().add("texte-regles-corps");
        contenu.setPrefWidth(540);
        contenu.setWrapText(true);
        Button btnAccepter = new Button("COMPRIS !");
        btnAccepter.getStyleClass().add("btn-regles-action");
        btnAccepter.setOnAction(e -> onClose.run());
        fenetreRegles.getChildren().addAll(titre, contenu, btnAccepter);
        calqueFond.getChildren().add(fenetreRegles);
    }
    @Override
    public void afficherSur(Pane conteneur) { conteneur.getChildren().add(calqueFond); }
    @Override
    public void cacher(Pane conteneur) { conteneur.getChildren().remove(calqueFond); }
}
