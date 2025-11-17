package xpathseguv3;

import java.io.IOException;
import java.io.File;
import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

// Namespace kezeléséhez class
class MyNamespaceContext implements NamespaceContext {

    @Override
    public String getNamespaceURI(String prefix) {
        if ("ns".equals(prefix)) {
            return "DOMSEGUV3";  // XML-ben lévő default namespace
        }
        return null;
    }

    @Override
    public String getPrefix(String namespaceURI) {
        return null;
    }

    @Override
    public Iterator<String> getPrefixes(String namespaceURI) {
        return null;
    }
}

public class xPathModify1SEGUV3 {

    public static void main(String[] args) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true); // NÉLKÜLE SEMMI SEM MŰKÖDIK NAMESPACE-ES XML ENNÉL

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("xPathSEGUV3/orarendSEGUV3.xml");
            document.getDocumentElement().normalize();

            XPath xPath = XPathFactory.newInstance().newXPath();
            xPath.setNamespaceContext(new MyNamespaceContext());   // saját namespace-kezelő

            // ========================
            // 1) id="5" órán helyszín módosítása
            // ========================

            Node ora5 = (Node) xPath.evaluate(
                    "//ns:ora[@id='4']/ns:helyszin",
                    document,
                    XPathConstants.NODE
            );

            if (ora5 != null) {
                ora5.setTextContent("L202");
            } else {
                System.out.println("Nem található a helyszín id='5' elemhez. (Namespace hiba?)");
            }

            // ========================
            // 2) Szerdai órák napjának módosítása csütörtökre
            // ========================

            NodeList szerdaiOrak = (NodeList) xPath.evaluate(
                    "//ns:ora/ns:idopont/ns:nap[text()='Szerda']",
                    document,
                    XPathConstants.NODESET
            );

            for (int i = 0; i < szerdaiOrak.getLength(); i++) {
                Node nap = szerdaiOrak.item(i);
                nap.setTextContent("Csütörtök");
            }

            // ========================
            // 3) Órák típusának felcserélése
            // ========================

            NodeList osszesOra = (NodeList) xPath.evaluate(
                    "//ns:ora",
                    document,
                    XPathConstants.NODESET
            );

            for (int i = 0; i < osszesOra.getLength(); i++) {

                Element o = (Element) osszesOra.item(i);
                String tipus = o.getAttribute("tipus");

                if (tipus.equals("gyakorlat")) {
                    o.setAttribute("tipus", "eloadas");
                } else if (tipus.equals("eloadas")) {
                    o.setAttribute("tipus", "gyakorlat");
                }
            }

            System.out.println("\nMódosítások sikeresen elvégezve!\n");

            // ========================
            // KONZOL KIÍRÁS
            // ========================

            System.out.println("===== MÓDOSÍTOTT ÓRAREND =====\n");

            NodeList oraLista = (NodeList) xPath.evaluate("//ns:ora", document, XPathConstants.NODESET);

            for (int i = 0; i < oraLista.getLength(); i++) {

                Element ora = (Element) oraLista.item(i);

                System.out.println("Óra ID: " + ora.getAttribute("id"));
                System.out.println("Típus: " + ora.getAttribute("tipus"));
                System.out.println("Tárgy: " + ora.getElementsByTagNameNS("DOMSEGUV3", "targy").item(0).getTextContent());

                Element ido = (Element) ora.getElementsByTagNameNS("DOMSEGUV3", "idopont").item(0);
                System.out.println(" - Nap: " + ido.getElementsByTagNameNS("DOMSEGUV3", "nap").item(0).getTextContent());
                System.out.println(" - Tól: " + ido.getElementsByTagNameNS("DOMSEGUV3", "tol").item(0).getTextContent());
                System.out.println(" - Ig: " + ido.getElementsByTagNameNS("DOMSEGUV3", "ig").item(0).getTextContent());

                System.out.println("Helyszín: " + ora.getElementsByTagNameNS("DOMSEGUV3", "helyszin").item(0).getTextContent());
                System.out.println("Oktató: " + ora.getElementsByTagNameNS("DOMSEGUV3", "oktato").item(0).getTextContent());
                System.out.println("Szak: " + ora.getElementsByTagNameNS("DOMSEGUV3", "szak").item(0).getTextContent());
                System.out.println("------------------------------------\n");
            }

            // ========================
            // FÁJL MENTÉSE
            // ========================

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("xPathSEGUV3/orarendSEGUV31.xml"));

            transformer.transform(source, result);

            System.out.println("Fájl elmentve: xPathSEGUV3/orarendSEGUV31.xml");

        } catch (ParserConfigurationException | SAXException | IOException |
                XPathExpressionException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
