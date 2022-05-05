package t1u7a1n2;

import t1u7a1n2.labwork.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

/**
 * Class for read file
 */
public class Reader {
    static LinkedHashMap<String, LabWork> collectionLab = new LinkedHashMap<>();
    CollectionManager collectionManager = new CollectionManager();
    String file;
    CommandReader r = new CommandReader();

    public Reader(String file) {
        this.file = file;
        /**
         *XML Document builder with file input using InputStreamReader class
         */
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputStreamReader in = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            if (!in.ready()) {
                System.out.println("  File is empty.");
                System.exit(0);
            }
            InputSource inputSource = new InputSource(in);
            Document data = builder.parse(inputSource);
            //normalize
            data.getDocumentElement().normalize();
            //Root element (Lab)
            if (data.getDocumentElement().getNodeName() != "Lab") {
                System.out.println("  File not meet required format.Root file node should be Lab.");
                System.exit(0);
            }
            //parse data of Lab into LinkedHashedMap collection
            NodeList nodeList = data.getElementsByTagName("LabWork");
            //now XML is loaded as Document in memory, lets convert it to Object List
            for (int i = 0; i < nodeList.getLength(); i++) {
                LabWork labWork = getLabWork(nodeList.item(i));
                if (labWork != null) { collectionLab.put(String.valueOf(i), labWork); }
            }
            in.close();
//            this.r = r;
//            //execute main process of program
//            r.readCommand();
        } catch (FileNotFoundException e) {
            System.out.println("  No such file exist.");
        } catch (ParserConfigurationException | SAXException e) {
            System.out.println("  Parser error.");
        } catch (IOException e) {
            System.out.println("  IOException occurred.");
        }
    }

    public void run() {
        collectionManager.add(collectionLab);
        this.r = new CommandReader(collectionManager);
        r.readCommand();
    }

    /**
     * Create new labwork instance with node data from XML file
     * @param node input node to read
     * @return labwork instance
     */
    private LabWork getLabWork(Node node) {
        try {
            LabWork labWork = new LabWork();
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                labWork.setName(getValue(element, "name"));
                Element coordElement = (Element) element.getElementsByTagName("coordinates").item(0);
                //set coordinates
                Coordinates coordinates = new Coordinates(
                        Long.valueOf(getValue(coordElement, "x")),
                        Double.valueOf(getValue(coordElement, "y"))
                );
                labWork.setCoordinates(coordinates);
                labWork.setMinimalPoint(Long.parseLong(getValue(element, "minimalPoint")));
                if (!getValue(element, "tunedInWorks").isEmpty()) {
                    labWork.setTunedInWorks(Long.valueOf(getValue(element, "tunedInWorks")));
                }
                if (!getValue(element, "averagePoint").isEmpty()) {
                    labWork.setAveragePoint(Integer.valueOf(getValue(element, "averagePoint")));
                }
                if (!getValue(element, "difficulty").isEmpty()) {
                    labWork.setDifficulty(Difficulty.valueOf(getValue(element, "difficulty").toUpperCase().replace(" ", "_")));
                }
                //set author
                if (!getValue(element, "author").isEmpty()) {
                    Element perElement = (Element) element.getElementsByTagName("author").item(0);
                    Person person = new Person(
                            getValue(perElement, "name"),
                            Float.parseFloat(getValue(perElement, "weight")),
                            null
                    );
                    if (!getValue(element, "location").isEmpty()) {
                        Element localElement = (Element) element.getElementsByTagName("location").item(0);
                        Location location = new Location(
                                Integer.valueOf(getValue(localElement, "x")),
                                Float.parseFloat(getValue(localElement, "y")),
                                Double.parseDouble(getValue(localElement, "z"))
                        );
                        if (!getValue(localElement, "name").isEmpty()) {
                            location.setName(getValue(localElement, "name"));
                        }
                        person.setLocation(location);
                    }
                    labWork.setAuthor(person);
                }
            }
            return labWork;
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Get value of element by tag name
     * @param item element
     * @param str tag name
     * @return
     */
    private String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return getElementValue(n.item(0));
    }

    /**
     * Safe way to get element value from node
     * @param node node
     * @return string value
     */
    private String getElementValue(Node node) {
        Node child;
        if (node.hasChildNodes()) {
            for (child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
                if (child.getNodeType() == Node.TEXT_NODE) {
                    return child.getNodeValue();
                }
            }
        }
        return "";
    }

    public void listId() {

    }
}
