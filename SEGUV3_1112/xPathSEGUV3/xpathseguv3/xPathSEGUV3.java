package xpathseguv3;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.io.IOException;

public class xPathSEGUV3 {

    public static void main(String[] args) {

        try {
            // XML fájl betöltése
            File xmlFile = new File("studentSEGUV3.xml");

            // DocumentBuilder létrehozása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // XML dokumentum beolvasása
            Document document = builder.parse(xmlFile);

            // Dokumentum normalizálása
            document.getDocumentElement().normalize();

            // XPath objektum létrehozása
            XPath xPath = XPathFactory.newInstance().newXPath();

            // XPath lekérdezés (neptunkod = seguv3)
            String seguv3 = "/class/student";
            NodeList students = (NodeList) xPath.compile(seguv3).evaluate(document, XPathConstants.NODESET);

            // Hallgatók kiíratása
            for (int i = 0; i < students.getLength(); i++) {
                Node node = students.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    System.out.println("\nHallgató ID: " + element.getAttribute("id"));
                    System.out.println("Keresztnév: " + element.getElementsByTagName("keresztnev").item(0).getTextContent());
                    System.out.println("Vezetéknév: " + element.getElementsByTagName("vezeteknev").item(0).getTextContent());
                    System.out.println("Becenév: " + element.getElementsByTagName("becenev").item(0).getTextContent());
                    System.out.println("Kor: " + element.getElementsByTagName("kor").item(0).getTextContent());
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }
}
