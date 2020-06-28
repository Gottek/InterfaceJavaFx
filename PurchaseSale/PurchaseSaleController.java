package PurchaseSale;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;

import java.net.URL;
import java.util.ResourceBundle;

public class PurchaseSaleController implements Initializable {
    @FXML
    private Spinner SpinnerAchatVente;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Integer getValueFromSpinner(){
        return (int) SpinnerAchatVente.getValue();
    }
    public int getPriceFromSipinner(String typeOperation, String PurchaseType,int wallet){
        int price=0;
        switch (PurchaseType){
            case "buttonLits":
                price=100*(int)SpinnerAchatVente.getValue();
                break;
            case "buttonMasques":
                price=10*(int)SpinnerAchatVente.getValue();
                break;
            case "buttonMedicaments":
                price=20*(int)SpinnerAchatVente.getValue();
                break;
        }
        wallet= (typeOperation.equals("achat"))? wallet - price : wallet + (price / 2);
        return wallet;
    }

}
