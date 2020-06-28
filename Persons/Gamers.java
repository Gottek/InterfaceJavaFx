package Persons;

public class Gamers extends Humanium{
    private int nbDoctors;
    private int nbMask;
    private int nbBed;
    private int nbMoney;
    private int nbInfected;
    private int nbCured;
    private int nbDrugs;
    private int score;

    public Gamers(String nom, String prenom, int nbDoctors, int nbMask, int nbBed, int nbMoney, int nbInfected, int nbCured, int nbDrugs, int score) {
        super(nom, prenom);
        this.nbDoctors = nbDoctors;
        this.nbMask = nbMask;
        this.nbBed = nbBed;
        this.nbMoney = nbMoney;
        this.nbInfected = nbInfected;
        this.nbCured = nbCured;
        this.nbDrugs = nbDrugs;
        this.score=score;
    }

    public int getNbDoctors() {
        return nbDoctors;
    }

    public void setNbDoctors(int nbDoctors) {
        this.nbDoctors = nbDoctors;
    }

    public int getNbMask() {
        return nbMask;
    }

    public void setNbMask(int nbMask) {
        this.nbMask = nbMask;
    }

    public int getNbBed() {
        return nbBed;
    }

    public void setNbBed(int nbBed) {
        this.nbBed = nbBed;
    }

    public int getNbMoney() {
        return nbMoney;
    }

    public void setNbMoney(int nbMoney) {
        this.nbMoney = nbMoney;
    }

    public int getNbInfected() {
        return nbInfected;
    }

    public void setNbInfected(int nbInfected) {
        this.nbInfected = nbInfected;
    }

    public int getNbCured() {
        return nbCured;
    }

    public void setNbCured(int nbCured) {
        this.nbCured = nbCured;
    }

    public int getNbDrugs() {
        return nbDrugs;
    }

    public void setNbDrugs(int nbDrugs) {
        this.nbDrugs = nbDrugs;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return  nom +" "+ prenom +' '+ nbDoctors +' '+ nbMask +' '+ nbBed +' '+ nbMoney +' '+nbInfected +' '+ nbCured +' '+nbDrugs+' '+score;
    }
}
