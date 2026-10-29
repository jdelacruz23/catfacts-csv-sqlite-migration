import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

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
    //13. loadFromCsv reads every row in the file. Assert that App.facts has as many entries as facts.csv
    @Test
    void testLoadFromCsv() throws Exception {
        App.loadFromCsv();
        assertEquals(10, App.facts.size());
    }
    // 14. A fact containing commas is stored whole, not truncated.
    @Test
    void testLoadFromCsvCommas() throws Exception {
        App.loadFromCsv();
        for (App.CatFact cat : App.facts) {
            if (cat.id() == 9) {
                assertEquals("Cats, like people, tend to favor one paw over the other", cat.fact());
                return;
            }
        }
        fail("CatFact with id 9 not found");
    }
    // 15. Ids come from the file, they are not generated
    @Test
    void testCsvFirstId() throws Exception {
        App.loadFromCsv();

        assertEquals(1, App.facts.get(0).id());
    }
    //16. nextId ends up one past the highest id in the file.
    @Test
    void testNextId() throws Exception {
        App.loadFromCsv();
        assertEquals(11, App.nextId);
    }
    //17. Calling loadFromCsv twice does not give you twice as many facts.
    // Test fail: expected [11] but was [20]
    @Test
    void testLoadFromCsvTwice() throws Exception {
        App.facts.clear();
        App.loadFromCsv();
        App.loadFromCsv();
        assertEquals(11, App.facts.size());
    }
}