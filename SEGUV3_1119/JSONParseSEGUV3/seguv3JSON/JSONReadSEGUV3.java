
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONReadSEGUV3 {
    public static void main(String[] args) {
        try (FileReader reader = new FileReader("orarendSEGUV3.json")) {

            JSONParser parser = new JSONParser();
            JSONObject rootObj = (JSONObject) parser.parse(reader);

            JSONObject root = (JSONObject) rootObj.get("SEGUV3_orarend");
            JSONArray lessons = (JSONArray) root.get("ora");

            System.out.println("ÓRAREND:\n");

            for (Object o : lessons) {
                JSONObject lesson = (JSONObject) o;
                JSONObject idopont = (JSONObject) lesson.get("idopont");

                System.out.println("Tárgy: " + lesson.get("targy"));
                System.out.println("Nap: " + idopont.get("nap"));
                System.out.println("Idő: " + idopont.get("tol") + " - " + idopont.get("ig"));
                System.out.println("Helyszín: " + lesson.get("helyszin"));
                System.out.println("Oktató: " + lesson.get("oktato"));
                System.out.println("Szak: " + lesson.get("szak"));
                System.out.println("---------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
