package t1u7a1n2;

import org.w3c.dom.*;
import t1u7a1n2.labwork.*;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CollectionManager {
    private LinkedHashMap<String, LabWork> collection;
    String timeStamp = new SimpleDateFormat("dd/MM/yy  HH:mm:ss").format(Calendar.getInstance().getTime());

    public CollectionManager(){}

    public void add(LinkedHashMap<String, LabWork> linkedHashMap) {
        this.collection = linkedHashMap;
    }

    /**
     * HELP!!!
     */
    public void help() {
        System.out.println("\n  help:     display help for available commands\n" +
                "  info:     print information about the collection to standard output\n" +
                "  show:     display all elements of the collection in string representation to standard output\n" +
                "  insert null {element}:     add a new element with the given key\n" +
                "  update id {element}:     update the value of the collection element whose id is equal to the given\n" +
                "  remove_key null:     remove an item from the collection by its key \n" +
                "  clear:     clear the collection\n" +
                "  save:     save the collection to a file\n" +
                "  execute_script file_name:     read and execute the script from the specified file \n" +
                "  exit:     exit the program (without saving to file)\n" +
                "  remove_lower {element}:     remove all elements from the collection that are less than the given one\n" +
                "  history:     display the last 14 commands (without their arguments)\n" +
                "  replace_if_greater null {element}:     replace value by key if new value is greater than old\n" +
                "  max_by_id:     display any object from the collection, the value of the id field of which is the maximum\n" +
                "  group_counting_by_average_point:     group the elements of the collection by the value of the averagePoint field, display the number of elements in each group\n" +
                "  print_descending:     display the elements of the collection in descending order");
    }

    /**
     * Basic information of collection
     */
    public void info() {
        System.out.println("\n  Type:  LinkedHashMap\n" +
                "  Data of initialization:  " + timeStamp +
                "\n  Size:  " + collection.size());
    }

    public void show() {
        if (collection.isEmpty()) System.out.println("\n  Collection is empty.");
        else {
            System.out.println();
            int i = collection.size();
            for (String key : collection.keySet()) {
                collection.get(key).display();
                if( i > 1) { System.out.println("\n"); i--; }
                else { System.out.println(); }
            }
        }
    }

    /**
     * Add new labWork
     */
    public void insert(String key) {
        if (collection.containsKey(key)) { System.out.println("\n  Key is exist.");
        } else {
            LabWork labWork = new LabWork();
            setData(labWork);
            collection.put(key, labWork);
            System.out.println("\n  Added new element");
        }
    }

    /**
     * Change data of person with given ID
     * @param id ID
     */
    public void update(long id) {
        Set<String> keySet = collection.keySet();
        int a = 0;
        for (String key : keySet) {
            if (Objects.equals(collection.get(key).getId(), id)) {
                LabWork labWork = new LabWork();
                setData(labWork);
                labWork.setId(id);
                collection.put(key, labWork);
                a++;
                System.out.println("\n  Updated element with id " + id);
            }
        }
        if (a == 0) {
            System.out.println("\n  No such element with id " + id);
        }
    }

    /**
     * Remove element with given key out of collection
     * @param key key
     */
    public void remove_key(String key) { collection.remove(key); System.out.println("\n  Removed element with key " + key);}

    /**
     * Clear the collection
     */
    public void clear() {
        collection.clear();
        System.out.println("\n  Cleared the collection");
    }

    /**
     * Save the collection data into initial XML file using class PrintWriter
     */
    public void save(String file) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            //root element
            Element root = document.createElement("Lab");
            document.appendChild(root);
            for (String key: collection.keySet()) {
                LabWork labWork = collection.get(key);
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
            System.out.println("\n  Save into file " + file);
        } catch (ParserConfigurationException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method removes all elements lower than the given element
     */
    public void remove_lower() {
        LabWork labWork = new LabWork();
        setData(labWork);
        collection.values().removeIf(s -> s.compareTo(labWork) < 0);
        System.out.println("\n  Removed lower than given element");
    }

    /**
     *Replaces element by key if new element is greater than old
     * @param key key
     */
    public void replace_if_greater(String key) {
        if (collection.containsKey(key)) {
            LabWork labWork = new LabWork();
            setData(labWork);
            if (labWork.compareTo(collection.get(key)) > 0) {
                collection.put(key, labWork);
                System.out.println("\n  Replaced element with key " + key);
            } else { System.out.println("\n  New element is less than old"); }
        } else {
            System.out.println("\n  No such element with key " + key);
        }
    }

    /**
     * Display element with maximum ID
     */
    public void max_by_id() {
        String keys = "";
        long ids = 0;
        for (String key : collection.keySet()) {
            if (collection.get(key).getId() > ids)  { ids = collection.get(key).getId(); keys = key; }
        }
        System.out.println();
        collection.get(keys).display();
        System.out.println();
    }

    /**
     * Group the elements by the value of the averagePoint field, display the number of elements in each group
     */
    public void group_counting_by_average_point() {
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        for (String key : collection.keySet()) {
            if (collection.get(key).getAveragePoint() < 60) a++;
            else if (collection.get(key).getAveragePoint() < 75) b++;
            else if (collection.get(key).getAveragePoint() < 90) c++;
            else d++;
        }
        System.out.println("\n  2: " + a + "\n  3: " + b + "\n  4: " + c + "\n  5: " + d);
    }

    /**
     * display the elements of the collection in descending order
     */
    public void print_descending() {
        LinkedHashMap<String, LabWork> newColl = new LinkedHashMap<>(collection);
        System.out.println();
        if (newColl.isEmpty()) {
            System.out.println("  Collection is empty");
        } else {
            while (!newColl.isEmpty()) {
                this.max_by_average(newColl);
            }
        }
    }

    /**
     * Display element with maximum Average Point
     */
    private void max_by_average(LinkedHashMap<String, LabWork> collection) {
        String keys = "";
        int ids = 0;
        for (String key : collection.keySet()) {
            if (collection.get(key).getAveragePoint() != null && collection.get(key).getAveragePoint() > ids) { ids = collection.get(key).getAveragePoint(); keys = key; }
            else {
                if (collection.get(key).getAveragePoint() == null && ids == 0) { keys = key; } }
        }
        collection.get(keys).display();
        collection.remove(keys);
        if (!collection.isEmpty()) System.out.println("\n");
        else { System.out.println(); }
    }

    /**
     *Manually write data for given labWork
     *@param labWork LabWork
     */
    private void setData(LabWork labWork) {
        try {
            Scanner scanner = new Scanner(System.in);
            boolean bool = true;
            while (labWork.getName() == null || labWork.getName() == "") {
                System.out.print("  Name (can't be empty): ");
                try {
                    labWork.setName(scanner.nextLine());
                } catch (Exception e) {
                }
            }
            while (labWork.getCoordinates() == null) {
                System.out.print("  Coordinates (can't be empty, format \"x y\", x long, y double): ");
                String scan = scanner.nextLine();
                if (scan.trim().split(" ").length == 1) {
                    System.out.println("  Must be 2 numbers");
                } else {
                    String[] sc = scan.trim().split(" ", 2);
                    try {
                        labWork.setCoordinates(new Coordinates(Long.valueOf(sc[0]), Double.valueOf(sc[1])));
                    } catch (NumberFormatException e) {
                        System.out.println("  Wrong coordinate format");
                    }
                }
            }
            while (labWork.getMinimalPoint() <= 0) {
                System.out.print("  Minimal Point (long, be larger than 0): ");
                long sc = 0;
                try {
                    sc = Long.parseLong(scanner.nextLine());
                    if (sc <= 0) {
                        System.out.println("  Be larger than 0!!!");
                    } else {
                        labWork.setMinimalPoint(sc);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("  Wrong number format");
                }

            }
            while (bool) {
                System.out.print("  Tuned In Works (long, can be null): ");
                String sc = scanner.nextLine();
                if (sc.equals("null") || sc.equals("")) bool = false;
                else {
                    try {
                        labWork.setTunedInWorks(Long.parseLong(sc));
                        bool = false;
                    } catch (NumberFormatException e) {
                        System.out.println("  Wrong number format");
                    }
                }
            }
            bool = true;
            while (bool) {
                System.out.print("  Average Point (long, be larger than 0, can be null): ");
                String sc = scanner.nextLine();
                if (sc.equals("null") || sc.equals("")) { bool = false; }
                else {
                    int x;
                    try {
                        x = Integer.parseInt(sc);
                        if ( x <= 0) System.out.println("  Be larger than 0!!!");
                        else { labWork.setAveragePoint(x); bool = false; }
                    } catch (NumberFormatException e) {
                        System.out.println("  Wrong number format");
                    }
                }
            }
            bool = true;
            while (bool) {
                System.out.print("  Difficulty (normal, hard, very hard, impossible, terrible, can be null): ");
                String x = scanner.nextLine().toLowerCase();
                switch (x) {
                    case "" : bool = false; break;
                    case "normal" : labWork.setDifficulty(Difficulty.NORMAL); bool = false; break;
                    case "hard" : labWork.setDifficulty(Difficulty.HARD); bool = false; break;
                    case "very hard" : labWork.setDifficulty(Difficulty.VERY_HARD); bool = false; break;
                    case "impossible" : labWork.setDifficulty(Difficulty.IMPOSSIBLE); bool = false; break;
                    case "terrible" : labWork.setDifficulty(Difficulty.TERRIBLE); bool = false; break;
                    default: System.out.println("  Wrong string."); break;
                }
            }
            bool = true;
            while (bool){
                System.out.print("  Add Author??? 1.Yes 2.No \t");
                String sc = scanner.nextLine();
                String[] str = new String[4];
                if (!sc.equals("1") && !sc.equals("2")) { System.out.println("  Enter 1 or 2."); }
                else if (sc.equals("2")) { bool = false; }
                else {
                    Person author = new Person();
                    while (author.getName() == null || author.getName() == "") {
                        System.out.print("  Author's name (can't be empty): ");
                        try {
                            author.setName(scanner.nextLine());
                        } catch (Exception e) {
                        }
                    }
                    while (author.getWeight() <= 0) {
                        System.out.print("  Author's weight (float, be larger than 0): ");
                        try {
                            float abc = Float.parseFloat(scanner.nextLine());
                            if (abc <= 0) { System.out.println("  Be larger than 0!!!"); }
                            else { author.setWeight(abc); }
                        } catch (NumberFormatException e) {
                            System.out.println("  Wrong number format"); }
                    }
                    Location location = new Location();
                    boolean localBool = true;
                    while (localBool) {
                        System.out.print("  Author Localtion (can be null, format \"x y z name\", x int, y float, z double, name (can't be longer than 236, can be null) ): ");
                        String s = scanner.nextLine();
                        if (s.equals(null) || s.equals("")) {localBool = false; }
                        else {
                            int n = s.trim().split(" ").length;
                            if (n < 3) {
                                System.out.println("  Must be 3 or 4 arguments");
                            } else {
                                if (n == 3) {
                                    str[0] = s.trim().split(" ")[0];
                                    str[1] = s.trim().split(" ")[1];
                                    str[2] = s.trim().split(" ")[2];
                                } else {
                                    str = s.trim().split(" ", 4);
                                    if (str[3].length() > 236) {
                                        System.out.println("  Location name can't be longer 236 char");
                                    }
                                }
                                try {
                                    location.setX(Integer.valueOf(str[0]));
                                    location.setY(Float.parseFloat(str[1]));
                                    location.setZ(Double.valueOf(str[2]));
                                    if (str[3] != null) {
                                        location.setName(str[3]);
                                    }
                                    localBool = false;
                                } catch (NumberFormatException e) {
                                    System.out.println("  Wrong number format");
                                }
                            }
                        }
                    }
                    author.setLocation(location);
                    labWork.setAuthor(author);
                    bool = false;
                }
            }
        } catch (NoSuchElementException e) {
            System.exit(0);
        }
    }
}
