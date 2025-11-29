package seguv3.domparse.hu;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

public class SEGUV3DOMRead {
    public static void main(String argv[]) throws SAXException, IOException, ParserConfigurationException, TransformerException {

        // XML fájl megnyitása
        File xmlFile = new File("SEGUV3DOMParse/src/seguv3/domparse/hu/SEGUV3XML.xml");

        // DocumentBuilderFactory létrehozása és DocumentBuilder példányosítása
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();

        // DOM fa létrehozása
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        System.out.println("==============================================");
        System.out.println(" Gyökérelem: " + doc.getDocumentElement().getNodeName());
        System.out.println("==============================================");

        // ===== SPORTOLÓK =====
        NodeList sportolok = doc.getElementsByTagName("Sportolo");
        System.out.println("\n*************** SPORTOLÓK ***************");
        for (int i = 0; i < sportolok.getLength(); i++) {
            Node node = sportolok.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;
                System.out.println("\n----------------------------------------");
                System.out.println("Sportoló ID: " + elem.getAttribute("sportolo_id"));
                System.out.println("Vezetéknév: " + elem.getElementsByTagName("vezeteknev").item(0).getTextContent());
                System.out.println("Keresztnév: " + elem.getElementsByTagName("keresztnev").item(0).getTextContent());
                System.out.println("Nemzetiség: " + elem.getElementsByTagName("nemzetiseg").item(0).getTextContent());
                System.out.println("Születési dátum: " + elem.getElementsByTagName("szuletesi_datum").item(0).getTextContent());

                // Elért eredmények
                NodeList eredmenyek = elem.getElementsByTagName("eredmeny");
                if (eredmenyek.getLength() > 0) {
                    System.out.println("Elért eredmények:");
                    for (int j = 0; j < eredmenyek.getLength(); j++) {
                        System.out.println("   • " + eredmenyek.item(j).getTextContent());
                    }
                }
            }
        }

        // ===== CSAPATOK =====
        NodeList csapatok = doc.getElementsByTagName("Csapat");
        System.out.println("\n*************** CSAPATOK ***************");
        for (int i = 0; i < csapatok.getLength(); i++) {
            Node node = csapatok.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;
                System.out.println("\n----------------------------------------");
                System.out.println("Csapat ID: " + elem.getAttribute("csapat_id"));
                System.out.println("Név: " + elem.getElementsByTagName("nev").item(0).getTextContent());
                System.out.println("Sportág: " + elem.getElementsByTagName("sportag").item(0).getTextContent());
                System.out.println("Edző: " +
                    elem.getElementsByTagName("vezeteknev").item(0).getTextContent() + " " +
                    elem.getElementsByTagName("keresztnev").item(0).getTextContent());
                System.out.println("Ország: " + elem.getElementsByTagName("orszag").item(0).getTextContent());
            }
        }

        // ===== VERSENYEK =====
        NodeList versenyek = doc.getElementsByTagName("Verseny");
        System.out.println("\n*************** VERSENYEK ***************");
        for (int i = 0; i < versenyek.getLength(); i++) {
            Node node = versenyek.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;
                System.out.println("\n----------------------------------------");
                System.out.println("Verseny ID: " + elem.getAttribute("verseny_id"));
                System.out.println("Név: " + elem.getElementsByTagName("nev").item(0).getTextContent());
                System.out.println("Dátum: " + elem.getElementsByTagName("datum").item(0).getTextContent());
                System.out.println("Kategória: " + elem.getElementsByTagName("kategoria").item(0).getTextContent());
                System.out.println("Szint: " + elem.getElementsByTagName("szint").item(0).getTextContent());
            }
        }

        // ===== HELYSZÍNEK =====
        NodeList helyszinek = doc.getElementsByTagName("Helyszin");
        System.out.println("\n*************** HELYSZÍNEK ***************");
        for (int i = 0; i < helyszinek.getLength(); i++) {
            Node node = helyszinek.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;
                System.out.println("\n----------------------------------------");
                System.out.println("Helyszín ID: " + elem.getAttribute("helyszin_id"));
                System.out.println("Város: " + elem.getElementsByTagName("varos").item(0).getTextContent());
                System.out.println("Ország: " + elem.getElementsByTagName("orszag").item(0).getTextContent());
                System.out.println("Létesítmény neve: " + elem.getElementsByTagName("letesitmeny_nev").item(0).getTextContent());
                System.out.println("Befogadóképesség: " + elem.getElementsByTagName("befogado_kepesseg").item(0).getTextContent());
            }
        }

        // ===== RÉSZVÉTELEK =====
        NodeList reszvetelek = doc.getElementsByTagName("Reszvetel");
        System.out.println("\n*************** RÉSZVÉTELEK ***************");
        for (int i = 0; i < reszvetelek.getLength(); i++) {
            Node node = reszvetelek.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;
                System.out.println("\n----------------------------------------");
                System.out.println("Részvétel ID: " + elem.getAttribute("reszvetel_id"));
                System.out.println("Csapat ref: " + elem.getAttribute("csapat_ref"));
                System.out.println("Verseny ref: " + elem.getAttribute("verseny_ref"));
                System.out.println("Helyezés: " + elem.getElementsByTagName("helyezes").item(0).getTextContent());
                System.out.println("Pontszám: " + elem.getElementsByTagName("pontszam").item(0).getTextContent());
                System.out.println("Díj összege: " + elem.getElementsByTagName("dij_osszeg").item(0).getTextContent());
                System.out.println("Megjeygzés: " + elem.getElementsByTagName("megjegyzes").item(0).getTextContent());
            }
        }

        // ===== SZÖVETSÉGEK =====
        NodeList szovetsegek = doc.getElementsByTagName("Szovetseg");
        System.out.println("\n*************** SZÖVETSÉGEK ***************");
        for (int i = 0; i < szovetsegek.getLength(); i++) {
            Node node = szovetsegek.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;
                System.out.println("\n----------------------------------------");
                System.out.println("Szövetség ID: " + elem.getAttribute("szovetseg_id"));
                System.out.println("Név: " + elem.getElementsByTagName("nev").item(0).getTextContent());
                System.out.println("Sportág: " + elem.getElementsByTagName("sportag").item(0).getTextContent());
                System.out.println("Alapítás éve: " + elem.getElementsByTagName("alapitasi_ev").item(0).getTextContent());
                System.out.println("Központ: " + elem.getElementsByTagName("kozpont").item(0).getTextContent());
            }
        }

        System.out.println("\n==============================================");
        System.out.println("Adatok beolvasása sikeresen befejezve!");
        System.out.println("==============================================");

        // ===== XML FÁJL MENTÉSE =====
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);

        StreamResult result = new StreamResult(new File("SEGUV3_XML_MENTES.xml"));
        transformer.transform(source, result);

        System.out.println("\nXML dokumentum sikeresen mentve: SEGUV3_XML_MENTES.xml");
    }
}
