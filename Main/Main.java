package Main;

import Connection.ConnectionController;
import Database.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        FXMLLoader anotherFxml = new FXMLLoader();
        Parent firstRoot = anotherFxml.load(getClass().getResource("MainWindow.fxml").openStream());
        Parent secondRoot = fxmlLoader.load(getClass().getResource("../Connection/connection.fxml").openStream());

        Position<Integer> firstPos = new Position(1200,800);
        Position<Double> secondPos= new Position(1200.000d,800.000d);

        primaryStage.setScene(new Scene(secondRoot,firstPos.getX(),firstPos.getY()));

        primaryStage.setTitle("Identifiant");
        ConnectionController myConnexionController = fxmlLoader.getController();
        MainController mainController = anotherFxml.getController();
        myConnexionController.validateConnexion.setOnAction(actionEvent -> {
            mainController.playerFirstName.setValue(myConnexionController.nomField.getText());
            mainController.playerLastName.setValue(myConnexionController.prenomField.getText());
            DatabaseConnection.getIntance().addPlayer(mainController.playerFirstName.getValue(),mainController.playerLastName.getValue());
            primaryStage.setScene(new Scene(firstRoot,secondPos.getX(),secondPos.getY()));
            primaryStage.setTitle("Hopital Crayzer");
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
