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

public class SEGUV3DOMRead {

    public static void main(String argv[]) throws SAXException, IOException, ParserConfigurationException {

        // XML fájl megnyitása
        File xmlFile = new File("SEGUV3DOMParse/src/seguv3/domparse/hu/SEGUV3XML.xml");

        // DocumentBuilderFactory példányosítása
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // DocumentBuilder létrehozása
        DocumentBuilder dBuilder = factory.newDocumentBuilder();

        // DOM fa előállítása az XML-ből
        Document doc = dBuilder.parse(xmlFile);

        // A dokumentum normalizálása
        doc.getDocumentElement().normalize();

        // Gyökérelem neve
        System.out.println("Gyökérelem: " + doc.getDocumentElement().getNodeName());
        System.out.println("========================================");

        // Sportolók adatainak kiolvasása
        NodeList sportolok = doc.getElementsByTagName("Sportolo");
        System.out.println("Sportolók listája:");
        for (int i = 0; i < sportolok.getLength(); i++) {
            Node nNode = sportolok.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;

                String id = elem.getElementsByTagName("sportolo_id").item(0).getTextContent();
                String vezeteknev = elem.getElementsByTagName("vezeteknev").item(0).getTextContent();
                String keresztnev = elem.getElementsByTagName("keresztnev").item(0).getTextContent();
                String nemzetiseg = elem.getElementsByTagName("nemzetiseg").item(0).getTextContent();

                System.out.println("\nSportoló ID: " + id);
                System.out.println("Név: " + vezeteknev + " " + keresztnev);
                System.out.println("Nemzetiség: " + nemzetiseg);
            }
        }

        System.out.println("\n========================================");

        // Csapatok adatainak kiolvasása
        NodeList csapatok = doc.getElementsByTagName("Csapat");
        System.out.println("Csapatok listája:");
        for (int i = 0; i < csapatok.getLength(); i++) {
            Node nNode = csapatok.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;

                String id = elem.getElementsByTagName("csapat_id").item(0).getTextContent();
                String nev = elem.getElementsByTagName("nev").item(0).getTextContent();
                String sportag = elem.getElementsByTagName("sportag").item(0).getTextContent();
                String orszag = elem.getElementsByTagName("orszag").item(0).getTextContent();

                System.out.println("\nCsapat ID: " + id);
                System.out.println("Név: " + nev);
                System.out.println("Sportág: " + sportag);
                System.out.println("Ország: " + orszag);
            }
        }

        System.out.println("\n========================================");

        // Versenyek adatainak kiolvasása
        NodeList versenyek = doc.getElementsByTagName("Verseny");
        System.out.println("Versenyek listája:");
        for (int i = 0; i < versenyek.getLength(); i++) {
            Node nNode = versenyek.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;

                String id = elem.getElementsByTagName("verseny_id").item(0).getTextContent();
                String nev = elem.getElementsByTagName("nev").item(0).getTextContent();
                String datum = elem.getElementsByTagName("datum").item(0).getTextContent();
                String kategoria = elem.getElementsByTagName("kategoria").item(0).getTextContent();
                String szint = elem.getElementsByTagName("szint").item(0).getTextContent();

                System.out.println("\nVerseny ID: " + id);
                System.out.println("Név: " + nev);
                System.out.println("Dátum: " + datum);
                System.out.println("Kategória: " + kategoria);
                System.out.println("Szint: " + szint);
            }
        }

        System.out.println("\n========================================");

        // Helyszínek adatainak kiolvasása
        NodeList helyszinek = doc.getElementsByTagName("Helyszin");
        System.out.println("Helyszínek listája:");
        for (int i = 0; i < helyszinek.getLength(); i++) {
            Node nNode = helyszinek.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;

                String id = elem.getElementsByTagName("helyszin_id").item(0).getTextContent();
                String varos = elem.getElementsByTagName("varos").item(0).getTextContent();
                String orszag = elem.getElementsByTagName("orszag").item(0).getTextContent();
                String letesitmeny = elem.getElementsByTagName("letesitmeny_nev").item(0).getTextContent();
                String befogadokep = elem.getElementsByTagName("befogado_kepesseg").item(0).getTextContent();

                System.out.println("\nHelyszín ID: " + id);
                System.out.println("Város: " + varos);
                System.out.println("Ország: " + orszag);
                System.out.println("Létesítmény neve: " + letesitmeny);
                System.out.println("Befogadóképesség: " + befogadokep);
            }
        }

        System.out.println("\n========================================");

        // Szponzorok adatainak kiolvasása
        NodeList szponzorok = doc.getElementsByTagName("Szponzor");
        System.out.println("Szponzorok listája:");
        for (int i = 0; i < szponzorok.getLength(); i++) {
            Node nNode = szponzorok.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;

                String id = elem.getElementsByTagName("sz_id").item(0).getTextContent();
                String nev = elem.getElementsByTagName("nev").item(0).getTextContent();
                String tipus = elem.getElementsByTagName("tipus").item(0).getTextContent();
                String orszag = elem.getElementsByTagName("orszag").item(0).getTextContent();

                System.out.println("\nSzponzor ID: " + id);
                System.out.println("Név: " + nev);
                System.out.println("Típus: " + tipus);
                System.out.println("Ország: " + orszag);
            }
        }

        System.out.println("\n========================================");

        // Résztvevők (kapcsoló entitás) kiolvasása
        NodeList reszvetelek = doc.getElementsByTagName("Reszvetel");
        System.out.println("Részvételi adatok:");
        for (int i = 0; i < reszvetelek.getLength(); i++) {
            Node nNode = reszvetelek.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;

                String id = elem.getElementsByTagName("reszvetel_id").item(0).getTextContent();
                String csapatId = elem.getElementsByTagName("csapat_id").item(0).getTextContent();
                String versenyId = elem.getElementsByTagName("verseny_id").item(0).getTextContent();
                String helyezes = elem.getElementsByTagName("helyezes").item(0).getTextContent();
                String pontszam = elem.getElementsByTagName("pontszam").item(0).getTextContent();

                System.out.println("\nRészvétel ID: " + id);
                System.out.println("Csapat ID: " + csapatId);
                System.out.println("Verseny ID: " + versenyId);
                System.out.println("Helyezés: " + helyezes);
                System.out.println("Pontszám: " + pontszam);
            }
        }

        System.out.println("\n========================================");
        System.out.println("XML dokumentum feldolgozása sikeresen befejeződött.");
    }
}

