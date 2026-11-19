package universite_paris8.iut.aboudhan.saes2javafx.modele.potion;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;

public class StrategieGel implements EffetPotionStrategie {
    private final int duree;

    public StrategieGel() {
        this.duree = 2;
    }

    @Override
    public void appliquerEffet(Environnement env) {
        env.setMicrobesGeles(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(duree));
        pause.setOnFinished(event -> {
            env.setMicrobesGeles(false);
        });
        pause.play();
    }
}
