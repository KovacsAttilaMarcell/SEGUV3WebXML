package domseguv31105;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class DomModify1SEGUV3 {

    public static void main(String[] args) {
        try {
            // XML beolvasása
            File inputFile = new File("orarendSEGUV3.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // 1.) Új elem hozzáadása az első <ora> elemhez
            NodeList oraLista = doc.getElementsByTagName("ora");
            if (oraLista.getLength() > 0) {
                Element elsoOra = (Element) oraLista.item(0);

                Element ujOraado = doc.createElement("oraado");
                ujOraado.setTextContent("Dr. Kovács Marcell");

                elsoOra.appendChild(ujOraado);
            }

            // Transformer létrehozása a kiíráshoz
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // Kiírás konzolra és fájlba
            System.out.println("\n--- Módosított fájl (új óraadó hozzáadva) ---\n");
            DOMSource source1 = new DOMSource(doc);
            StreamResult consoleResult = new StreamResult(System.out);
            StreamResult fileResult = new StreamResult(new File("orarendModify1SEGUV3.xml"));
            transformer.transform(source1, consoleResult);
            transformer.transform(source1, fileResult);

            // 2.) Minden 'ora' típusát gyakorlatról előadásra módosítja
            NodeList orak = doc.getElementsByTagName("ora");
            for (int i = 0; i < orak.getLength(); i++) {
                Element ora = (Element) orak.item(i);
                if ("gyakorlat".equals(ora.getAttribute("tipus"))) {
                    ora.setAttribute("tipus", "eloadas");
                }
            }

            // Kiírás a konzolra strukturáltan
            System.out.println("\n--- Módosított fájl (gyakorlat -> előadás) ---\n");
            DOMSource source2 = new DOMSource(doc);
            transformer.transform(source2, new StreamResult(System.out));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
