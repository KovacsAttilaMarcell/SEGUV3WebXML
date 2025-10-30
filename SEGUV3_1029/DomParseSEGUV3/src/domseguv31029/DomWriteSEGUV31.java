package domseguv31029;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DomWriteSEGUV31 {

    public static void main(String[] args) throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.newDocument();

        Element root = doc.createElementNS("DOMSEGUV3", "SEGUV3_orarend");
        doc.appendChild(root);

        // A gyökérelemhez több gyermekelemet fűzünk (órák)
        root.appendChild(createOra(doc, "1", "eloadas", "Elektrotechnika-elektronika", "Hétfő", "08:00", "9:30", "E 3", "Szabó Norbert István", "Mérnökinformatikus BSc"));
        root.appendChild(createOra(doc, "2", "eloadas", "Web technológiák 1.", "Hétfő", "10:00", "11:30", "E 5", "Dr. Agárdi Anita", "Mérnökinformatikus BSc"));
        root.appendChild(createOra(doc, "3", "gyakorlat", "Angol műszaki szaknyelv 1.", "Hétfő", "12:00", "13:30", "A1/101", "Dobronyi Eszter", "Mérnökinformatikus BSc"));
        root.appendChild(createOra(doc, "4", "gyakorlat", "Web technológiák 1.", "Hétfő", "14:00", "15:30", "L101", "Dr. Agárdi Anita", "Mérnökinformatikus BSc"));
        root.appendChild(createOra(doc, "5", "eloadas", "Mobil programozási alapok", "Hétfő", "16:00", "17:30", "L101", "Dr. Agárdi Anita", "Mérnökinformatikus BSc"));
        root.appendChild(createOra(doc, "6", "gyakorlat", "Mobil programozási alapok", "Hétfő", "18:00", "19:30", "L101", "Dr. Agárdi Anita", "Mérnökinformatikus BSc"));
        root.appendChild(createOra(doc, "7", "gyakorlat", "Windows rendszergazda", "Kedd", "8:00", "9:30", "L103", "Dr. Wagner György", "Mérnökinformatikus BSc"));
        root.appendChild(createOra(doc, "8", "eloadas", "Mesterséges intelligencia alapok", "Kedd", "10:00", "11:30", "E 32", "Kunné Dr. Tamás Judit, Fazekas Levente Áron", "Mérnökinformatikus BSc"));
        root.appendChild(createOra(doc, "9", "gyakorlat", "Mesterséges intelligencia alapok", "Kedd", "12:00", "13:30", "E 32", "Kunné Dr. Tamás Judit, Fazekas Levente Áron", "Mérnökinformatikus BSc"));
        root.appendChild(createOra(doc, "10", "gyakorlat", "Elektrotechnika-elektronika", "Kedd", "14:00", "15:30", "A1/317", "Dr. Kozsely Gábor", "Mérnökinformatikus BSc"));
        root.appendChild(createOra(doc, "11", "eloadas", "Windows rendszergazda", "Kedd", "16:00", "17:30", "L103", "Dr. Wagner György", "Mérnökinformatikus BSc"));
        root.appendChild(createOra(doc, "12", "eloadas", "Webes adatkezelő környezetek", "Szerda", "8:00", "9:30", "A1/310", "Dr. Kovács László József", "Mérnökinformatikus BSc"));
        root.appendChild(createOra(doc, "13", "gyakorlat", "Webes adatkezelő környezetek", "Szerda", "10:00", "11:30", "L103", "Dr. Bednarik László", "Mérnökinformatikus BSc"));

        // XML fájlba írás
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transf = transformerFactory.newTransformer();

        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transf.setOutputProperty(OutputKeys.INDENT, "yes");
        transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(doc);

        File myFile = new File("orarend1SEGUV3.xml");

        StreamResult console = new StreamResult(System.out);
        StreamResult file = new StreamResult(myFile);

        transf.transform(source, console);
        transf.transform(source, file);
    }

    // Egy óra (óra elem) létrehozása
    private static Node createOra(Document doc, String id, String tipus, String targy, String nap, String tol, String ig, String helyszin, String oktato, String szak) {

        Element ora = doc.createElement("ora");
        ora.setAttribute("id", id);
        ora.setAttribute("tipus", tipus);

        ora.appendChild(createOraElem(doc, "targy", targy));

        // Időpont elem létrehozása (összetett gyermek)
        Element idopont = doc.createElement("idopont");
        idopont.appendChild(createOraElem(doc, "nap", nap));
        idopont.appendChild(createOraElem(doc, "tol", tol));
        idopont.appendChild(createOraElem(doc, "ig", ig));
        ora.appendChild(idopont);

        ora.appendChild(createOraElem(doc, "helyszin", helyszin));
        ora.appendChild(createOraElem(doc, "oktato", oktato));
        ora.appendChild(createOraElem(doc, "szak", szak));

        return ora;
    }

    // Segédmetódus: egyszerű elem létrehozása
    private static Node createOraElem(Document doc, String name, String value) {

        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));

        return node;
    }
}
