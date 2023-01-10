package evolutionGame;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Test {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        URL url = Test.class.getResource("/configs.xml");
        File file = new File(url.getPath());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("config");
        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            Element element = (Element) node;
            System.out.println("<height>" + element.getElementsByTagName("height").item(0).getTextContent());
            System.out.println("<width>"+ element.getElementsByTagName("width").item(0).getTextContent());
            System.out.println("<map>"+ element.getElementsByTagName("map").item(0).getTextContent());
            System.out.println("<startGrass>"+ element.getElementsByTagName("startGrass").item(0).getTextContent());
            System.out.println("<grassEnergy>"+ element.getElementsByTagName("grassEnergy").item(0).getTextContent());
            System.out.println("<grassGain>"+ element.getElementsByTagName("grassGain").item(0).getTextContent());
            System.out.println("<startAnimals>"+ element.getElementsByTagName("startAnimals").item(0).getTextContent());
            System.out.println("<startEnergy>"+ element.getElementsByTagName("startEnergy").item(0).getTextContent());
            System.out.println("<whenFull>"+ element.getElementsByTagName("whenFull").item(0).getTextContent());
            System.out.println("<energyCopulate>"+ element.getElementsByTagName("energyCopulate").item(0).getTextContent());
            System.out.println("<minMutation>"+ element.getElementsByTagName("minMutation").item(0).getTextContent());
            System.out.println("<maxMutation>"+ element.getElementsByTagName("maxMutation").item(0).getTextContent());
            System.out.println("<mutation>"+ element.getElementsByTagName("mutation").item(0).getTextContent());
            System.out.println("<genotypeLength>"+ element.getElementsByTagName("genotypeLength").item(0).getTextContent());
            System.out.println("<genes>"+ element.getElementsByTagName("genes").item(0).getTextContent());
        }

    }
}
