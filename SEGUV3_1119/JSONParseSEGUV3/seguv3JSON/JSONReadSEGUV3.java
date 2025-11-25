package seguv3JSON;

import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONReadSEGUV3 {

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        try {
            Object object = parser.parse(new FileReader("orarendSEGUV3.json"));

            JSONObject root = (JSONObject) object;
            JSONObject orarend = (JSONObject) root.get("SEGUV3_orarend");

            JSONArray oras = (JSONArray) orarend.get("ora");

            for (Object o : oras) {
                JSONObject ora = (JSONObject) o;

                System.out.println("Tárgy: " + ora.get("targy"));
                
                JSONObject idopont = (JSONObject) ora.get("idopont");
                System.out.println("Nap: " + idopont.get("nap"));
                System.out.println("Tól: " + idopont.get("tol"));
                System.out.println("Ig: " + idopont.get("ig"));

                System.out.println("Helyszín: " + ora.get("helyszin"));
                System.out.println("Oktató: " + ora.get("oktato"));
                System.out.println("Szak: " + ora.get("szak"));
                System.out.println("-----------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
