package domseguv31015;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class DOMRead {

    public static void main(String[] args) {
        String xmlFile = "orarendSEGUV3.xml";

        try {
            File f = new File(xmlFile);
            if (!f.exists()) {
                System.err.println("Hiba: a fájl nem található: " + xmlFile);
                System.exit(1);
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
           
            dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            dbFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            dbFactory.setXIncludeAware(false);
            dbFactory.setExpandEntityReferences(false);

            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(f);
            doc.getDocumentElement().normalize();

           
            System.out.println("----- DOM fa (blokk formában) -----");
            printNode(doc.getDocumentElement(), 0);
            System.out.println("----- Vége -----");

        } catch (Exception e) {
            System.err.println("Hiba a DOM feldolgozás során: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printNode(Node node, int indent) {
        if (node == null) return;

        switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                printIndent(indent);
                Element elem = (Element) node;
                System.out.print("<" + elem.getTagName());
                NamedNodeMap attrs = elem.getAttributes();
                for (int i = 0; i < attrs.getLength(); i++) {
                    Node a = attrs.item(i);
                    System.out.print(" " + a.getNodeName() + "=\"" + escape(a.getNodeValue()) + "\"");
                }
                System.out.println(">");

                
                NodeList children = elem.getChildNodes();
                boolean hasElementChild = false;
                boolean hasTextChild = false;
                StringBuilder textContent = new StringBuilder();

                for (int i = 0; i < children.getLength(); i++) {
                    Node ch = children.item(i);
                    if (ch.getNodeType() == Node.ELEMENT_NODE) {
                        hasElementChild = true;
                    } else if (ch.getNodeType() == Node.TEXT_NODE) {
                        String t = ch.getTextContent().trim();
                        if (!t.isEmpty()) {
                            hasTextChild = true;
                            textContent.append(t);
                        }
                    }
                }

                if (hasTextChild && !hasElementChild) {
                    printIndent(indent + 1);
                    System.out.println(escape(textContent.toString()));
                } else {
                    for (int i = 0; i < children.getLength(); i++) {
                        Node ch = children.item(i);
                        if (ch.getNodeType() == Node.TEXT_NODE) {
                            String t = ch.getTextContent().trim();
                            if (!t.isEmpty()) {
                                printIndent(indent + 1);
                                System.out.println(escape(t));
                            }
                        } else {
                            printNode(ch, indent + 1);
                        }
                    }
                }

                printIndent(indent);
                System.out.println("</" + elem.getTagName() + ">");
                break;

            case Node.TEXT_NODE:
                String text = node.getTextContent().trim();
                if (!text.isEmpty()) {
                    printIndent(indent);
                    System.out.println(escape(text));
                }
                break;

            case Node.COMMENT_NODE:
                printIndent(indent);
                System.out.println("<!-- " + node.getNodeValue() + " -->");
                break;

            default:
                break;
        }
    }

    private static void printIndent(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("    "); 
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
