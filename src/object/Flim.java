package object;

import utils.DBConnection;
import utils.RealisateurAbsentException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Flim {

    private String titre;
    private int id;
    private int id_real;

    public Flim(String titre, Personne real) {
        this.titre = titre;
        this.id_real = real.getId();
        this.id = -1;
    }

    private Flim(String titre, int id, int id_real) {
        this.titre = titre;
        this.id = id;
        this.id_real = id_real;
    }

    public static List<Flim> findById(int id) {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "select * from film where id = ?"
            );
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Flim> flims = new ArrayList<>();
            while (resultSet.next()) {
                Flim flim = new Flim(
                        resultSet.getString("titre"),
                        resultSet.getInt("id"),
                        resultSet.getInt("id_real")
                );
                flims.add(flim);
            }
            return flims;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Flim> findByRealisateur(Personne p) {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "select * from film where id_real = ?"
            );
            statement.setInt(1, p.getId());
            ResultSet resultSet = statement.executeQuery();
            List<Flim> flims = new ArrayList<>();
            while (resultSet.next()) {
                Flim flim = new Flim(
                        resultSet.getString("titre"),
                        resultSet.getInt("id"),
                        resultSet.getInt("id_real")
                );
                flims.add(flim);
            }
            return flims;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Personne getRealisateur() {
        return Objects.requireNonNull(Personne.findById(this.id_real)).get(0);
    }

    public void save() throws SQLException, RealisateurAbsentException {
        if (this.id_real == -1) throw new RealisateurAbsentException();
        if (this.id != -1) this.update();
        else this.saveNew();
    }

    private void update() throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "update film set id_real = ?, titre = ? " +
                        "where id = ?"
        );
        statement.setInt(1, this.id_real);
        statement.setString(2, this.titre);
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
                "insert into film(id, titre, id_real) values(?, ?, ?)"
        );
        statement.setInt(1, this.id_real);
        statement.setString(2, this.titre);
        statement.setInt(3, this.id);
        statement.execute();
    }
}
