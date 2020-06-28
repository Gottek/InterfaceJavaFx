package Main;

import Database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;


public class BedManager {

    private static final BedManager instance = new BedManager();
    private BedManager() {
    }
    public static BedManager getInstance(){
        return instance;
    }

    public void onCLickBed(ActionEvent actionEvent, MainController controller){
        switch (((Button) actionEvent.getSource()).getText()) {
            case "Lit inoccupé" -> {
                ((Button) actionEvent.getSource()).setText("Lit occupé");
                ((Button) actionEvent.getSource()).setStyle("-fx-background-color:cadetblue ; -fx-font-size: 16");
                ((Button) actionEvent.getSource()).setTextFill(Color.WHITE);
            }
            case "Lit occupé" -> {
                ((Button) actionEvent.getSource()).setText("Lit inoccupé");
                ((Button) actionEvent.getSource()).setStyle("-fx-background-color: darkred;-fx-font-size: 16");
                ((Button) actionEvent.getSource()).setTextFill(Color.WHITE);
            }
            case "Guéri" -> {
                if (GameManager.getInstance().checkWin()) {
                    GameManager.getInstance().pauseGame();
                    GameManager.getInstance().finishGame();
                    GameManager.getInstance().isGamePlayed=false;
                    DialogueManager.getInstance().endGame("Partie gagné","Vous avez reussi à éradiquer la maladie","Il n'y a plus personne d'infecté");
                }
                ((Button) actionEvent.getSource()).setText("Lit inoccupé");
                ((Button) actionEvent.getSource()).setStyle("-fx-background-color: darkred;-fx-font-size: 16");
                ((Button) actionEvent.getSource()).setTextFill(Color.WHITE);
                controller.nbCured.setValue(controller.nbCured.getValue() + 1);
                controller.nbInfected.setValue(controller.nbInfected.getValue() - 1);
                controller.walletMoney.setValue(controller.walletMoney.getValue() + 30);
            }
        }
    }
    public void onBedCreate(String typeOperation, int quantity,MainController controller){
        if (typeOperation.equals("vendre")) {
            for (int i = 0; i < quantity; i++) {
                controller.ListLits.remove(controller.ListLits.size() - 1);
            }
        } else{
            if(typeOperation.equals("other")){
                controller.ListLits.clear();
            }
            creatingBed(quantity,controller);
        }
    }
    public void onHealing(MainController controller){
        int randomHeal = ((int) (Math.random() * controller.ListLits.size()));
        if(((Button)controller.ListLits.get(randomHeal)).getText().equals("Lit occupé")){
            ((Button)controller.ListLits.get(randomHeal)).setText("Guéri");
            ((Button)controller.ListLits.get(randomHeal)).setStyle("-fx-background-color: green;-fx-font-size: 16");
            ((Button)controller.ListLits.get(randomHeal)).setTextFill(Color.BLACK);
        }
    }
    private void creatingBed(int quantity, MainController controller){
        for (int i = 0; i < quantity; i++) {
            ImageView image = new ImageView("Assets/hospital-bed.png");
            image.setFitWidth(60);
            image.setFitHeight(60);
            Button button = new Button("Lit inoccupé", image);
            button.setOnAction(controller::StateChange);
            button.setStyle("-fx-background-color: darkred;-fx-font-size: 16");
            button.setTextFill(Color.WHITE);
            button.setPrefWidth(190);
            button.setPrefHeight(190);
            button.wrapTextProperty();
            controller.ListLits.add(button);
        }
    }
}
