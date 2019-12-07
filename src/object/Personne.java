package object;

import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Personne {
    private int id;
    private String nom;
    private String prenom;

    public Personne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.id = -1;
    }

    public static List<Personne> findAll() {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "select * from personne"
            );
            ResultSet resultSet = statement.executeQuery();
            List<Personne> personnes = new ArrayList<>();
            while (resultSet.next()) {
                Personne personne = new Personne(
                        resultSet.getString("nom"),
                        resultSet.getString("prenom")
                );
                personne.setId(resultSet.getInt("id"));
                personnes.add(personne);
            }
            return personnes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Personne> findById(int id) {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "select * from personne where id = ?"
            );
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Personne> personnes = new ArrayList<>();
            while (resultSet.next()) {
                Personne personne = new Personne(
                        resultSet.getString("nom"),
                        resultSet.getString("prenom")
                );
                personne.setId(resultSet.getInt("id"));
                personnes.add(personne);
            }
            return personnes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Personne> findByNom(String nom) {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "select * from personne where nom = ?"
            );
            statement.setString(1, nom);
            ResultSet resultSet = statement.executeQuery();
            List<Personne> personnes = new ArrayList<>();
            while (resultSet.next()) {
                Personne personne = new Personne(
                        resultSet.getString("nom"),
                        resultSet.getString("prenom")
                );
                personne.setId(resultSet.getInt("id"));
                personnes.add(personne);
            }
            return personnes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save() throws SQLException {
        if (this.id != -1) this.update();
        else this.saveNew();
    }

    public void update() throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "update personne set nom = ?, prenom = ? " +
                        "where id = ?"
        );
        statement.setString(1, this.nom);
        statement.setString(2, this.prenom);
        statement.setInt(3, this.id);

    }

    private void saveNew() throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statementID = connection.prepareStatement(
                "select max(id) as idMax from personne"
        );
        ResultSet idSet = statementID.executeQuery();
        while (idSet.next())
            this.id = idSet.getInt("idMax") + 1;
        PreparedStatement statement = connection.prepareStatement(
                "insert into  personne(id, nom, prenom) values(?, ?, ?)"
        );
        statement.setInt(1, this.id);
        statement.setString(2, this.nom);
        statement.setString(3, this.prenom);
        statement.execute();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public static void createTable() throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement st = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `testpersonne` (\n" +
                "`ID` int(11) NOT NULL,\n" +
                "`NOM` varchar(40) NOT NULL,\n" +
                "`PRENOM` varchar(40) NOT NULL");
        st.executeUpdate();
    }

    public static void deleteTable() throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement st = connection.prepareStatement("DROP TABLE IF EXISTS `testpersonne`");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Personne: %d\n", this.id));
        builder.append(String.format("  Nom: %s\n", this.nom));
        builder.append(String.format("  Prenom: %s\n", this.prenom));
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personne personne = (Personne) o;
        return id == personne.id &&
                Objects.equals(nom, personne.nom) &&
                Objects.equals(prenom, personne.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom);
    }
}
