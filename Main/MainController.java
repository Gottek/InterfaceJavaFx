package Main;

import Persons.Doctors;
import PurchaseSale.PurchaseSaleController;
import Persons.DoctorsController;
import Settings.SettingController;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    @FXML
    public Label labelMoney;
    @FXML
    public Label PlayerName;
    @FXML
    public MenuItem menuItemQuit;
    @FXML
    private MenuItem menuItemStopGame;
    @FXML
    private FlowPane FlowPaneControlle;
    @FXML
    private Label LabelDocteurs;
    @FXML
    private Label LabelLits;
    @FXML
    private Label LabelMasques;
    @FXML
    private Label LabelGuerris;
    @FXML
    private Label LabelInfectes;
    @FXML
    private Label LabelMedicament;
    @FXML
    private MenuItem menuItemPause, menuItemOtherGamers, menuItemSettings, menuItemNewGame;
    @FXML
    private Button buttonMasques;
    @FXML
    private Button buttonMedicaments;
    @FXML
    private Button buttonLits;
    @FXML
    private Button buttonDocteurs;
    @FXML
    private HBox HboxButtons;
    @FXML
    public BorderPane MainBorderPane;
    @FXML
    protected ListView doctorsListView;

    public ObservableList ListLits;
    public SimpleStringProperty playerFirstName = new SimpleStringProperty();
    public SimpleStringProperty playerLastName = new SimpleStringProperty();
    public static SimpleIntegerProperty walletMoney = new SimpleIntegerProperty();
    public static SimpleIntegerProperty nbInfected = new SimpleIntegerProperty();
    public static SimpleIntegerProperty nbCured = new SimpleIntegerProperty();
    public static SimpleIntegerProperty nbMask = new SimpleIntegerProperty();
    public static SimpleIntegerProperty nbDrugs = new SimpleIntegerProperty();
    public static SimpleIntegerProperty nbDoctors = new SimpleIntegerProperty();
    public static SimpleIntegerProperty nbBed = new SimpleIntegerProperty();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        walletMoney.setValue(2000);
        Doctors doctors = new Doctors("salu","er","zoeijfr");
        doctors.getNom();
        doctorsListView.setItems(DialogueManager.getInstance().getDoctorsList());
        labelMoney.textProperty().bind(walletMoney.asString());
        LabelInfectes.textProperty().bind(nbInfected.asString());
        LabelGuerris.textProperty().bind(nbCured.asString());
        LabelMasques.textProperty().bind(nbMask.asString());
        LabelMedicament.textProperty().bind(nbDrugs.asString());
        LabelDocteurs.textProperty().bind(nbDoctors.asString());
        LabelLits.textProperty().bind(nbBed.asString());
        PlayerName.textProperty().bind(playerLastName);
        ListLits = FlowPaneControlle.getChildren();
        doctorsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //Checker pourquoi la fonction se repète 2 fois
        //DialogueManager.getInstance().displayAlertSellDoctors(this);
        doctorsListView.getSelectionModel().selectedItemProperty().addListener((c)->{
            int index = doctorsListView.getSelectionModel().getSelectedIndex();
            System.out.println(index);
            DialogueManager.getInstance().removeDoctor(index);
        });
    }

    // pour donner l'url du pop up et performé des actions en fonction de cela
    @FXML
    private void clique(ActionEvent actionEvent) {
        String typeButton = (actionEvent.getSource().getClass().equals(MenuItem.class)) ? ((MenuItem) actionEvent.getSource()).getId() : ((Button) actionEvent.getSource()).getId();
        if (actionEvent.getSource().getClass().equals(Button.class)) {
            String typeOperation = (typeButton.equals("buttonDocteurs")) ? "achat" : DialogueManager.getInstance().displayAlert(typeButton);
            String path = (typeButton.equals("buttonDocteurs")) ? "../Persons/DoctorsDialogue.fxml" : "../PurchaseSale/PurchaseSaleDialogue.fxml";
            if (typeOperation.equals("vendre") || typeOperation.equals("achat")) {

                DialoguePopUp(path, typeButton, typeOperation);
            }
        } else {
            if (!GameManager.getInstance().isGamePlayed) {
                String path = (typeButton.equals("menuItemOtherGamers")) ? "../OtherGamers/OtherGamer.fxml" : "../Settings/SettingsDialogue.fxml";
                DialoguePopUp(path, typeButton, "null");
            }
        }

    }

    // pour afficher tous les dialogues en fonction de l'url donnée
    private void DialoguePopUp(String path, String typeButton, String typeOperation) {
        Dialog<ButtonType> dialogue = new Dialog<>();
        dialogue.initOwner(MainBorderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(path));
        try {
            //fxmlLoader.setController(this);
            dialogue.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException exc) {
            System.out.println("vous avez une erreur quelque part ");
            exc.printStackTrace();
            return;
        }
        dialogue.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        dialogue.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialogue.setTitle("Dialogue pour l'instant");
        Optional<ButtonType> result = dialogue.showAndWait();
        if (typeOperation.equals("vendre") || typeOperation.equals("achat")) {
            showButtonActions(result, typeButton, fxmlLoader, typeOperation);
        } else {
            showMenuActions(fxmlLoader, result, typeButton);
        }

    }

    // pour ajouter et enlever un nombre précis de lit
    private void countLit(int quantity, String typeOperation) {
        BedManager.getInstance().onBedCreate(typeOperation, quantity, this);
    }

    // pour toutes les actions provoqué par les quatres button en bas
    private void showButtonActions(Optional<ButtonType> result, String typeButton, FXMLLoader fxmlLoader, String typeOperation) {
        //TODO faire que lorsqu'on vend, cela enlève des lits et non pas refresh toute la list
        if (result.isPresent() && result.get() == ButtonType.APPLY) {
            if (typeButton.equals("buttonDocteurs")) {
                if ((walletMoney.getValue() - 100) >= 0) {
                    DoctorsController doctorsController = fxmlLoader.getController();
                    walletMoney.setValue(walletMoney.getValue() - 100);
                    doctorsController.addDoctors();
                    nbDoctors.setValue(nbDoctors.getValue() + 1);
                } else DialogueManager.getInstance().displayAlertError();
            } else {
                PurchaseSaleController achatVenteController = fxmlLoader.getController();
                int tempWallet = achatVenteController.getPriceFromSipinner(typeOperation, typeButton, walletMoney.getValue());
                if (tempWallet < 0) DialogueManager.getInstance().displayAlertError();
                else {
                    switch (typeButton) {
                        case "buttonLits":
                            if (typeOperation.equals("vendre") && (nbBed.getValue() - achatVenteController.getValueFromSpinner() < 0)) {
                                DialogueManager.getInstance().displayAlertError();
                            } else {
                                nbBed.setValue((typeOperation == "achat") ? nbBed.getValue() + achatVenteController.getValueFromSpinner() : nbBed.getValue() - achatVenteController.getValueFromSpinner());
                                countLit(achatVenteController.getValueFromSpinner(), typeOperation);
                            }
                            break;
                        case "buttonMedicaments":
                            if (QuickCheck(typeOperation, achatVenteController, nbDrugs)) break;
                        case "buttonMasques":
                            if (QuickCheck(typeOperation, achatVenteController, nbMask)) break;
                    }
                    walletMoney.setValue(tempWallet);
                }
            }
        }
    }

    // verifie qu'on ne vende pas en dessous de zéro
    private boolean QuickCheck(String typeOperation, PurchaseSaleController achatVenteController, SimpleIntegerProperty quickBind) {
        if (typeOperation.equals("vendre") && (quickBind.getValue() - achatVenteController.getValueFromSpinner() < 0)) {
            DialogueManager.getInstance().displayAlertError();
        } else {
            quickBind.setValue((typeOperation == "achat") ? quickBind.getValue() + achatVenteController.getValueFromSpinner() : quickBind.getValue() - achatVenteController.getValueFromSpinner());
            return true;
        }
        return false;
    }

    //pour les donnée en prevenance des settings
    private void showMenuActions(FXMLLoader fxmlLoader, Optional<ButtonType> result, String typeButton) {
        if (result.isPresent() && result.get() == ButtonType.APPLY && typeButton.equals("menuItemSettings")) {
            SettingController settingController = fxmlLoader.getController();
            nbMask.setValue(settingController.getMask());
            nbDrugs.setValue(settingController.getDrugs());
            walletMoney.setValue(settingController.getCapital());
            nbBed.setValue(settingController.getBed());
            countLit(nbBed.getValue(), "other");
        }
    }

    //pour le changement d'état d'un lit
    protected void StateChange(ActionEvent actionEvent) {
        BedManager.getInstance().onCLickBed(actionEvent, this);
    }

    //pour les action Pause, nouvelle partie et arrêt
    public void menuActionButton(ActionEvent actionEvent) {
        switch (((MenuItem) actionEvent.getSource()).getId()) {
            case "menuItemNewGame" -> GameManager.getInstance().startGame(this);
            case "menuItemStopGame"->GameManager.getInstance().stopGame();
            case "menuItemPause" -> {
                GameManager.getInstance().pauseGame();
                menuItemPause.setText((GameManager.isPause) ? "Play" : "Pause");
            }
        }
    }

    public void closeButton(ActionEvent actionEvent) {
        Platform.exit();
    }
}
