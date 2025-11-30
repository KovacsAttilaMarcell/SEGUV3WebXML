package seguv3.domparse.hu;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class SEGUV3DOMModify {

    public static void main(String[] args) {
        try {
            // XML beolvasása
            File inputFile = new File("SEGUV3DOMParse/src/seguv3/domparse/hu/SEGUV3XML.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println("=======================================");
            System.out.println(" Gyökérelem: " + doc.getDocumentElement().getNodeName());
            System.out.println("=======================================");

            // ===== 1. Új sportoló hozzáadása =====
            Element ujSportolo = doc.createElement("Sportolo");
            ujSportolo.setAttribute("sportolo_id", "S003");

            Element nev = doc.createElement("nev");
            Element vezeteknev = doc.createElement("vezeteknev");
            vezeteknev.setTextContent("Tóth");
            Element keresztnev = doc.createElement("keresztnev");
            keresztnev.setTextContent("László");
            nev.appendChild(vezeteknev);
            nev.appendChild(keresztnev);
            ujSportolo.appendChild(nev);

            Element nemzetiseg = doc.createElement("nemzetiseg");
            nemzetiseg.setTextContent("Magyar");
            ujSportolo.appendChild(nemzetiseg);

            Element szuletesiDatum = doc.createElement("szuletesi_datum");
            szuletesiDatum.setTextContent("2000-05-21");
            ujSportolo.appendChild(szuletesiDatum);

            Element eredmenyek = doc.createElement("elert_eredmenyek");
            Element eredmeny1 = doc.createElement("eredmeny");
            eredmeny1.setTextContent("1. hely - Városi bajnokság 2024");
            eredmenyek.appendChild(eredmeny1);
            ujSportolo.appendChild(eredmenyek);

            doc.getDocumentElement().appendChild(ujSportolo);
            System.out.println("Új sportoló hozzáadva: Tóth László");

            // ===== 2. Csapat országának módosítása =====
            NodeList csapatok = doc.getElementsByTagName("Csapat");
            if (csapatok.getLength() > 0) {
                Element csapat = (Element) csapatok.item(0);
                Node orszagNode = csapat.getElementsByTagName("orszag").item(0);
                orszagNode.setTextContent("Szlovákia");
                System.out.println("Első csapat országa módosítva: Szlovákia");
            }

            // ===== 3. Helyszín befogadóképesség növelése =====
            NodeList helyszinek = doc.getElementsByTagName("Helyszin");
            if (helyszinek.getLength() > 0) {
                Element helyszin = (Element) helyszinek.item(0);
                Node befogado = helyszin.getElementsByTagName("befogado_kepesseg").item(0);
                int ujErtek = Integer.parseInt(befogado.getTextContent()) + 2000;
                befogado.setTextContent(String.valueOf(ujErtek));
                System.out.println("Helyszín befogadóképessége növelve: " + ujErtek);
            }

            // ===== 4. Szövetség központjának módosítása =====
            NodeList szovetsegek = doc.getElementsByTagName("Szovetseg");
            if (szovetsegek.getLength() > 0) {
                Element szovetseg = (Element) szovetsegek.item(0);
                Node kozpont = szovetseg.getElementsByTagName("kozpont").item(0);
                kozpont.setTextContent("Debrecen");
                System.out.println("Szövetség központja módosítva: Debrecen");
            }

            // ===== Módosított dokumentum konzolra írása =====
            System.out.println("\n--- Módosított dokumentum ---\n");
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            transformer.transform(new DOMSource(doc), new StreamResult(System.out));

            System.out.println("\n=======================================");
            System.out.println("Minden módosítás sikeresen végrehajtva!");
            System.out.println("=======================================");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
