package Settings;

import Main.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;

import java.net.URL;
import java.util.ResourceBundle;


public class SettingController implements Initializable {
    @FXML
    private Spinner capitalSpinner,DrugsSpinner,BedSpinner,MaskSpinner;

    public Integer getCapital() {
        return (int) capitalSpinner.getValue();
    }
    public Integer getDrugs() {
        return (int) DrugsSpinner.getValue();
    }
    public Integer getBed() {
        return (int) BedSpinner.getValue();
    }
    public Integer getMask() {
        return (int) MaskSpinner.getValue();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        capitalSpinner.getValueFactory().setValue(MainController.walletMoney.getValue());
        DrugsSpinner.getValueFactory().setValue(MainController.nbDrugs.getValue());
        BedSpinner.getValueFactory().setValue(MainController.nbBed.getValue());
        MaskSpinner.getValueFactory().setValue(MainController.nbMask.getValue());
    }
}

