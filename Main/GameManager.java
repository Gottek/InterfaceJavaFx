package Main;

import Database.DatabaseConnection;
import Persons.Doctors;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import jdk.jfr.SettingControl;

public class GameManager {
    private static final GameManager instance = new GameManager();
    private Timeline timeline;
    private MainController controller;
    private int ScoreGamer = 600000;
    public static boolean isPause = false;
    public Boolean isGamePlayed = false;


    private GameManager() {}

    public static GameManager getInstance() {
        return instance;
    }

    public void stopGame(){
        if(isGamePlayed){
        checkGamePlayed();
        isGamePlayed=false;
        timeline.stop();
        }
    }

    // gagné si le nombre d'infecter = 0
    public Boolean checkWin() { return MainController.nbInfected.getValue() == 1; }

    //perdu si le nombre d'infecter devient trop grand
    private Boolean checkLose() { return MainController.nbInfected.getValue() >= 3000; }

    public void pauseGame() {
        if (isGamePlayed) {
            if (!isPause) {
                timeline.stop();
            } else {
                timeline.play();
            }
            isPause = !isPause;
        }
    }

    // Le commencement du jeu
    public void startGame(MainController controller) {
        this.controller = controller;
        checkGamePlayed();
        int randomInfected = ((int) (Math.random() * 10 - 5) + 5);
        System.out.println(randomInfected);
        MainController.nbInfected.setValue(randomInfected);
        timeline = new Timeline(new KeyFrame(Duration.millis(8000), ae -> {
            if (checkLose()) {
                DialogueManager.getInstance().endGame("Partie Perdu","Le nombre d'infecté est devenu trop important","vous êtes un très mauvais gestionnaire d'hopital");
                isGamePlayed = false;
                finishGame();
                return;
            }
            this.ScoreGamer -= 400;
            addInfection();
            depreciationMask();
            depreciationDrugs();
            patientHealing();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    //infection aléatoire
    private void addInfection() {
        int randomInfected = ((int) (Math.random() * 10));
        MainController.nbInfected.setValue(MainController.nbInfected.getValue() + randomInfected);

    }

    //les masques se use avec le temps
    private void depreciationMask() {
        int quantity = ((int) (Math.random() * (MainController.nbDoctors.getValue())));
        if (MainController.nbMask.getValue() - quantity > 0) {
            MainController.nbMask.setValue(MainController.nbMask.getValue() - quantity);
        } else {
            if (isRemainsDoctors()) {
                MainController.nbDoctors.setValue(MainController.nbDoctors.getValue() - 1);
                DialogueManager.getInstance().removeDoctor(0);
                MainController.nbInfected.setValue(MainController.nbInfected.getValue() + 1);
            }
        }
    }

    // les medicament se use avec le temps
    private void depreciationDrugs() {
        int quantity = ((int) (Math.random() * (MainController.nbBed.getValue()))/2);
        if (MainController.nbDrugs.getValue() - quantity > 0) {
            MainController.nbDrugs.setValue(MainController.nbDrugs.getValue() - quantity);
        }
    }

    //verification s'il reste encore des docteurs
    private Boolean isRemainsDoctors() {
        return MainController.nbDoctors.getValue() - 1 >= 0;
    }

    //retablissement du lit
    private void patientHealing() {
        for (Doctors doctor:DialogueManager.getInstance().getDoctorsList()) { doctor.healing(controller); }
    }

    private void checkGamePlayed() {
        if (isGamePlayed) {
            MainController.walletMoney.setValue(2000);
            MainController.nbDrugs.setValue(0);
            MainController.nbDoctors.setValue(0);
            MainController.nbBed.setValue(0);
            MainController.nbCured.setValue(0);
            MainController.nbMask.setValue(0);
            MainController.nbInfected.setValue(0);
            DialogueManager.getInstance().getDoctorsList().clear();
            BedManager.getInstance().onBedCreate("other", 0, controller);
            ScoreGamer = 600000;
        } else {
            isGamePlayed = true;
        }
    }
    protected void finishGame(){
        DatabaseConnection.getIntance().addScoreToPlayer(controller.playerFirstName.getValue(),
                controller.playerLastName.getValue(),controller.nbDoctors.getValue(),
                controller.nbMask.getValue(),controller.nbBed.getValue(),
                controller.nbInfected.getValue(),controller.nbCured.getValue(),
                controller.nbDrugs.getValue(),controller.walletMoney.getValue(),ScoreGamer);
    }
}
