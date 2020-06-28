package OtherGamers;

import Database.DatabaseConnection;
import Persons.Gamers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OtherGamerController implements Initializable {
    @FXML
    private TableView<Gamers> GamerTable;

    private final ObservableList<Gamers> GamerList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initiatePlayers();
        GamerTable.setItems(GamerList);
    }
    private void initiatePlayers(){
        try {
            ResultSet rs= DatabaseConnection.getIntance().fetchAllPlayers();
            while (rs.next()) {
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                int nbdoctors = rs.getInt("nbdoctors");
                int nbCured = rs.getInt("nbCured");
                int nbdrugs = rs.getInt("nbdrugs");
                int nbmasks = rs.getInt("nbmasks");
                int nbinfected = rs.getInt("nbinfected");
                int nbmoney = rs.getInt("nbmoney");
                int nbbeds = rs.getInt("nbbeds");
                int score = rs.getInt("score");

                GamerList.add(new Gamers(firstName,lastName,nbdoctors,nbmasks,nbbeds,nbmoney,nbinfected,nbCured,nbdrugs,score));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
