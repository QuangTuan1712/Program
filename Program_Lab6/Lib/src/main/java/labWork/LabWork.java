package labWork;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class an element of collection
 */
public class LabWork implements Comparable<LabWork>, Serializable {
//    private static final long serialVersionUID = 1L;
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate = java.time.LocalDateTime.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long minimalPoint; //Значение поля должно быть больше 0
    private Long tunedInWorks; //Поле может быть null
    private Integer averagePoint; //Поле может быть null, Значение поля должно быть больше 0
    private Difficulty difficulty; //Поле может быть null
    private Person author; //Поле может быть null

    public LabWork() {
        this.id = generateID();
    }
/**    public LabWork(String name, Coordinates coordinates, long minimalPoint, Long tunedInWorks, Integer averagePoint, Difficulty difficulty, Person author) {
        this.id = generateID();
        this.name = name;
        this.coordinates = coordinates;
        this.minimalPoint = minimalPoint;
        this.tunedInWorks = tunedInWorks;
        this.averagePoint = averagePoint;
        this.difficulty = difficulty;
        this.author = author;
    }
 */

    public void setId() {
    this.id = generateID();
}

    public void setDate() { this.creationDate = java.time.LocalDateTime.now(); }

    public void setId(Long id) { this.id = id; }

    public Long getId() {
        return id;
    }

    public void setName(String name){
        if (name != null && name.length() != 0) {
            this.name = name;
        } else {
            throw new RuntimeException("Name can't be null or empty line.");
        }
    }

    public String getName() {
        return name;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates != null) { this.coordinates = coordinates; }
        else {
            throw new RuntimeException("Coordinates can't be null.");
        }
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setMinimalPoint(long minimalPoint) {
        if (minimalPoint > 0) {
            this.minimalPoint = minimalPoint;
        } else {
            throw new RuntimeException("Minimal point can't be less than 0.");
        }
    }

    public long getMinimalPoint() {
        return minimalPoint;
    }

    public void setTunedInWorks(Long tunedInWorks) {
            this.tunedInWorks = tunedInWorks;
    }

    public Long getTunedInWorks() {
        return tunedInWorks;
    }

    public void setAveragePoint(Integer averagePoint) {
        if (averagePoint > 0) {
            this.averagePoint = averagePoint;
        } else {
            throw new RuntimeException("Average point can't be less than 0.");
        }
    }

    public Integer getAveragePoint() {
        return averagePoint;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return  difficulty;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public Person getAuthor() {
        return  author;
    }

    public String display() {
        String disp = "";
        disp += ("  Id:  " + getId());
        disp += ("\n  Name: " + getName() + "\n  Coordinates:      { X: " + coordinates.getX() +
                ",  Y: " + coordinates.getY() + " }\n  Creation Date: " + getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy  'at'  HH:mm:ss")) +
                "\n  Minimal Point: " + getMinimalPoint() + "\n  Tuned In Works: ");
        if (getTunedInWorks() != null) {
            disp += (getTunedInWorks());
        }
        disp += ("\n  Average Point: ");
        if (getAveragePoint() != null) {
            disp += (getAveragePoint());
        }
        disp += ("\n  Difficulty: ");
        if (getDifficulty() != null) {
            disp += (getDifficulty());
        }
        disp += ("\n  Author:");
        if (getAuthor() != null) {
            disp += ("      { Name: " + author.getName() + ",  Weight: " + author.getWeight() + ",  Location:");
            if (getAuthor().getLocation() != null) { disp +=("      { X: " + author.getLocation().getX() + ",  Y: " + author.getLocation().getY() +
                    ",  Z: " + author.getLocation().getZ() + ",  Name: "); }
            if (getAuthor().getLocation().getName() != null) {
                disp += (author.getLocation().getName() + " } }.");
            } else { disp += (" } }."); }
        }
        return disp;
    }

    /**
     * Generate a unique ID (8 digits)
     * @return id as long
     */
    private Long generateID() {
        return Math.round(Math.random() * 100000000);
    }

    /**
     * Compare based on getMinimalPoint() method
     * @param o LabWork to compare with
     * @return 1 if larger, 0 if equal, -1 if smaller
     */
    @Override
    public int compareTo(LabWork o) {
        if (o == null) { return 1; }
        else if (this.getAveragePoint() == null) {
            if (o.getAveragePoint() == null) return 0;
            else return -1;
        }
        return Integer.compare(this.averagePoint, o.getAveragePoint());
    }
}
