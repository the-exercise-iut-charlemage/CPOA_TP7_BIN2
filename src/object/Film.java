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

public class Film {

    private String titre;
    private int id;

    public String getTitre() {
        return titre;
    }

    public int getId() {
        return id;
    }

    private int id_real;

    public Film(String titre, Personne real) {
        this.titre = titre;
        this.id_real = real.getId();
        this.id = -1;
    }

    private Film(String titre, int id, int id_real) {
        this.titre = titre;
        this.id = id;
        this.id_real = id_real;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public static Film findById(int id) {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "select * from film where id = ?"
            );
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Film> films = new ArrayList<>();
            while (resultSet.next()) {
                Film film = new Film(
                        resultSet.getString("titre"),
                        resultSet.getInt("id"),
                        resultSet.getInt("id_real")
                );
                films.add(film);
            }
            return films.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Film> findByRealisateur(Personne p) {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "select * from film where id_rea = ?"
            );
            statement.setInt(1, p.getId());
            ResultSet resultSet = statement.executeQuery();
            List<Film> films = new ArrayList<>();
            while (resultSet.next()) {
                Film film = new Film(
                        resultSet.getString("titre"),
                        resultSet.getInt("id"),
                        resultSet.getInt("id_real")
                );
                films.add(film);
            }
            return films;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Personne getRealisateur() {
        return Objects.requireNonNull(Personne.findById(this.id_real));
    }

    public void save() throws SQLException, RealisateurAbsentException {
        if (this.id_real == -1) throw new RealisateurAbsentException();
        if (this.id != -1) this.update();
        else this.saveNew();
    }

    private void update() throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "update film set id_rea = ?, titre = ? " +
                        "where id = ?"
        );
        statement.setInt(1, this.id_real);
        statement.setString(2, this.titre);
        statement.setInt(3, this.id);
        statement.execute();
    }

    private void saveNew() throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statementID = connection.prepareStatement(
                "select max(id) as idMax from film"
        );
        ResultSet idSet = statementID.executeQuery();
        while (idSet.next())
            this.id = idSet.getInt("idMax") + 1;
        PreparedStatement statement = connection.prepareStatement(
                "insert into film(id, titre, id_rea) values(?, ?, ?)"
        );
        statement.setInt(1, this.id);
        statement.setString(2, this.titre);
        statement.setInt(3, this.id_real);
        statement.execute();
    }

    public static void createTable() throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement st = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `film` (" +
                "  `ID` int(11) primary key NOT NULL," +
                "  `TITRE` varchar(40) NOT NULL," +
                "  `ID_REA` int(11) DEFAULT NULL" +
                ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;");
        st.executeUpdate();
        st = connection.prepareStatement("ALTER TABLE `film` ADD CONSTRAINT `film_ibfk_1` FOREIGN KEY (`ID_REA`) REFERENCES `personne` (`ID`)");
        st.executeUpdate();
    }

    public static void deleteTable() throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement st = connection.prepareStatement("DROP TABLE IF EXISTS `film`");
        st.execute();
    }
}
