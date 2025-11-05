package domseguv31105;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DomQuery1SEGUV3 {

    public static void main(String[] args) {
        try {
            // XML beolvasása
            File inputFile = new File("orarendSEGUV3.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println("Gyökérelem: " + doc.getDocumentElement().getNodeName());

            // 1.) Kérdezze le a kurzusok nevét listába, majd írja ki a konzolra
            NodeList oraLista = doc.getElementsByTagName("ora");
            List<String> kurzusok = new ArrayList<>();

            for (int i = 0; i < oraLista.getLength(); i++) {
                Element ora = (Element) oraLista.item(i);
                String targy = ora.getElementsByTagName("targy").item(0).getTextContent();
                kurzusok.add(targy);
            }

            System.out.println("\n--- 1. Lekérdezés: Kurzusnevek listája ---");
            System.out.println("Kurzusnév: " + kurzusok);

            // 2.) Első óra példány kiírása strukturált formában a konzolra és fájlba
            System.out.println("\n--- 2. Lekérdezés: Első óra adatai ---");
            Element elsoOra = (Element) oraLista.item(0);

            // Konzolra strukturáltan
            printStructured(elsoOra, "");

            // Fájlba is mentjük
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(elsoOra);
            StreamResult result = new StreamResult(new File("orarendQuery1SEGUV3.xml"));
            transformer.transform(source, result);

            System.out.println("\n(Az első óra adatai az 'orarendQuery1SEGUV3.xml' fájlba is mentve.)");

            // 3.) Oktatók neveinek listája
            List<String> oktatok = new ArrayList<>();
            for (int i = 0; i < oraLista.getLength(); i++) {
                Element ora = (Element) oraLista.item(i);
                String oktato = ora.getElementsByTagName("oktato").item(0).getTextContent();
                oktatok.add(oktato);
            }

            System.out.println("\n--- 3. Lekérdezés: Oktatók listája ---");
            System.out.println("Oktatók: " + oktatok);

            // 4.) Összetett lekérdezés: listázza azokat a kurzusokat, amelyek hétfői napra esnek
            System.out.println("\n--- 4. Lekérdezés: Hétfői kurzusok nevei és időpontjai ---");
            for (int i = 0; i < oraLista.getLength(); i++) {
                Element ora = (Element) oraLista.item(i);
                Element idopont = (Element) ora.getElementsByTagName("idopont").item(0);
                String nap = idopont.getElementsByTagName("nap").item(0).getTextContent();

                if ("Hétfő".equalsIgnoreCase(nap)) {
                    String targy = ora.getElementsByTagName("targy").item(0).getTextContent();
                    String tol = idopont.getElementsByTagName("tol").item(0).getTextContent();
                    String ig = idopont.getElementsByTagName("ig").item(0).getTextContent();
                    System.out.println("- " + targy + " (" + tol + " - " + ig + ")");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Segédfüggvény: strukturált kiírás
    private static void printStructured(Node node, String indent) {
        System.out.println(indent + "<" + node.getNodeName() + ">");
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                printStructured(child, indent + "  ");
            } else if (child.getNodeType() == Node.TEXT_NODE && !child.getTextContent().trim().isEmpty()) {
                System.out.println(indent + "  " + child.getTextContent().trim());
            }
        }
        System.out.println(indent + "</" + node.getNodeName() + ">");
    }
}
