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

    public LabWork() {}

    public void setDate(String str) {
        String[] dateTime = str.split(" ");
        int[] date = cast(dateTime[0].split("/"));
        int[] time = cast(dateTime[1].split(":"));
        this.creationDate = java.time.LocalDateTime.of(date[2], date[1], date[0], time[0], time[1], time[2]);
    }

    public int[] cast(String[] str) {
        int[] num = new int[str.length];
        for ( int i = 0; i < str.length; i++) {
            num[i] = Integer.parseInt(str[i]);
        }
        return num;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
                ",  Y: " + coordinates.getY() + " }\n  Creation Date: " +
                getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy  'at'  HH:mm:ss")) +
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
                    ",  Z: " + author.getLocation().getZ() + ",  Name: ");
                if (getAuthor().getLocation().getName() != null && !getAuthor().getLocation().getName().equals("")) {
                    disp += (author.getLocation().getName() + " } }.");
                } else { disp += (" } }."); }
            }
        }
        return disp;
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
