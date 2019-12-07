package test;

import java.sql.SQLException;
import java.util.ArrayList;

import object.Personne;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PersonneTest {
    public PersonneTest() {
    }

    @Before
    public void creerDonnees() throws SQLException {
        Personne.createTable();
        (new Personne("Spielberg", "Steven")).save();
        (new Personne("Scott", "Ridley")).save();
        (new Personne("Kubrick", "Stanley")).save();
        (new Personne("Spielberg", "George")).save();
    }

    @After
    public void supprimerDonnees() throws SQLException {
        Personne.deleteTable();
    }

    @Test
    public void testConstruct() {
        Personne p = new Personne("toto", "tot");
        Assert.assertEquals("objet pas dans la base", (long)p.getId(), -1L);
    }

    @Test
    public void testFindByNameExiste() throws SQLException {
        ArrayList<Personne> p = Personne.findByName("Kubrick");
        Assert.assertEquals("une seule reponse", 1L, (long)p.size());
        Personne pers = (Personne)p.get(0);
        Assert.assertEquals(pers.getNom(), "Kubrick");
        Assert.assertEquals(pers.getPrenom(), "Stanley");
    }

    @Test
    public void testFindByName2Existe() throws SQLException {
        ArrayList<Personne> p = Personne.findByName("Spielberg");
        Assert.assertEquals("deux reponses dans personne", 2L, (long)p.size());
        Personne pers = (Personne)p.get(0);
        Assert.assertEquals(pers.getNom(), "Spielberg");
        pers = (Personne)p.get(1);
        Assert.assertEquals(pers.getNom(), "Spielberg");
    }

    @Test
    public void testFindByNameNon() throws SQLException {
        ArrayList<Personne> p = Personne.findByName("ee");
        Assert.assertEquals("pas de reponse", 0L, (long)p.size());
    }

    @Test
    public void testFindAll() throws SQLException {
        ArrayList<Personne> p = Personne.findAll();
        Assert.assertEquals(4L, (long)p.size());
    }

    @Test
    public void testSaveNew() throws SQLException {
        Personne p = new Personne("toto", "titi");
        p.save();
        Assert.assertEquals("id de la personne vient autoincrement", 5L, (long)p.getId());
        Personne pers = Personne.findById(5);
        Assert.assertEquals(pers.getNom(), "toto");
        Assert.assertEquals(pers.getPrenom(), "titi");
        Assert.assertEquals((long)pers.getId(), 5L);
    }

    @Test
    public void testSaveExistant() throws SQLException {
        Personne p = Personne.findById(2);
        p.setNom("Bertrand");
        p.save();
        Assert.assertEquals("id ne devrait pas bouger", 2L, (long)p.getId());
        ArrayList<Personne> pers = Personne.findByName("Bertrand");
        Assert.assertEquals(1L, (long)pers.size());
        Personne p2 = (Personne)pers.get(0);
        Assert.assertEquals("Bertrand", p2.getNom());
        Assert.assertEquals("Ridley", p2.getPrenom());
        Assert.assertEquals("ide devrait etre le meme", (long)p2.getId(), 2L);
    }

    @Test
    public void testDelete() throws SQLException {
        Personne p = Personne.findById(2);
        p.delete();
        Personne p2 = Personne.findById(2);
        Assert.assertEquals("id devrait etre revenue à -1", -1L, (long)p.getId());
        Assert.assertEquals("le supprime n'existe plus", (Object)null, p2);
    }
}
