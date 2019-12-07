import object.Personne;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.DBConnection;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

@SuppressWarnings("null")
public class PersonneTest {

    private Personne spiel, p, p1, p2, p3, p4, p5;
    private List<Personne> lb;

    @Before
    public void setup() throws SQLException {
        Personne.createTable();
        lb = new ArrayList<>();
        p1 = new Personne("Spielberg", "Steven");
        p1.save();
        p2 = new Personne("Scott", "Ridley");
        p2.save();
        p3 = new Personne("Kubrick", "Stanley");
        p3.save();
        p4 = new Personne("Fincher", "David");
        p1.save();
        lb.addAll(Arrays.asList(p1, p2, p3, p4));
    }

    @Test
    public void test_find_all() {
        int i = 0;
        for (Personne p: Personne.findAll()) {
            assertEquals("Les listes doivent être les mêmes", p, lb.get(i));
            i++;
        }
    }

    @Test
    public void test_find_by_id() throws SQLException {
        Personne pId = Personne.findById(2);
        assertNotNull("Le retour et null", pId);
        assertTrue("Le résultat devrait contenir l'id 2", pId.getNom().equals(p2.getNom()));
    }
    
    @Test
    public void test_find_by_nom() {
        List<Personne> pNom = Personne.findByName("Kubrick");
        assertNotNull("Le retour et null", pNom);
        assertTrue("Le résultat devrait contenir Kubrick", pNom.get(0).getNom().equals(p3.getNom()));
    }

    @Test
    public void test_insertion_personne() throws SQLException {
        p = new Personne("Besson", "Luc");
        p.save();
        ArrayList<Personne> listP = Personne.findAll();
        assertTrue("La personne n'est pas dans la base", listP.contains(p));
    }

    @Test
    public void test_suppresion_personne() throws SQLException {
        p = new Personne("Nolan", "Christohper");
        p.save();
        p.delete();
        ArrayList<Personne> listP = Personne.findAll();
        assertFalse("La personne est toujours présente dans la base", listP.contains(p));
    }

   @After
    public void deleteTable() throws SQLException {
        Personne.deleteTable();
    }
}