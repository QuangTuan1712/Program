package server;

import labWork.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import javax.xml.parsers.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;

public class ServerReader {
    private LinkedHashMap<String, LabWork> collection = new LinkedHashMap<>();
    public String timeStamp = new SimpleDateFormat("dd/MM/yy  HH:mm:ss").format(Calendar.getInstance().getTime());
    String file;

    public LinkedHashMap<String, LabWork> getCollection() {
        return collection;
    }

    public ServerReader(String file) {
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
            data.getDocumentElement().normalize();
            if (data.getDocumentElement().getNodeName() != "Lab") {
                System.out.println("  File not meet required format. Root file node should be Lab.");
                System.exit(0);
            }
            NodeList nodeList = data.getElementsByTagName("LabWork");
            for (int i = 0; i < nodeList.getLength(); i++) {
                LabWork labWork = getLabWork(nodeList.item(i));
                if (labWork != null) { collection.put(String.valueOf(i), labWork); }
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.err.println("  No such file \"" + file +"\" exist.");
        } catch (ParserConfigurationException | SAXException e) {
            System.err.println("  Parser error.");
        } catch (IOException e) {
            System.err.println("  IOException occurred.");
        }
    }

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
            System.err.println(e.getMessage());
            return null;
        }
    }

    private String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return getElementValue(n.item(0));
    }

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
}
