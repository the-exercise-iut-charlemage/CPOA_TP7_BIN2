package test;

import java.sql.SQLException;

import object.Film;
import object.Personne;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.RealisateurAbsentException;

public class FilmTest {
    Personne spielberg;
    Personne scott;
    Personne kubrick;
    Personne fincher;

    public FilmTest() {
    }

    @Before
    public void creerDonnees() throws SQLException, RealisateurAbsentException {
        Personne.createTable();
        Film.createTable();
        this.spielberg = new Personne("Spielberg", "Steven");
        this.spielberg.save();
        this.scott = new Personne("Scott", "Ridley");
        this.scott.save();
        this.kubrick = new Personne("Kubrick", "Stanley");
        this.kubrick.save();
        this.fincher = new Personne("Fincher", "David");
        this.fincher.save();
        (new Film("Arche perdue", this.spielberg)).save();
        (new Film("Alien", this.scott)).save();
        (new Film("Temple Maudit", this.spielberg)).save();
        (new Film("Blade Runner", this.scott)).save();
        (new Film("Alien3", this.fincher)).save();
        (new Film("Fight Club", this.fincher)).save();
        (new Film("Orange Mecanique", this.kubrick)).save();
    }

    @After
    public void detruireDonnees() throws SQLException {
        Film.deleteTable();
        Personne.deleteTable();
    }

    @Test
    public void testConstruct() {
        Film f = new Film("derniere croisade", this.spielberg);
        Assert.assertEquals("l'objet n'est pas dans la base", (long)f.getId(), -1L);
    }

    @Test
    public void testfindById() throws SQLException {
        Film f = Film.findById(1);
        Assert.assertEquals("Arche perdue", f.getTitre());
        Assert.assertEquals("Spielberg", f.getRealisateur().getNom());
    }

    @Test
    public void testfindByIdBis() throws SQLException {
        Film f = Film.findById(5);
        Assert.assertEquals("Alien3", f.getTitre());
        Assert.assertEquals("Fincher", f.getRealisateur().getNom());
    }

    @Test
    public void testfindByIdInexistant() throws SQLException {
        Film f = Film.findById(8);
        Assert.assertEquals("pas de film correspondant", (Object)null, f);
    }

    @Test
    public void testSaveAvecRealisateur() throws SQLException, RealisateurAbsentException {
        (new Film("Panic Room", this.fincher)).save();
        Film f2 = Film.findById(8);
        Assert.assertEquals("Panic Room", f2.getTitre());
        Assert.assertEquals("Fincher", f2.getRealisateur().getNom());
        Assert.assertEquals(4L, (long)f2.getRealisateur().getId());
    }

    @Test
    public void testSaveAvecRealisateurRecupere() throws SQLException, RealisateurAbsentException {
        Personne p = (Personne)Personne.findByName("Spielberg").get(0);
        (new Film("ET", p)).save();
        Film f2 = Film.findById(8);
        Assert.assertEquals("ET", f2.getTitre());
        Assert.assertEquals("Spielberg", f2.getRealisateur().getNom());
        Assert.assertEquals(1L, (long)f2.getRealisateur().getId());
    }

    @Test
    public void test2Saves() throws SQLException, RealisateurAbsentException {
        Film f = new Film("Panic Room", this.fincher);
        f.save();
        Film f2 = new Film("ET", this.spielberg);
        f2.save();
        Film f3 = Film.findById(9);
        Assert.assertEquals("ET", f3.getTitre());
        Assert.assertEquals("Spielberg", f3.getRealisateur().getNom());
        Assert.assertEquals(1L, (long)f3.getRealisateur().getId());
    }

    @Test
    public void testNouveauRealSauve() throws SQLException, RealisateurAbsentException {
        Personne p = new Personne("Zemeckis", "Robert");
        p.save();
        (new Film("Retour vers le futur", p)).save();
        Film f = Film.findById(8);
        Assert.assertEquals("Retour vers le futur", f.getTitre());
        Assert.assertEquals("Zemeckis", f.getRealisateur().getNom());
        Assert.assertEquals(5L, (long)f.getRealisateur().getId());
    }

    @Test(
            expected = RealisateurAbsentException.class
    )
    public void testNouveauRealInconnu() throws SQLException, RealisateurAbsentException {
        Personne p = new Personne("Zemeckis", "Robert");
        (new Film("Retour vers le futur", p)).save();
    }

    @Test
    public void testChangeNomReal() throws SQLException, RealisateurAbsentException {
        Film f = new Film("Retour vers le futur", this.fincher);
        f.save();
        this.fincher.setNom("bincher");
        this.fincher.save();
        Film f2 = Film.findById(8);
        Assert.assertEquals("Retour vers le futur", f2.getTitre());
        Assert.assertEquals("bincher", f2.getRealisateur().getNom());
        Assert.assertEquals(4L, (long)f2.getRealisateur().getId());
    }
}
