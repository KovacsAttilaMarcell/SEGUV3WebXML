package seguv3.domparse.hu;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.w3c.dom.*;

public class SEGUV3DOMQuery {

    public static void main(String argv[]) throws SAXException, IOException, ParserConfigurationException {

        // XML fájl beolvasása
        File xmlFile = new File("SEGUV3DOMParse/src/seguv3/domparse/hu/SEGUV3XML.xml");

        // DOM parser előkészítése
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        System.out.println("Gyökérelem: " + doc.getDocumentElement().getNodeName());
        System.out.println("========================================");

        // 1. Magyar sportolók listája
        System.out.println("Magyar sportolók listája:");
        NodeList sportolok = doc.getElementsByTagName("Sportolo");
        for (int i = 0; i < sportolok.getLength(); i++) {
            Element elem = (Element) sportolok.item(i);
            String nemzetiseg = elem.getElementsByTagName("nemzetiseg").item(0).getTextContent();
            if (nemzetiseg.equalsIgnoreCase("Magyar")) {
                String vezeteknev = elem.getElementsByTagName("vezeteknev").item(0).getTextContent();
                String keresztnev = elem.getElementsByTagName("keresztnev").item(0).getTextContent();
                System.out.println(" - " + vezeteknev + " " + keresztnev);
            }
        }

        System.out.println("\n========================================");

        // 2. Nemzetközi versenyek
        System.out.println("Nemzetközi szintű versenyek:");
        NodeList versenyek = doc.getElementsByTagName("Verseny");
        for (int i = 0; i < versenyek.getLength(); i++) {
            Element elem = (Element) versenyek.item(i);
            String szint = elem.getElementsByTagName("szint").item(0).getTextContent();
            if (szint.equalsIgnoreCase("Nemzetközi")) {
                String nev = elem.getElementsByTagName("nev").item(0).getTextContent();
                String datum = elem.getElementsByTagName("datum").item(0).getTextContent();
                System.out.println(" - " + nev + " (" + datum + ")");
            }
        }

        System.out.println("\n========================================");

        // 3. Csapatok, amelyek több mint 80 pontot szereztek
        System.out.println("Csapatok 80 pont felett:");
        NodeList reszvetelek = doc.getElementsByTagName("Reszvetel");
        for (int i = 0; i < reszvetelek.getLength(); i++) {
            Element elem = (Element) reszvetelek.item(i);
            int pontszam = Integer.parseInt(elem.getElementsByTagName("pontszam").item(0).getTextContent());
            if (pontszam > 80) {
                String csapatRef = elem.getAttribute("csapat_ref");
                String helyezes = elem.getElementsByTagName("helyezes").item(0).getTextContent();
                System.out.println(" - Csapat ref: " + csapatRef + " | Pontszám: " + pontszam + " | Helyezés: " + helyezes);
            }
        }

        System.out.println("\n========================================");

        // 4. Nagy befogadóképességű helyszínek
        System.out.println("Nagy befogadóképességű helyszínek (10.000 fő felett):");
        NodeList helyszinek = doc.getElementsByTagName("Helyszin");
        for (int i = 0; i < helyszinek.getLength(); i++) {
            Element elem = (Element) helyszinek.item(i);
            int befogado = Integer.parseInt(elem.getElementsByTagName("befogado_kepesseg").item(0).getTextContent());
            if (befogado > 10000) {
                String varos = elem.getElementsByTagName("varos").item(0).getTextContent();
                String orszag = elem.getElementsByTagName("orszag").item(0).getTextContent();
                System.out.println(" - " + varos + " (" + orszag + "), befogadóképesség: " + befogado);
            }
        }

        System.out.println("\n========================================");
        System.out.println("Lekérdezések sikeresen végrehajtva.");
    }
}
