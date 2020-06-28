package Database;

import java.sql.*;

public class DatabaseConnection {
    private static final DatabaseConnection instance = new DatabaseConnection();

    private DatabaseConnection() {
        super();
    }

    private Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/JavaFxDB", "postgres", "admin");
            //return DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Amarl\\Desktop\\SQLite DB\\mydatabase.db");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new SQLException(throwables);
        }
    }

    public ResultSet fetchAllPlayers() throws SQLException {
        Statement st = getConnection().createStatement();
        return st.executeQuery("SELECT * FROM persons");
    }

    public void addScoreToPlayer(String nom, String prenom, int nbDoctors, int nbMask, int nbBeds, int nbInfected, int nbCured, int nbDrugs, int nbMoney,int score) {
        String sqlQuery = "UPDATE persons SET nbDoctors=" + nbDoctors + ",nbmasks=" + nbMask + ",nbbeds=" + nbBeds + ",nbinfected=" + (nbInfected-1) + "," +
                "nbcured=" + nbCured + ",nbdrugs=" + nbDrugs + ",nbmoney=" + nbMoney + " " +",score="+score+
                "WHERE lastname='" + nom + "' AND firstname='" + prenom + "'";
        statementExecuted(sqlQuery);
    }

    public void addPlayer(String nom, String prenom) {
        String sqlQuery = "insert into persons(LastName, FirstName) values('" + nom + "','" + prenom + "')";
        statementExecuted(sqlQuery);
    }

    private void statementExecuted(String sqlQuery) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(sqlQuery);
            statement.execute();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static DatabaseConnection getIntance() {
        return instance;
    }
}
