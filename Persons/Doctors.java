package Persons;

import Main.BedManager;
import Main.MainController;

public class Doctors extends Humanium implements IHeal{
    private String description;

    public Doctors(String nom, String prenom, String description) {
        super(nom, prenom);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return nom +' '+ prenom + '\n' +  description;
    }

    @Override
    public void healing(MainController controller) {
        if(MainController.nbDrugs.getValue()-1>0){
            MainController.nbDrugs.setValue(MainController.nbDrugs.getValue()-1);
            BedManager.getInstance().onHealing(controller);
        }
    }
}

