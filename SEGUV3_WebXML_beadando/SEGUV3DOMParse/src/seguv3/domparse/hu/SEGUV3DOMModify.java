package seguv3.domparse.hu;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.w3c.dom.*;


public class SEGUV3DOMModify {

    public static void main(String argv[]) {

        try {
            // XML fájl megnyitása
            File inputFile = new File("SEGUV3DOMParse/src/seguv3/domparse/hu/SEGUV3XML.xml");

            // DocumentBuilderFactory példányosítása
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // XML dokumentum beolvasása és DOM fa létrehozása
            Document doc = docBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println("Gyökérelem: " + doc.getDocumentElement().getNodeName());
            System.out.println("\n--- Adatmódosítások kezdődnek ---");

            // ================================
            // 1. Csapatnév módosítása
            // ================================
            Node csapat = doc.getElementsByTagName("Csapat").item(0);

            // Csapat attribútum (azonosító) módosítása
            Element csapatElem = (Element) csapat;
            Node csapatIdNode = csapatElem.getElementsByTagName("csapat_id").item(0);
            csapatIdNode.setTextContent("C01");
            System.out.println("Csapat ID módosítva: C01");


            // Csapatnév gyerekelem módosítása
            NodeList lista = csapat.getChildNodes();

            for (int i = 0; i < lista.getLength(); i++) {
                Node node = lista.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;

                    if ("nev".equals(eElement.getNodeName())) {
                        if ("Tigrisek".equals(eElement.getTextContent())) {
                            eElement.setTextContent("Miskolci Bajnokok");
                            System.out.println("Csapatnév módosítva: Miskolci Bajnokok");
                        }
                    }

                    if ("sportag".equals(eElement.getNodeName())) {
                        if ("Foci".equals(eElement.getTextContent())) {
                            eElement.setTextContent("Kosárlabda");
                            System.out.println("Sportág módosítva: Kosárlabda");
                        }
                    }
                }
            }

            // ================================
            // 2. Pontszám növelése
            // ================================
            Node reszvetel = doc.getElementsByTagName("Reszvetel").item(0);
            NodeList reszvetelGyerekek = reszvetel.getChildNodes();

            for (int i = 0; i < reszvetelGyerekek.getLength(); i++) {
                Node node = reszvetelGyerekek.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;

                    if ("pontszam".equals(eElement.getNodeName())) {
                        int ujPontszam = Integer.parseInt(eElement.getTextContent()) + 10;
                        eElement.setTextContent(String.valueOf(ujPontszam));
                        System.out.println("Pontszám módosítva: +" + 10 + " (új érték: " + ujPontszam + ")");
                    }
                }
            }

            // ================================
            // 3. Új szponzor hozzáadása
            // ================================
            Element ujSzponzor = doc.createElement("Szponzor");
            ujSzponzor.setAttribute("sz_id", "SZ03");

            Element nev = doc.createElement("nev");
            nev.appendChild(doc.createTextNode("SportTech Hungary"));
            ujSzponzor.appendChild(nev);

            Element tipus = doc.createElement("tipus");
            tipus.appendChild(doc.createTextNode("Technikai támogatás"));
            ujSzponzor.appendChild(tipus);

            Element orszag = doc.createElement("orszag");
            orszag.appendChild(doc.createTextNode("Magyarország"));
            ujSzponzor.appendChild(orszag);

            doc.getDocumentElement().appendChild(ujSzponzor);
            System.out.println("Új szponzor hozzáadva: SportTech Hungary");

            // ================================
            // 4. Helyszín befogadóképesség módosítása
            // ================================
            Node helyszin = doc.getElementsByTagName("Helyszin").item(0);
            NodeList helyszinGyerekek = helyszin.getChildNodes();

            for (int i = 0; i < helyszinGyerekek.getLength(); i++) {
                Node node = helyszinGyerekek.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;

                    if ("befogado_kepesseg".equals(eElement.getNodeName())) {
                        eElement.setTextContent("25000");
                        System.out.println("Helyszín befogadóképessége módosítva: 25000 fő");
                    }
                }
            }

            // ================================
            // Módosított fájl mentése konzolra
            // ================================
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            DOMSource source = new DOMSource(doc);

            System.out.println("\n--- Módosított dokumentum ---");
            OutputStreamWriter writer = new OutputStreamWriter(System.out, StandardCharsets.UTF_8);
            StreamResult consoleResult = new StreamResult(writer);
            transformer.transform(source, consoleResult);


            System.out.println("\nA módosítások sikeresen végrehajtva!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
