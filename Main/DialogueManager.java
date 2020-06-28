package Main;

import Database.DatabaseConnection;
import Persons.Doctors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import java.util.Optional;
// classe qui s'occupe de gérer et afficher tous les modals

public class DialogueManager {
    private static final DialogueManager instance = new DialogueManager();
    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private static ObservableList<Doctors> doctorsList = FXCollections.observableArrayList();


    private DialogueManager(){
        super();
    }
    public static DialogueManager getInstance(){
        return DialogueManager.instance;
        }

    public String displayAlert(String type){
        alert.setTitle(type);
        alert.setHeaderText("Voulez-vous acheter ou vendre des " + type + "s");
        ButtonType buttonTypeVendre = new ButtonType("Vendre");
        ButtonType buttonTypeAcheter = new ButtonType("Acheter");
        ButtonType buttonTypeClose = new ButtonType("Fermer", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeAcheter, buttonTypeVendre, buttonTypeClose);
        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == buttonTypeAcheter)? "achat":(result.get()==buttonTypeVendre)?"vendre":"fermer";
    }
    public void displayAlertError(){
        alert.setTitle("Problèmes");
        alert.setHeaderText("Vous ne pouvez pas vendre en dessous de zéro ou acheter plus");
        ButtonType buttonTypeClose = new ButtonType("Fermer", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeClose);
        alert.show();
    }
    /*public void displayAlertSellDoctors(MainController controller){
        controller.doctorsListView.getSelectionModel().selectedItemProperty().addListener((c)->{
            int index = controller.doctorsListView.getSelectionModel().getSelectedIndex();
            System.out.println("looooooooooooooooooooooooooooooool" );
            alert.setTitle("Vente de docteur");
            alert.setHeaderText("Voulez-vous vendre ce docteur ?");
            ButtonType buttonTypeVendre = new ButtonType("Vendre");
            ButtonType buttonTypeClose = new ButtonType("Fermer", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeVendre, buttonTypeClose);
            ;
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeVendre) {
                removeDoctor(index);
            }
        });
    }*/

    public void removeDoctor(int index) {
        doctorsList.remove(index);
    }

    public void addDoctor(Doctors doctor){
        doctorsList.add(doctor);
    }
    public ObservableList<Doctors> getDoctorsList(){
        return doctorsList;
    }
    public void endGame(String title, String header, String content){
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        ButtonType buttonTypeClose = new ButtonType("Fermer", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeClose);
        alert.show();
    }
}
