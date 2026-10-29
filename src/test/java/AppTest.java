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
    //9.Serializing a CatFact produces JSON containing both of its fields.
    @Test
    void testCatFactToJson(){
    App.CatFact catJson = new App.CatFact(1, "Cats are cute.");

    String json = App.gson.toJson(catJson);

    assertTrue(json.contains("\"id\": 1"));
    assertTrue(json.contains("\"fact\": \"Cats are cute.\""));
    }
    //10. A JSON request body deserializes into a NewFact 
    @Test
    void testDeserializeNewFact(){
        String json = "{\"fact\": \"Cats are cute.\"}";
        App.NewFact newFact = App.gson.fromJson(json, App.NewFact.class);
        assertEquals("Cats are cute.", newFact.fact());
    }
    //11. An apostrophe comes out of Gson as an apostrophe
    @Test
    void testApostropheInCatFact(){
        App.CatFact apostropheFact = new App.CatFact(1, "Cats aren't dogs.");
        String json = App.gson.toJson(apostropheFact);
        assertTrue(json.contains("aren't"));
        assertFalse(json.contains("\\u0027"));
    }
    //12.  A fact containing commas survives being serialized and deserialized.
    @Test 
    void testToJsonFromJson(){
        App.CatFact toFromJsonFact = new App.CatFact(9, "Cats, unlike dogs, cannot taste sweetness");
        String toJson = App.gson.toJson(toFromJsonFact);
        App.CatFact resultJson = App.gson.fromJson(toJson, App.CatFact.class);
        assertEquals(toFromJsonFact, resultJson);
    }
}