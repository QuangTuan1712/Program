package commands;

import labWork.*;
import server.ServerReader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveCommand extends Command {

    public SaveCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute(String file) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            //root element
            Element root = document.createElement("Lab");
            document.appendChild(root);
            for (Integer key: getCollection().keySet()) {
                LabWork labWork = getCollection().get(key);
                Element lab = document.createElement("LabWork");
                root.appendChild(lab);
                Element id = document.createElement("id");
                id.appendChild(document.createTextNode(labWork.getId().toString()));
                lab.appendChild(id);
                Element name = document.createElement("name");
                name.appendChild(document.createTextNode(labWork.getName()));
                lab.appendChild(name);
                Element coordinates = document.createElement("coordinates");
                lab.appendChild(coordinates);
                Element xCoord = document.createElement("x");
                xCoord.appendChild(document.createTextNode(labWork.getCoordinates().getX().toString()));
                coordinates.appendChild(xCoord);
                Element yCoord = document.createElement("y");
                yCoord.appendChild(document.createTextNode(labWork.getCoordinates().getY().toString()));
                coordinates.appendChild(yCoord);
                Element minimal = document.createElement("minimalPoint");
                minimal.appendChild(document.createTextNode(String.valueOf(labWork.getMinimalPoint())));
                lab.appendChild(minimal);
                Element tuned = document.createElement("tunedInWorks");
                if (labWork.getTunedInWorks() != null) {
                    tuned.appendChild(document.createTextNode(labWork.getTunedInWorks().toString()));
                }
                lab.appendChild(tuned);
                Element average = document.createElement("averagePoint");
                if (labWork.getAveragePoint() != null) {
                    average.appendChild(document.createTextNode(labWork.getAveragePoint().toString()));
                }
                lab.appendChild(average);
                Element diff = document.createElement("difficulty");
                if (labWork.getDifficulty() != null) {
                    diff.appendChild(document.createTextNode(labWork.getDifficulty().toString().toLowerCase()));
                }
                lab.appendChild(diff);
                Element author = document.createElement("author");
                lab.appendChild(author);
                if (labWork.getAuthor() != null) {
                    Element nameAuthor = document.createElement("name");
                    nameAuthor.appendChild(document.createTextNode(labWork.getAuthor().getName()));
                    author.appendChild(nameAuthor);
                    Element weightAuthor = document.createElement("weight");
                    weightAuthor.appendChild(document.createTextNode(String.valueOf(labWork.getAuthor().getWeight())));
                    author.appendChild(weightAuthor);
                    Element localAuthor = document.createElement("location");
                    author.appendChild(localAuthor);
                    if (labWork.getAuthor().getLocation() != null) {
                        Element xLocal = document.createElement("x");
                        xLocal.appendChild(document.createTextNode(labWork.getAuthor().getLocation().getX().toString()));
                        localAuthor.appendChild(xLocal);
                        Element yLocal = document.createElement("y");
                        yLocal.appendChild(document.createTextNode(String.valueOf(labWork.getAuthor().getLocation().getY())));
                        localAuthor.appendChild(yLocal);
                        Element zLocal = document.createElement("z");
                        zLocal.appendChild(document.createTextNode(String.valueOf(labWork.getAuthor().getLocation().getZ())));
                        localAuthor.appendChild(zLocal);
                        Element nameLocal = document.createElement("name");
                        if (labWork.getAuthor().getLocation().getName() != null) {
                            nameLocal.appendChild(document.createTextNode(labWork.getAuthor().getLocation().getName()));
                        }
                        localAuthor.appendChild(nameLocal);
                    }
                }
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(file)));
        } catch (ParserConfigurationException | IOException | TransformerException e) {
            e.printStackTrace();
        }
        return ("Save into file " + file);
    }
}
