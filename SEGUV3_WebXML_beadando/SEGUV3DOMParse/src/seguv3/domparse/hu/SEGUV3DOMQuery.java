package seguv3.domparse.hu;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class SEGUV3DOMQuery {

    public static void main(String argv[]) throws SAXException, IOException, ParserConfigurationException {

        // XML fájl beolvasása
        File xmlFile = new File("SEGUV3DOMParse/src/seguv3/domparse/hu/SEGUV3XML.xml");

        // DOM parser előkészítése
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        // Dokumentum normalizálása
        doc.getDocumentElement().normalize();
        System.out.println("Gyökérelem: " + doc.getDocumentElement().getNodeName());
        System.out.println("========================================");

        // Példa 1: Lekérdezés – Az összes magyar sportoló neve
        System.out.println("Magyar sportolók listája:");
        NodeList sportolok = doc.getElementsByTagName("Sportolo");
        for (int i = 0; i < sportolok.getLength(); i++) {
            Node nNode = sportolok.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;
                String nemzetiseg = elem.getElementsByTagName("nemzetiseg").item(0).getTextContent();

                if (nemzetiseg.equalsIgnoreCase("Magyar")) {
                    String vezeteknev = elem.getElementsByTagName("vezeteknev").item(0).getTextContent();
                    String keresztnev = elem.getElementsByTagName("keresztnev").item(0).getTextContent();
                    System.out.println(" - " + vezeteknev + " " + keresztnev);
                }
            }
        }

        System.out.println("\n========================================");

        // Példa 2: Lekérdezés – Versenyek, ahol a szint „Nemzetközi”
        System.out.println("Nemzetközi szintű versenyek:");
        NodeList versenyek = doc.getElementsByTagName("Verseny");
        for (int i = 0; i < versenyek.getLength(); i++) {
            Node nNode = versenyek.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;
                String szint = elem.getElementsByTagName("szint").item(0).getTextContent();

                if (szint.equalsIgnoreCase("Nemzetközi")) {
                    String nev = elem.getElementsByTagName("nev").item(0).getTextContent();
                    String datum = elem.getElementsByTagName("datum").item(0).getTextContent();
                    System.out.println(" - " + nev + " (" + datum + ")");
                }
            }
        }

        System.out.println("\n========================================");

        // Példa 3: Lekérdezés – Csapatok, amelyek több mint 80 pontot szereztek
        System.out.println("Csapatok 80 pont felett:");
        NodeList reszvetelek = doc.getElementsByTagName("Reszvetel");
        for (int i = 0; i < reszvetelek.getLength(); i++) {
            Node nNode = reszvetelek.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;
                int pontszam = Integer.parseInt(elem.getElementsByTagName("pontszam").item(0).getTextContent());

                if (pontszam > 80) {
                    String csapatId = elem.getElementsByTagName("csapat_id").item(0).getTextContent();
                    String helyezes = elem.getElementsByTagName("helyezes").item(0).getTextContent();
                    System.out.println(" - Csapat ID: " + csapatId + " | Pontszám: " + pontszam + " | Helyezés: " + helyezes);
                }
            }
        }

        System.out.println("\n========================================");

        // Példa 4: Lekérdezés – Helyszínek, ahol a befogadóképesség > 10000
        System.out.println("Nagy befogadóképességű helyszínek (10.000 fő felett):");
        NodeList helyszinek = doc.getElementsByTagName("Helyszin");
        for (int i = 0; i < helyszinek.getLength(); i++) {
            Node nNode = helyszinek.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;
                int befogado = Integer.parseInt(elem.getElementsByTagName("befogado_kepesseg").item(0).getTextContent());

                if (befogado > 10000) {
                    String varos = elem.getElementsByTagName("varos").item(0).getTextContent();
                    String orszag = elem.getElementsByTagName("orszag").item(0).getTextContent();
                    System.out.println(" - " + varos + " (" + orszag + "), befogadóképesség: " + befogado);
                }
            }
        }

        System.out.println("\n========================================");
        System.out.println("Lekérdezések sikeresen végrehajtva.");
    }
}
