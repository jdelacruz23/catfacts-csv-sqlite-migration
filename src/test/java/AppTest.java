import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class AppTest {
    @Test
    //2. CatFact test Record keeps the data you passed in
    void testCatFact() {
        App.CatFact catFact = new App.CatFact(1, "Cats love to sleep.");

        assertEquals(1, catFact.id());
        assertEquals("Cats love to sleep.", catFact.fact());
    }
    @Test
    //3. two CatFacts with the same values are equal.
    void testCatFactEquality() {
        App.CatFact cat1 = new App.CatFact(1, "Cats are cute.");
        App.CatFact cat2 = new App.CatFact(1, "Cats are cute.");

        assertEquals(cat1, cat2);
    }   
    //4.Two CatFact s with different text are not equal.
    @Test
    void testCatFactNotEqual() {
        App.CatFact cat1 = new App.CatFact(1, "Cats are cute.");
        App.CatFact cat2 = new App.CatFact(1, "Cats love to sleep.");

        assertNotEquals(cat1, cat2);
    }
    //5. A list finds a CatFact by its values, not by which object it is.
    @Test
    void testCatFactList() {
        List<App.CatFact> cats = new ArrayList<>();

        cats.add(new App.CatFact(1, "Cats are cute."));

        assertTrue(cats.contains(new App.CatFact(1, "Cats are cute.")));
    }
    //6. A NewFact gives back the text it was built with..
    @Test
    void testNewFact(){
        App.NewFact newFact = new App.NewFact("Cats are cute.");
        assertEquals("Cats are cute.", newFact.fact());
    }
    //7. An ErrorBody gives back the message it was built with.
    @Test
    void testErrorBody() {
        App.ErrorBody error = new App.ErrorBody("Not found");
        assertEquals("Not found", error.error());
    }
    //8. Nothing stops a CatFact from holding empty text. Assert that it was constructed successfully and that fact() returns the empty string
    @Test
    void testEmptyCatFact(){
        App.CatFact emptyFact = new App.CatFact(1, "");
        assertNotNull(emptyFact);
        assertEquals("", emptyFact.fact());
    }
}