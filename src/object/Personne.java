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
