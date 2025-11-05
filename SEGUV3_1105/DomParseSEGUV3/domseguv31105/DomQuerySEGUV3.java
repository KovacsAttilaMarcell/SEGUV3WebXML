package domseguv31105;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;

public class DomQuerySEGUV3 {

    public static void main(String[] args) {
        try {
            // XML beolvasása
            File inputFile = new File("SEGUV3hallgato.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Gyökérelem nevének kiírása (ellenőrzéshez)
            System.out.println("Gyökérelem: " + doc.getDocumentElement().getNodeName());
            System.out.println("\n--- Hallgatók vezetéknevei ---");

            // Lekérdezés: minden hallgato elem vezetékneve
            NodeList hallgatoLista = doc.getElementsByTagName("hallgato");
            for (int i = 0; i < hallgatoLista.getLength(); i++) {
                Node node = hallgatoLista.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    String vezeteknev = elem.getElementsByTagName("vezeteknev").item(0).getTextContent();
                    System.out.println((i + 1) + ". hallgató vezetékneve: " + vezeteknev);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
