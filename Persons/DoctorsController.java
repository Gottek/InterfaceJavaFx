package Persons;

import Main.DialogueManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DoctorsController {
    @FXML
    private TextField nomText;
    @FXML
    private TextField prenomText;
    @FXML
    private TextArea descriptionText;

    public void addDoctors(){
        String nom = nomText.getText().trim();
        String prenom = prenomText.getText().trim();
        String description = descriptionText.getText().trim();
        DialogueManager.getInstance().addDoctor(new Doctors(nom,prenom,description));
    }
}
