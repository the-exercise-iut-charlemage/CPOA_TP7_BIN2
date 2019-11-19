import object.Personne;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class PersonneTest {

    Personne spiel, p, p1, p2, p3, p4;
    List<Personne> lb;

    @Before
    public void setup() {
        spiel = new Personne("Spielberg", "Steven");
        lb = new ArrayList<>();
        p1 = new Personne("Spielberg", "Steven");
        p1.setId(1);
        p2 = new Personne("Scott", "Ridley");
        p2.setId(2);
        p3 = new Personne("Kubrick", "Stanley");
        p3.setId(3);
        p4 = new Personne("Fincher", "David");
        p4.setId(4);
        lb.addAll(Arrays.asList(p1, p2, p3, p4));
    }

    @Test
    public void test_find_all() {
        List<Personne> l = p.findAll();
        assertTrue("Les listes doivent être les mêmes", l.equals(lb));
    }


    @Test
    public void test_find_by_id() {
        List<Personne> pId = p.findById(2);
        assertTrue("Le résultat devrait contenir l'id 2", pId.contains(p2));
    }
    
    @Test
    public void test_find_by_nom() {
        List<Personne> pNom = p.findByNom("Kubrick");
        assertTrue("Le résultat devrait contenir Kubrick", pNom.contains(p3));
    }
}