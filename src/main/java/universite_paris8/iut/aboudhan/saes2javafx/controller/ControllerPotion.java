package universite_paris8.iut.aboudhan.saes2javafx.controller;

import javafx.scene.control.Button;
import universite_paris8.iut.aboudhan.saes2javafx.modele.jeu.Environnement;
import universite_paris8.iut.aboudhan.saes2javafx.modele.potion.*;
import universite_paris8.iut.aboudhan.saes2javafx.vue.bouton.PotionVue;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.IntConsumer;

public class ControllerPotion {

    private final Environnement env;
    private final Controller controllerPrincipal;

    private final PotionVue potionVue = new PotionVue();
    private final Map<TypePotion, Boolean> potionsEnCooldown = new EnumMap<>(TypePotion.class);

    private Button btnSoin, btnRage, btnGel;

    public ControllerPotion(Environnement env, Controller controllerPrincipal) {
        this.env = env;
        this.controllerPrincipal = controllerPrincipal;
    }

    public void setBoutons(Button btnSoin, Button btnRage, Button btnGel) {
        this.btnSoin = btnSoin;
        this.btnRage = btnRage;
        this.btnGel = btnGel;
    }

    public boolean acheterPotion(String typeItem) {
        TypePotion type = TypePotion.depuisIdentifiant(typeItem);
        if (type == null || env.getArgent() < type.getPrix()) return false;

        env.reduireArgent(type.getPrix());
        switch (type) {
            case SOIN -> env.setNbPotionSoin(env.getNbPotionSoin() + 1);
            case RAGE -> env.setNbPotionRage(env.getNbPotionRage() + 1);
            case GEL  -> env.setNbPotionGel(env.getNbPotionGel() + 1);
        }
        controllerPrincipal.updateCompteurs();
        return true;
    }

    public void utiliserSoin() { utiliser(TypePotion.SOIN, btnSoin, env.getNbPotionSoin(), env::setNbPotionSoin); }
    public void utiliserRage() { utiliser(TypePotion.RAGE, btnRage, env.getNbPotionRage(), env::setNbPotionRage); }
    public void utiliserGel()  { utiliser(TypePotion.GEL,  btnGel,  env.getNbPotionGel(),  env::setNbPotionGel); }

    private void utiliser(TypePotion type, Button bouton, int quantite, IntConsumer modifierQuantite) {
        if (quantite <= 0 || potionsEnCooldown.getOrDefault(type, false)) return;

        potionsEnCooldown.put(type, true);
        modifierQuantite.accept(quantite - 1);

        Potion potion = type.creerPotion();
        potion.appliquerEffet(env);
        controllerPrincipal.updateCompteurs();

        bouton.setDisable(true);
        potionVue.animerJaugeActive(bouton, 5.0, () -> {
            bouton.setDisable(false);
            potionsEnCooldown.put(type, false);
        });
    }
}
