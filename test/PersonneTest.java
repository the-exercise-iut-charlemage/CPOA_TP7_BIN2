import object.Film;
import object.Personne;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.*;

@SuppressWarnings("null")
public class PersonneTest {

    private Personne spiel, p, p1, p2, p3, p4, p5;
    private List<Personne> lb;

    @Before
    public void setup() throws SQLException {
        Personne.createTable();
        spiel = new Personne("Spielberg", "Steven");
        lb = new ArrayList<>();
        p1 = new Personne("Spielberg", "Steven");
        p1.setId(1);
        p1.save();
        p2 = new Personne("Scott", "Ridley");
        p2.setId(2);
        p2.save();
        p3 = new Personne("Kubrick", "Stanley");
        p3.setId(3);
        p3.save();
        p4 = new Personne("Fincher", "David");
        p4.setId(4);
        p4.save();
        lb.addAll(Arrays.asList(p1, p2, p3, p4));
    }

    @Test
    public void test_find_all() {
        List<Personne> l = Personne.findAll();
        assertEquals("Les listes doivent être les mêmes", l, lb);
    }


    @Test
    public void test_find_by_id() {
        Personne pId = Personne.findById(2);
        assertNotNull("Le retour et null", pId);
        assertTrue("Le résultat devrait contenir l'id 2", pId.equals(p2));
    }
    
    @Test
    public void test_find_by_nom() {
        List<Personne> pNom = Personne.findByName("Kubrick");
        assertNotNull("Le retour et null", pNom);
        assertTrue("Le résultat devrait contenir Kubrick", pNom.contains(p3));
    }

    @Test
    public void test_insertion_personne() throws SQLException {
        p5 = new Personne("Besson", "Luc");
        p5.save();
        ArrayList<Personne> listP = Personne.findAll();
        for (Personne p : listP) {
            System.out.print(p);
        }
        //assertTrue("La personne n'est pas dans la base", listP.contains(equals(p5)));
    }

    @Test
    public void test_suppresion_personne() throws SQLException {
        p = new Personne("Nolan", "Christohper");
        p.save();
        p.delete();
        ArrayList<Personne> listP = Personne.findAll();
        assertFalse("La personne est toujours présente dans la base", listP.contains(equals(p)));
    }

    @After
    public void deleteTable() throws SQLException {
        Personne.deleteTable();
    }
}